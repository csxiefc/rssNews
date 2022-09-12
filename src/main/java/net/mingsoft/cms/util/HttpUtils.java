package net.mingsoft.cms.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
public class HttpUtils {


    private static HttpUtils instance;

    private RestTemplate restTemplate = new RestTemplate();

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        if (null == instance) {
            instance = new HttpUtils();
        }
        return instance;
    }

    public <T> T postForObject(String url, Object requestParameter,
        Map<String, String> requestHeaders, Class<T> responseType, String authorization) {
        String result = exchange(url, HttpMethod.POST, requestParameter, requestHeaders,authorization);

        return JsonUtil.convertJsonStrToBean(result, responseType);
    }

    public <T> List<T> postForList(String url, Object requestParameter,
        Map<String, String> requestHeaders, Class<T> responseType,String authorization) {
        String result = exchange(url, HttpMethod.POST, requestParameter, requestHeaders,authorization);

        return JsonUtil.convertJsonStrToList(result, responseType);
    }

    public <T> T putForObject(String url, Object requestParameter,
        Map<String, String> requestHeaders, Class<T> responseType,String authorization) {
        String result = exchange(url, HttpMethod.PUT, requestParameter, requestHeaders,authorization);

        return JsonUtil.convertJsonStrToBean(result, responseType);
    }

    public <T> T delForObject(String url, Object requestParameter,
                              Map<String, String> requestHeaders, Class<T> responseType,String authorization) {
        String result = exchange(url, HttpMethod.DELETE, requestParameter, requestHeaders,authorization);

        return JsonUtil.convertJsonStrToBean(result, responseType);
    }

    public <T> T getForObject(String url, Map<String, String> requestHeaders, Class<T> responseType,String authorization) {
        String result = exchange(url, HttpMethod.GET, null, requestHeaders,authorization);

        return JsonUtil.convertJsonStrToBean(result, responseType);
    }

    public <T> List<T> getForList(String url, Map<String, String> requestHeaders,
        Class<T> responseType,String authorization) {
        String result = exchange(url, HttpMethod.GET, null, requestHeaders,authorization);

        return JsonUtil.convertJsonStrToList(result, responseType);
    }
    
    // 增加返回map格式接口-get请求
    public <T> Map<String, Object> getForMap(String url, Map<String, String> requestHeaders, String authorization) {
        String result = exchange(url, HttpMethod.GET, null, requestHeaders,authorization);

        return JsonUtil.convertJsonStrToMap(result);
    }
    
    // 增加返回map格式接口-post请求
    public <T> Map<String, Object> postForMap(String url, Object requestParameter, Map<String, String> requestHeaders, String authorization) {
        String result = exchange(url, HttpMethod.POST, requestParameter, requestHeaders,authorization);

        return JsonUtil.convertJsonStrToMap(result);
    }
    
    // 增加返回String格式接口-get请求
    public <T> String getForString(String url, Map<String, String> requestHeaders, String authorization) {
        String result = exchange(url, HttpMethod.GET, null, requestHeaders,authorization);
        return result;
    }

    /**
     * 
     * @param url
     * @param httpMethod
     * @param requestParameter
     * @param requestHeaders
     * @param authorization jwt调用授权码
     * @return
     */
    private String exchange(String url, HttpMethod httpMethod, Object requestParameter,
        Map<String, String> requestHeaders,String authorization) {
    	String result = "";
        HttpHeaders httpHeaders = new HttpHeaders();
        ensureDefaultHeader(httpMethod, httpHeaders);
        if (null != requestHeaders) {
            for (Entry<String, String> entry : requestHeaders.entrySet()) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }
        }

        if(StringUtils.isNotEmpty(authorization)) {
        	httpHeaders.add(HttpHeaders.AUTHORIZATION, authorization);
        }
        
        HttpEntity<Object> httpEntity;
        if (null != requestParameter) {
            httpEntity = new HttpEntity<Object>(requestParameter, httpHeaders);
        } else {
            httpEntity = new HttpEntity<Object>(httpHeaders);
        }
        //System.setProperty("javax.net.debug", "all");
        
        if(url.startsWith("https://")) {
        	log.debug("https start,url=" + url);
        	restTemplate = new RestTemplate(new HttpsClientRequestFactory());
        	result = restTemplate.exchange(url, httpMethod, httpEntity, String.class).getBody();
        	log.debug("https end,url=" + url);
        }else {
        	 log.debug("http start,url=" + url);
        	 log.debug("http start,url=" + httpEntity.getHeaders());
        	 restTemplate = new RestTemplate();
             result = restTemplate.exchange(url, httpMethod, httpEntity, String.class).getBody();
             log.debug("http end,url=" + url);
        }
    	return result;
    }

    private void ensureDefaultHeader(HttpMethod httpMethod, HttpHeaders httpHeaders) {
        if (HttpMethod.POST == httpMethod) {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        }
    }
    
    
    public static void main(String []args){
        Map<String, String> requestHeaders= new HashMap<String, String>(1);
    	requestHeaders.put(HttpHeaders.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJCQ0FuMkNIRGtvU0RKdjdJcDBrMFdqVkhVUm85NElTRiJ9.zzJtS7TYaaLy-VIANMU96SOw2r9driz-0Qxy-To9ko8"); 
    	String url2 = "http://47.100.56.159:38081/blueland-plateform-render/role/BIO/role/leader/users/luob";
    	String res = getInstance().getForObject(url2,  requestHeaders, String.class,"");
    }
}
