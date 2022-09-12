package net.mingsoft.cms.spider.crawl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Slf4j
@Component
public class GithubRepoPipeline implements Pipeline {


    public void process(ResultItems resultItems, Task task) {
        String author = resultItems.get("author");
        String name = resultItems.get("name");
        String readme = resultItems.get("readme");
        System.out.println("author="+author+",name="+name+",readme="+readme);
        // 存储数据
    }
}