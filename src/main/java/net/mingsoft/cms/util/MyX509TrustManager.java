package net.mingsoft.cms.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
* @author 59552
* @version 创建时间：2019年6月27日 上午11:29:34
* 类说明
*/
public class MyX509TrustManager implements X509TrustManager {
	private static String[] VERIFY_HOST_NAME_ARRAY = new String[]{};
		@Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			// TODO Auto-generated method stub
			for(X509Certificate c:arg0){
				for(String str:VERIFY_HOST_NAME_ARRAY){
					if(str.equals(c.getSigAlgName())){
						c.checkValidity();
					}
				}
			}
			
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			// TODO Auto-generated method stub
			for(X509Certificate c:chain){
				for(String str:VERIFY_HOST_NAME_ARRAY){
					if(str.equals(c.getSigAlgName())){
						c.checkValidity();
					}
				}
			}
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return new X509Certificate[]{};
		}
}