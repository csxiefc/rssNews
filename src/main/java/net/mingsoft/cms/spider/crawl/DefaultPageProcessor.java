package net.mingsoft.cms.spider.crawl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Slf4j
@Component
public class DefaultPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        // page.addTargetRequests(page.getHtml().links().regex("https://www\\.zhihu\\.com/question/\\d+/answer/\\d+.*").all());

        page.addTargetRequests(page.getHtml().links().regex("https://www.chinanews.com.cn/cj/2022/09-02/9843370.shtml").all());
        page.putField("title", page.getHtml().xpath("//h1[@class='content_left_title']/text()").toString());
        if (page.getResultItems().get("title") == null) {
            // 如果是列表页，跳过此页，pipeline不进行后续处理
            page.setSkip(true);
        }

        page.putField("content", page.getHtml().xpath("//div[@class='left_zw']/p[1]/text()").toString());
        page.putField("left_ph_img", page.getHtml().xpath("//div[@class='left_ph']/img[1]/@src").toString());
        page.putField("left_ph_txt", page.getHtml().xpath("//div[@class='left_ph']/img[1]/@alt").toString());
        page.putField("left_pt", page.getHtml().xpath("//div[@class='left_pt']/text()").toString());

        System.out.println(JSON.toJSONString(page.getResultItems()));
        /**/
        Document doc = page.getHtml().getDocument();
        page.putField("newsdate", doc.getElementById("newsdate").val());
        page.putField("newstime", doc.getElementById("newstime").val());
        page.putField("editorname", doc.getElementById("editorname").val());
        page.putField("title2", doc.getElementById("newstitle").val());






    }

    @Override
    public Site getSite() {
        return site;
    }
}
