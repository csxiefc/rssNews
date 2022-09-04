package net.mingsoft.cms.action.search.interceptor;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;

/**
 * @author :yepk
 * @version :1.0
 * @apiNote :solr登录拦截器
 * @date :2021-06-08-15:02
 */

public class SolrAuthInterceptor implements HttpRequestInterceptor {
    @Override
    public void process(final HttpRequest request, final HttpContext context) {
        AuthState authState = (AuthState) context.getAttribute(HttpClientContext.TARGET_AUTH_STATE);
        if (authState.getAuthScheme() == null) {
            CredentialsProvider provider =
                    (CredentialsProvider) context.getAttribute(HttpClientContext.CREDS_PROVIDER);
            HttpHost httpHost = (HttpHost) context.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
            AuthScope scope = new AuthScope(httpHost.getHostName(), httpHost.getPort());
            Credentials credentials = provider.getCredentials(scope);
            authState.update(new BasicScheme(), credentials);
        }
    }
}

