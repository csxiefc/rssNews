package net.mingsoft.cms.spider.signal;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.ContentEntity;
import net.mingsoft.cms.entity.RssItemEntity;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.util.Date;

@Slf4j
public class ArticleParseImplBiz implements IArticleParseBiz {

    public AjaxResult<ContentEntity> parse(RssItemEntity item) {
        AjaxResult<ContentEntity> result = this.parse(item.getLink());
        if(result.getCode() != 200) { // 解析非成功情况下，直接返回
            return result;
        }
        result.getData().setCategoryId("1565900410928119810"); // 测试-头条资讯
        result.getData().setContentType("h");
        result.getData().setContentDisplay("0");
        result.getData().setContentSort(0);
        result.getData().setContentUrl(item.getLink());
        result.getData().setContentSource(item.getLink());
        result.getData().setContentDatetime(item.getPubdate());
        result.getData().setContentHit(0);
        result.getData().setCreateBy("57");
        result.getData().setCreateDate(new Date());
        result.getData().setUpdateBy("57");
        result.getData().setUpdateDate(new Date());
        return result;
    }

    @Override
    public AjaxResult<ContentEntity> parse(String url) {
        ContentEntity result = new ContentEntity();
        try {
            JXDocument jxd = JXDocument.createByUrl(url);
            //Xpath语句
            String titleXpath = "//h1[@class='content_left_title']/text()";
            String contentXpath = "//div[@class='left_zw']/html()";
            String imgHtmlXpath = "//div[@class='left_ph']/html()";
            String imgDescXpath = "//div[@class='left_pt']/text()";
            String creatorIdXpath = "//input[@id='creator']/@value";
            String editorNameXpath = "//input[@id='editorname']/@value";
            String title2Xpath = "//input[@id='newstitle']/@value";
            // 组装
            StringBuffer content = new StringBuffer(100);
            JXNode imgHtmlNode = jxd.selNOne(imgHtmlXpath);
            JXNode imgDescNode = jxd.selNOne(imgDescXpath);
            JXNode contentNode = jxd.selNOne(contentXpath);
            if(imgHtmlNode != null) {
                content.append("<div style='text-align: center;'>"+imgHtmlNode.asString()+"</div>");
            }
            if(imgDescNode != null) {
                content.append("<br/>"+imgDescNode.asString());
            }
            if(contentNode != null) {
                content.append("<br/>"+contentNode.asString());
            }
            result.setContentTitle(jxd.selNOne(titleXpath).asString());
            result.setContentDetails(content.toString());
            result.setContentAuthor(jxd.selNOne(editorNameXpath) != null ? jxd.selNOne(editorNameXpath).asString() : null);
            result.setContentImg("[]"); // jxd.selNOne(imgXpath) != null ? jxd.selNOne(imgXpath).asString() : null
            return AjaxResult.success(result);
        } catch (Exception e) { //
            log.error("parse url异常",e);
            return AjaxResult.error(ExceptionUtils.getStackTrace(e),result);
        }
    }

    public ContentEntity test(String url) {
        return null;
    }

}
