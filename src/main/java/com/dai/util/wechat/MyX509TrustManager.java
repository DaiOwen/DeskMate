package com.dai.util.wechat;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 证书信任管理器（用于https请求）
 * 
 * @author xiangze
 * 
 */
public class MyX509TrustManager implements X509TrustManager {

	/**
	 * 该方法检查客户端的证书，若不信任该证书则抛出异常。 由于我们不需要对客户端进行认证，因此我们只需要执行默认的信任管理器的这个方法。
	 * JSSE中，默认的信任管理器类为TrustManager
	 */
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	/**
	 * 该方法检查服务器的证书，若不信任该证书同样抛出异常。通过自己实现该方法，可以使之信任我们指定的任何证书。
	 * 在实现该方法时，也可以简单的不做任何处理，即一个空的函数体，由于不会抛出异常，它就会信任任何证书。
	 */
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	/**
	 * 返回受信任的X509证书数组
	 */
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}