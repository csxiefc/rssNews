package net.mingsoft.cms.action.search.config;

import net.mingsoft.cms.action.search.interceptor.SolrAuthInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class SolrConfig {

    @Value("${spring.data.solr.username}")
    private String username;
    @Value("${spring.data.solr.password}")
    private String password;
    @Value("${spring.data.solr.host}")
    private String uri;

    /***
     * @apiNote 配置solr账号密码
     * @author yepk
     * @date 2021-6-8 15:05
     * 拦截器 {@link SolrAuthInterceptor}
     * @return {@link HttpSolrClient }
     */
    @Bean
    public HttpSolrClient solrClient() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        final URI uri = URI.create(this.uri);
        provider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()),
                new UsernamePasswordCredentials(username, password));
        HttpClientBuilder builder = HttpClientBuilder.create();
        // 指定拦截器，用于设置认证信息
        builder.addInterceptorFirst(new SolrAuthInterceptor());
        builder.setDefaultCredentialsProvider(provider);
        CloseableHttpClient httpClient = builder.build();
        return new HttpSolrClient.Builder(this.uri).withHttpClient(httpClient).build();
    }
}
