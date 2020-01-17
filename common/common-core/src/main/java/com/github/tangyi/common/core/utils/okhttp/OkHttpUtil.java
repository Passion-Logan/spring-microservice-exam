package com.github.tangyi.common.core.utils.okhttp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tangyi.common.core.utils.JsonMapper;

import okhttp3.*;

/**
 * okHttp工具类
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 15:15
 */
public class OkHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    /**
     * 单例对象
     */
    private static OkHttpUtil instance;

    /**
     * okHttpClient
     */
    private OkHttpClient okHttpClient;

    public OkHttpUtil() {
        // 创建httpClient
        okHttpClient = new OkHttpClient().newBuilder()
            // 设置连接的超时时间
            .connectTimeout(10, TimeUnit.SECONDS)
            // 设置响应的超时时间
            .writeTimeout(30, TimeUnit.SECONDS)
            // 请求的超时时间
            .readTimeout(30, TimeUnit.SECONDS)
            // 配置忽略证书
            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
            // 日志拦截器
            .addInterceptor(new LogInterceptor()).build();
    }

    /**
     * 获取实例
     *
     * @return
     */
    public synchronized static OkHttpUtil getInstance() {
        if (instance == null) {
            instance = new OkHttpUtil();
        }
        return instance;
    }

    /**
     * get请求
     *
     * @param url
     * @param header
     * @return
     * @throws IOException
     */
    public String get(String url, Map<String, Object> header) throws IOException {
        return getResponseBody(url, header).string();
    }

    /**
     * get请求获取响应体
     *
     * @param url
     * @param header
     * @return
     * @throws IOException
     */
    public ResponseBody getResponseBody(String url, Map<String, Object> header) throws IOException {
        return getResponse(url, header).body();
    }

    /**
     * 获取响应对象
     *
     * @param url
     * @param header
     * @return
     * @throws IOException
     */
    public Response getResponse(String url, Map<String, Object> header) throws IOException {
        Request.Builder builder = new Request.Builder().url(url).get();
        for (String key : header.keySet()) {
            builder.addHeader(key, header.get(key).toString());
        }
        return okHttpClient.newCall(builder.build()).execute();
    }

    /**
     * post请求
     *
     * @param url
     * @param header
     * @param data
     * @return
     */
    public String postJson(String url, Map<String, Object> header, Map<String, Object> data) {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            String json = mapToJSONObject(data).toString();
            logger.debug("postJson json:{}", json);
            RequestBody body = RequestBody.create(mediaType, json);
            Request.Builder builder = new Request.Builder().url(url).post(body);
            for (String key : header.keySet()) {
                builder.addHeader(key, header.get(key).toString());
            }
            return okHttpClient.newCall(builder.build()).execute().body().string();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * post请求
     *
     * @param url
     * @param header
     * @param object
     * @return
     */
    public String postJson(String url, Map<String, Object> header, Object object) {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            String json = JsonMapper.getInstance().toJson(object);
            logger.debug("postJson json:{}", json);
            RequestBody body = RequestBody.create(mediaType, json);
            Request.Builder builder = new Request.Builder().url(url).post(body);
            for (String key : header.keySet()) {
                builder.addHeader(key, header.get(key).toString());
            }
            return okHttpClient.newCall(builder.build()).execute().body().string();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * post请求
     *
     * @param url
     * @param header
     * @param data
     * @return
     * @throws IOException
     */
    public String postForm(String url, Map<String, Object> header, Map<String, Object> data) throws IOException {
        logger.debug("post data:{}", data);
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (String key : data.keySet()) {
            formBuilder.add(key, data.get(key).toString());
        }
        Request.Builder builder = new Request.Builder().url(url).post(formBuilder.build());
        for (String key : header.keySet()) {
            builder.addHeader(key, header.get(key).toString());
        }
        return okHttpClient.newCall(builder.build()).execute().body().string();
    }

    /**
     * post请求
     *
     * @param url
     * @param header
     * @param filePath
     * @return
     * @throws IOException
     */
    public String postFile(String url, Map<String, Object> header, String filePath) throws IOException {
        Request.Builder builder = new Request.Builder().url(url)
            .post(RequestBody.create(MediaType.parse("application/json"), new File(filePath)));
        for (String key : header.keySet()) {
            builder.addHeader(key, header.get(key).toString());
        }
        Response response = okHttpClient.newCall(builder.build()).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("请求接口失败");
        }
        return response.body().string();
    }

    /**
     * put 请求，用于上传文件
     *
     * @param url
     * @param header
     * @param file
     * @return
     * @throws IOException
     */
    public String put(String url, Map<String, Object> header, String file) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new File(file));
        Request.Builder builder = new Request.Builder().url(url).put(body);
        for (String key : header.keySet()) {
            builder.addHeader(key, header.get(key).toString());
        }
        Response response = okHttpClient.newCall(builder.build()).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("请求接口失败");
        }
        return response.body().string();
    }

    /**
     * 上传字节数组
     *
     * @param url
     * @param header
     * @param bytes
     * @return
     * @throws IOException
     */
    public String putBytes(String url, Map<String, Object> header, byte[] bytes) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), bytes);
        Request.Builder builder = new Request.Builder().url(url).put(body);
        for (String key : header.keySet()) {
            builder.addHeader(key, header.get(key).toString());
        }
        Response response = okHttpClient.newCall(builder.build()).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("请求接口失败");
        }
        return response.body().string();
    }

    /**
     * 上传流
     *
     * @param url
     * @param header
     * @param inputStream
     * @return
     * @throws IOException
     */
    public String putStream(String url, Map<String, Object> header, InputStream inputStream) throws IOException {
        RequestBody body = OkHttpRequestBodyUtil.create(MediaType.parse("application/json"), inputStream);
        Request.Builder builder = new Request.Builder().url(url).put(body);
        for (String key : header.keySet()) {
            builder.addHeader(key, header.get(key).toString());
        }
        Response response = okHttpClient.newCall(builder.build()).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("请求接口失败");
        }
        return response.body().string();
    }

    /**
     * patch请求
     *
     * @param url
     * @param header
     * @param data
     * @return
     * @throws IOException
     */
    public String patch(String url, Map<String, Object> header, Map<String, Object> data) throws IOException {
        logger.debug("patch data:{}", data);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, mapToJSONObject(data).toString());
        Request.Builder builder = new Request.Builder().url(url).patch(body);
        for (String key : header.keySet()) {
            builder.addHeader(key, header.get(key).toString());
        }
        return okHttpClient.newCall(builder.build()).execute().body().string();
    }

    /**
     * 删除请求
     *
     * @param url
     * @param header
     * @return
     * @throws IOException
     */
    public String delete(String url, Map<String, Object> header) throws IOException {
        return deleteResponse(url, header).body().string();
    }

    /**
     * 删除请求
     *
     * @param url
     * @param header
     * @return
     * @throws IOException
     */
    public Response deleteResponse(String url, Map<String, Object> header) throws IOException {
        Request.Builder builder = new Request.Builder().url(url).delete();
        for (String key : header.keySet()) {
            builder.addHeader(key, header.get(key).toString());
        }
        return okHttpClient.newCall(builder.build()).execute();
    }

    private JSONObject mapToJSONObject(Map<String, Object> data) {
        JSONObject jsonObject = new JSONObject();
        for (String key : data.keySet()) {
            Object object = data.get(key);
            // 子节点
            if (object instanceof HashMap) {
                Map childMap = (Map)object;
                JSONObject childJsonObject = new JSONObject();
                for (Object childKey : childMap.keySet()) {
                    childJsonObject.put(childKey.toString(), childMap.get(childKey));
                }
                object = childJsonObject;
            }
            jsonObject.put(key, object);
        }
        return jsonObject;
    }
}
