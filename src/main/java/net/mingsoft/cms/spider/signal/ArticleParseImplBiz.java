package net.mingsoft.cms.spider.signal;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.RssItemEntity;
import net.mingsoft.cms.entity.RssSeedEntity;
import net.mingsoft.cms.entity.RssSiteEntity;
import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.util.Date;

@Slf4j
public class ArticleParseImplBiz implements IArticleParseBiz {

    public AjaxResult parse(RssSiteEntity siteObj, RssSeedEntity seedObj,RssItemEntity item) {
        try {
            // 获取网页解析配置
            String parseConf = seedObj.getParseConf();
            if(StringUtils.isEmpty(parseConf)) { // 若是SEED的解析配置为空，则用RSS站点的通用解析对象
                parseConf = siteObj.getParseConf();
            }
            // 网页解析配置为空，不好干活了
            if(StringUtils.isEmpty(parseConf)) {
                throw new Exception("RssSite or RssSeed's parseConf is Empty!");
            }
            // 从配置JSON中读取各个字段的XPATH
            JSONObject conf = JSONObject.parseObject(parseConf);
            String contentXpath = conf.getString("contentXpath");
            String imgHtmlXpath = conf.getString("imgHtmlXpath");
            String imgDescXpath = conf.getString("imgDescXpath");
            String editorNameXpath = conf.getString("editorNameXpath");
            // 解析网页
            JXDocument jxd = JXDocument.createByUrl(item.getLink());
            //Xpath语句
        /*
        String titleXpath = "//h1[@class='content_left_title']/text()";
        String contentXpath = "//div[@class='left_zw']/html()";
        String imgHtmlXpath = "//div[@class='left_ph']/html()";
        String imgDescXpath = "//div[@class='left_pt']/text()";
        String creatorIdXpath = "//input[@id='creator']/@value";
        String editorNameXpath = "//input[@id='editorname']/@value";
        String title2Xpath = "//input[@id='newstitle']/@value";
        */
            // 组装
            StringBuffer content = new StringBuffer(100);
            JXNode imgHtmlNode = jxd.selNOne(imgHtmlXpath);
            JXNode imgDescNode = jxd.selNOne(imgDescXpath);
            JXNode contentNode = jxd.selNOne(contentXpath);
            JXNode editorNameNode = jxd.selNOne(editorNameXpath);
            if(imgHtmlNode != null) {
                content.append("<div style='text-align: center;'>"+imgHtmlNode.asString()+"</div><br/>");
            }
            if(imgDescNode != null) {
                content.append(imgDescNode.asString()+"<br/>");
            }
            if(contentNode != null) {
                content.append(contentNode.asString());
            }
            //item.setTitle(jxd.selNOne(titleXpath).asString());
            item.setLinkContent(content.toString());
            item.setEditor(editorNameNode != null ? editorNameNode.asString() : null);
            item.setThumbnailPath(imgHtmlNode != null ? imgHtmlNode.asString() : null);
            item.setThumbnailDesc(imgDescNode != null ? imgDescNode.asString() : null);
            // 标记-已经抓取网页内容
            item.setIsBuild("Y");
            // 记录更新时间
            item.setUpdateBy("sys");
            item.setUpdateDate(new Date());
            // 返回
            return AjaxResult.success(item);
        } catch (Exception e) {
            log.error("解析网页内容异常",e);
            return AjaxResult.error(e.getLocalizedMessage());
        }
    }



}
