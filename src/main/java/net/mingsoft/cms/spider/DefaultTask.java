package net.mingsoft.cms.spider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DefaultTask {

    @Autowired
    private DefaultPipeline pipeline;

    @Autowired
    private DefaultPageProcessor pageProcessor;

    private ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

    public void crawl() {
        Thread.currentThread().setName("RssCrawlerThread");
        try {
            Spider.create(pageProcessor)
                    // 从https://www.xxx.com/explore开始抓
                    .addUrl("https://www.chinanews.com.cn/cj/2022/09-02/9843370.shtml")
                    // 抓取到的数据存数据库
                    .addPipeline(pipeline)
                    // 开启2个线程抓取
                    .thread(2)
                    // 异步启动爬虫
                    .start();
        } catch (Exception ex) {
            log.error("定时抓取数据线程执行异常", ex);
        }
        // 定时任务，每10分钟爬取一次
        /*
        timer.scheduleWithFixedDelay(() -> {
            Thread.currentThread().setName("xxxCrawlerThread");
            try {
                Spider.create(pageProcessor)
                        // 从https://www.xxx.com/explore开始抓
                        .addUrl("https://www.zhihu.com/explore")
                        // 抓取到的数据存数据库
                        .addPipeline(pipeline)
                        // 开启2个线程抓取
                        .thread(2)
                        // 异步启动爬虫
                        .start();
            } catch (Exception ex) {
                log.error("定时抓取数据线程执行异常", ex);
            }
        }, 0, 10, TimeUnit.MINUTES);
         */
    }
}
