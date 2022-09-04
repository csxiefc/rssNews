package net.mingsoft.cms.spider.signal;

import lombok.extern.slf4j.Slf4j;
import net.mingsoft.cms.entity.AjaxResult;
import net.mingsoft.cms.entity.ContentEntity;
import net.mingsoft.cms.entity.RssItemEntity;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.util.Date;

@Slf4j
public class ArticleParseImplBiz implements IArticleParseBiz {

    public AjaxResult<RssItemEntity> parse(RssItemEntity item) {
        JXDocument jxd = null;
        try {
            jxd = JXDocument.createByUrl(item.getLink());
        } catch (Exception e) { //
            log.error("parseHtml url异常",e);
        }
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
        //item.setTitle(jxd.selNOne(titleXpath).asString());
        item.setLinkContent(content.toString());
        item.setEditor(jxd.selNOne(editorNameXpath) != null ? jxd.selNOne(editorNameXpath).asString() : null);
        item.setThumbnailPath(imgHtmlNode != null ? imgHtmlNode.asString() : null);
        item.setThumbnailDesc(imgDescNode != null ? imgDescNode.asString() : null);

        return AjaxResult.success(item);
    }



}
