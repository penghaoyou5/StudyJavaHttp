package bjzb.simplenetdemo.poolandstack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bjzb.simplenetdemo.request.Request;
import bjzb.simplenetdemo.response.Response;

/**
 * Created by bjhl-penghaoyou on 16/11/12.
 * 使用HttpUrlConnection 执行网络请求的HttpStack
 */

public class HttpUrlConnStack implements HttpStack {

    @Override
    public Response performRequest(Request request) throws IOException {
        //构建HttpUrlConnection对象
        HttpURLConnection httpURLConnection = creatUrlConnection(request.getUrl());
        //设置头部数据
        setRequestHeaders(httpURLConnection,request);
        //进行参数写入
        setRequestParams(httpURLConnection,request);
        //进行网络请求 返回数据
        Response response = fetchResponse(httpURLConnection);
        addHeadersToResponse(response,httpURLConnection);
        return response;
    }


    private HttpURLConnection creatUrlConnection(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        return urlConnection;
    }

    private void setRequestHeaders(HttpURLConnection httpURLConnection, Request request) throws ProtocolException {

        httpURLConnection.setDoInput(true);

        //设置请求行
        httpURLConnection.setRequestMethod(request.getmMethod().getmMethod());

        //设置请求头
        Set<Map.Entry<String, String>> entries = request.getHeaders().entrySet();
        for (Map.Entry<String, String> entry : entries) {
            httpURLConnection.addRequestProperty(entry.getKey(),entry.getValue());
        }
    }

    private void setRequestParams(HttpURLConnection httpURLConnection, Request request) throws IOException {
        byte[] body = request.getBody();
        if(body!=null&&body.length>0){
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type",request.getBodyContentType());
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(body);
            outputStream.close();
        }
    }

    private Response fetchResponse(HttpURLConnection httpURLConnection) throws IOException {
        httpURLConnection.connect();

        //构建 Response
        Response response = new Response(httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());

        //设置 response 数据
        InputStream inputStream = null;
        try {
           inputStream  = httpURLConnection.getInputStream();
        }catch (IOException exception){
            inputStream = httpURLConnection.getErrorStream();
        }finally {
            
        }

        int contentLength = httpURLConnection.getContentLength();
        String contentEncoding = httpURLConnection.getContentEncoding();
        String contentType = httpURLConnection.getContentType();

        Response.HttpEntity httpEntity = new Response.HttpEntity(inputStream, contentLength, contentEncoding, contentType);
        byte[] rawByte = httpEntity.getRawByte();
        response.setRawData(rawByte);
        return response;
    }


    /**
     * 添加头部
     * @param response
     * @param httpURLConnection
     */
    private void addHeadersToResponse(Response response, HttpURLConnection httpURLConnection) {
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        response.setHeaderFields(headerFields);
    }

}
