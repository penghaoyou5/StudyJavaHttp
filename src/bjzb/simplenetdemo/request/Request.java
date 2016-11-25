package bjzb.simplenetdemo.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import bjzb.simplenetdemo.queue.RequestQueue;
import bjzb.simplenetdemo.response.Response;

/**
 * Created by bjhl-penghaoyou on 16/11/12.
 *
 * 进行网络请求基类
 * 1.主要进行请求数据保存
 * 以及转化为http格式数据的方法
 *
 *
 *
 * 请求行 请求头      请求方式 请求地址
 * 请求体              请求参数
 */

public abstract class Request<T> {


    HttpMethod mMethod;
    String mUrl;
    RequestListener<T> mRequestListener;


    /**
     * 默认的编码方式
     */
    public static final String DEGAULT_PARAMS_ENDODING = "UTF-8";

    /**
     * 请求的header
     */
    private Map<String,String> mHeaders = new HashMap<>();

    /**
     * 请求参数
     */
    private Map<String,String> mBodyParams = new HashMap<>();


    /**
     * @param method  请求方法
     * @param url      请求地址
     * @param requestListener  请求地址回调
     */
    public Request(HttpMethod method,String url,RequestListener<T> requestListener){
        this.mMethod = method;
        this.mUrl = url;
        this.mRequestListener = requestListener;
    }

    /**
     * 加入请求队列开始进行请求的方法
     */
    public void startRequest(){
        RequestQueue.getInstance().addRequest(this);
    }

    public HttpMethod getmMethod() {
        return mMethod;
    }

    public String getUrl() {
        return mUrl;
    }

    public void addHeaders(String key,String value){
        mHeaders.put(key, value);
    }

    public Map<String, String> getHeaders(){
        return mHeaders;
    }

    public void addBodyParams(String key,String value){
        mBodyParams.put(key, value);
    }

    public String getParamsEncoding(){
        return DEGAULT_PARAMS_ENDODING;
    }

    public String getBodyContentType(){
        return "application/x-www-form-urlencode; charset=" +getParamsEncoding();
    }

    /**
     * @return POST或GET请求时的body的参数字节数据
     */
    public byte[] getBody(){
        if(mBodyParams!=null&&mBodyParams.size()>0){
            try {
                return ecodeParameters(mBodyParams,DEGAULT_PARAMS_ENDODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private byte[] ecodeParameters(Map<String, String> mBodyParams, String paramsEncoding) throws UnsupportedEncodingException {
        StringBuilder encodeParams = new StringBuilder();
        for (Map.Entry<String, String> entry : mBodyParams.entrySet()) {
            //进行参数符号的转换
            String key = URLEncoder.encode(entry.getKey(), paramsEncoding);
            encodeParams.append(key);
            encodeParams.append('=');
            String value = URLEncoder.encode(entry.getValue(), paramsEncoding);
            encodeParams.append(value);
            encodeParams.append('&');
        }
        return encodeParams.toString().getBytes();
    }


    /**
     * 请求结果出现以后 首先在子线程中的调用 为了顺便先在子线程中进行耗时操作
     * 随后调用  {@link Request}  diviveryResponseInMainThread
     * @param response
     */
    public void diviveryResponseInZiThread(Response response) {
        //这里进行请求结果的解析   一个请求对应一个响应类
        result = parseResponse(response);

    }

    T result;
    protected abstract T parseResponse(Response response);

    /**
     * 最后将请求结果分发给主线程
     * 在 diviveryResponseInZiThread之后调用
     * @param response
     */
    public void diviveryResponseInMainThread(Response response){
        if(mRequestListener!=null){
            mRequestListener.onComplete(response.getmCode(),result,response.getmReason());
        }
    }



}
