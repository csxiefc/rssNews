package net.mingsoft.cms.spider.signal;

import lombok.Data;

@Data
public class Article implements java.io.Serializable {

    private String title;

    private String subTitle;

    private String content;

    private String link;

    private String keyword;

    private String description;

    private String img;

    private String source;

    private String author;

    private String pubdate;

}
