package com.github.tangyi.common.core.utils.okhttp;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;

/**
 * SSLSocketFactory
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 15:24
 */
public class SSLSocketClient {

    /**
     * 获取这个SSLSocketFactory
     *
     * @return
     */
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取TrustManager
     *
     * @return
     */
    private static TrustManager[] getTrustManager() {
        return new TrustManager[] {new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }
        }};
    }

    /**
     * 获取HostnameVerifier
     *
     * @return
     */
    public static HostnameVerifier getHostnameVerifier() {
        return (requestedHost, remoteServerSession) -> requestedHost.equals(remoteServerSession.getPeerHost());
    }

}
