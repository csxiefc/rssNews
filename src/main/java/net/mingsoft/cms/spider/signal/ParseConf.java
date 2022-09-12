package net.mingsoft.cms.spider.signal;

import lombok.Data;

@Data
public class ParseConf {

    /**
     * 标题
     */
    private String titleXpath;

    /**
     * 子标题
     */
    private String subTitleXpath;

    /**
     * 正文
     */
    private String contentXpath;

    /**
     * 配图-资源地址
     */
    private String imgHtmlXpath;

    /**
     * 配图-文字说明
     */
    private String imgDescXpath;

    /**
     * 直播或者视频
     */
    private String videoXpath;

    /**
     * 创建人
     */
    private String creatorIdXpath;

    /**
     * 编辑人
     */
    private String editorNameXpath;
}
