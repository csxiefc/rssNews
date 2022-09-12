package net.mingsoft.cms.spider.signal;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.RssItemEntity;
import net.mingsoft.cms.entity.RssSeedEntity;
import net.mingsoft.cms.entity.RssSiteEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.lang.reflect.Field;
import java.util.Date;

@Slf4j
public class ArticleParseImplBiz implements IArticleParseBiz {

    private ParseConf getParseConf(RssSiteEntity siteObj, RssSeedEntity seedObj) {
        // 获取网页解析配置
        ParseConf result = null;
        if(StringUtils.isNotEmpty(siteObj.getParseConf()) && StringUtils.isNotEmpty(seedObj.getParseConf())) { // 父子都有配置解析的情况
            ParseConf parentObj =  JSONObject.parseObject(siteObj.getParseConf(),ParseConf.class);
            ParseConf subObj =  JSONObject.parseObject(seedObj.getParseConf(),ParseConf.class);
            try {
                // 子对象有值的情况下，替换父对象的属性值
                Class<?> clazz = subObj.getClass();
                Field[] field = clazz.getDeclaredFields();
                for (Field f : field) {
                    f.setAccessible(true);
                    String name = f.getName();
                    String value = f.get(subObj).toString();
                    if(StringUtils.isNotEmpty(value)) { // 子对象有值的情况下，替换父对象的属性值
                        BeanUtils.setProperty(parentObj,name,value);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            result = parentObj;
        } else if(StringUtils.isNotEmpty(siteObj.getParseConf())) { // 父配置了，子没有配置
            result =  JSONObject.parseObject(siteObj.getParseConf(),ParseConf.class);
        } else if(StringUtils.isNotEmpty(seedObj.getParseConf())) { // 子配置了，父没有配置
            result =  JSONObject.parseObject(seedObj.getParseConf(),ParseConf.class);
        }
        return result;
    }

    public AjaxResult parse(RssSiteEntity siteObj, RssSeedEntity seedObj,RssItemEntity item) {
        try {
            // 获取网页解析配置
            ParseConf confObj = this.getParseConf(siteObj,seedObj);
            // 网页解析配置为空，向上抛出异常
            if(confObj == null ) {
                throw new Exception("RssSiteId="+siteObj.getId()+" or RssSeedId="+seedObj.getId()+",parseConf is Empty!");
            }
            // 从配置JSON中读取各个字段的XPATH
            // 解析网页
            Document doc = Jsoup.connect(item.getLink()).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36").get();
            JXDocument jxd = JXDocument.create(doc);
            // JXDocument jxd = JXDocument.createByUrl(item.getLink());
            // 组装
            StringBuffer content = new StringBuffer(100);

            // 缩图路径
            JXNode imgHtmlNode = null;
            if(StringUtils.isNotEmpty(confObj.getImgHtmlXpath())) {
                imgHtmlNode = jxd.selNOne(confObj.getImgHtmlXpath());
            }
            // 缩图文字描述
            JXNode imgDescNode = null;
            if(StringUtils.isNotEmpty(confObj.getImgDescXpath())) {
                imgDescNode = jxd.selNOne(confObj.getImgDescXpath());
            }
            // 正文
            JXNode contentNode = null;
            if(StringUtils.isNotEmpty(confObj.getContentXpath())) {
                contentNode = jxd.selNOne(confObj.getContentXpath());
            }
            // 编辑
            JXNode editorNameNode = null;
            if(StringUtils.isNotEmpty(confObj.getEditorNameXpath())) {
                editorNameNode = jxd.selNOne(confObj.getEditorNameXpath());
            }
            // 视频
            JXNode videoNode = null;
            if(StringUtils.isNotEmpty(confObj.getVideoXpath())) {
                videoNode = jxd.selNOne(confObj.getVideoXpath());
            }

            if(imgHtmlNode != null) { // 配图
                content.append("<div style='text-align: center;'><img src='"+imgHtmlNode.asString()+"'/></div><br/>");
            }
            if(imgDescNode != null) { // 配图-文字
                content.append("<div style='text-align: center;'>"+imgDescNode.asString()+"</div><<br/>");
            }
            if(videoNode != null) { // 视频或者直播
                content.append(videoNode.asString()+"<br/>");
            }
            if(contentNode != null) { // 正文
                content.append(contentNode.asString());
            }
            //item.setTitle(jxd.selNOne(titleXpath).asString());
            item.setLinkContent(content.toString().length() > 0 ? content.toString() : null);

            String editor = null;
            if(editorNameNode != null && editorNameNode.asString().length() > 195) {
                editor = editorNameNode.asString().substring(0,195);
                item.setRemark("editor error");
            } else if (editorNameNode != null) {
                editor = editorNameNode.asString();
            }
            item.setEditor(editor);

            String thumbnailPath = null;
            if(imgHtmlNode != null && imgHtmlNode.asString().length() > 195) {
                thumbnailPath = imgHtmlNode.asString().substring(0,195);
                item.setRemark("thumbnailPath error");
            } else if (imgHtmlNode != null) {
                thumbnailPath = imgHtmlNode.asString();
            }
            item.setThumbnailPath(thumbnailPath);
            item.setThumbnailDesc(imgDescNode != null ? imgDescNode.asString() : null);
            // 标记-已经抓取网页内容
            item.setIsBuild("Y");
            // 记录更新时间
            item.setUpdateBy("sys");
            item.setUpdateDate(new Date());
            // 返回
            return AjaxResult.success(item);
        } catch (Exception e) {
            log.error("解析网页内容异常,RssItemId="+item.getId()+",Url="+item.getLink(),e);
            return AjaxResult.error(e.getLocalizedMessage());
        }
    }



}
