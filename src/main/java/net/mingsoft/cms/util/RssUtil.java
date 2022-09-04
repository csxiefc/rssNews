package net.mingsoft.cms.util;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.alibaba.fastjson.JSON;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;


public class RssUtil {

    /**
     * 根据链接地址解析数据
     *
     * @param url
     *            RSS形式的xml文件
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void praseXml2(URL url) throws Exception {

        SyndFeed feed = null;

        SyndFeedInput input = new SyndFeedInput();
        // 创建链接对象
        URLConnection conn = url.openConnection();

        String content_encoding = conn.getHeaderField("Content-Encoding");

        if (content_encoding != null && content_encoding.contains("gzip")) {

            System.out.println("conent encoding is gzip");

            GZIPInputStream gzin = new GZIPInputStream(conn.getInputStream());

            feed = input.build(new XmlReader(gzin));
        } else {
            //1.得到此链接的输入流
            //2.读取此输入流
            //3.得到此输入流的有效信息
            feed = input.build(new XmlReader(conn.getInputStream()));
        }
        // 得到XML的所有实体
        List<SyndEntry> entries = feed.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            SyndEntry entry = (SyndEntry) entries.get(i);
            System.out.println(entry.getTitle());
            System.out.println(entry.getPublishedDate());
        }
        System.out.println("feed size:" + feed.getEntries().size());
    }


    /**
     * 根据链接地址解析数据
     *
     * @param url
     *            RSS形式的xml文件
     *
     * @throws Exception
     */
    public static List<SyndEntry> praseXml(String url) throws Exception {
        List<SyndEntry> result = null;
        SyndFeed feed = null;
        SyndFeedInput input = new SyndFeedInput();
        // 创建链接对象
        URL urlObj = new URL(url);
        URLConnection conn = urlObj.openConnection();
        String content_encoding = conn.getHeaderField("Content-Encoding");
        if (content_encoding != null && content_encoding.contains("gzip")) {
            System.out.println("conent encoding is gzip");
            GZIPInputStream gzin = new GZIPInputStream(conn.getInputStream());
            feed = input.build(new XmlReader(gzin));
        } else {
            //1.得到此链接的输入流
            //2.读取此输入流
            //3.得到此输入流的有效信息
            feed = input.build(new XmlReader(conn.getInputStream()));
        }
        // 得到XML的所有实体
        result = feed.getEntries();
        return result;
    }

    public static void main(String[] args) throws Exception {
        // "https://www.chinanews.com.cn/rss/scroll-news.xml";
        String str = "https://www.msra.cn/feed";
        RssUtil rssPrase = new RssUtil();
        rssPrase.praseXml2(new URL(str));
    }

}