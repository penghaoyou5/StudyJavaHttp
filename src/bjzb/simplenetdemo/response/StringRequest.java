package bjzb.simplenetdemo.response;

import bjzb.simplenetdemo.request.HttpMethod;
import bjzb.simplenetdemo.request.Request;
import bjzb.simplenetdemo.request.RequestListener;

/**
 * Created by bjhl-penghaoyou on 16/11/15.
 */

public class StringRequest extends Request<String> {
    /**
     * @param method          请求方法
     * @param url             请求地址
     * @param requestListener 请求地址回调
     */
    public StringRequest(HttpMethod method, String url, RequestListener<String> requestListener) {
        super(method, url, requestListener);
    }

    /**
     * 将Response 转化为String字符串
     * @param response
     * @return
     */
    @Override
    protected String parseResponse(Response response) {
        byte[] rawData = response.getRawData();
        if(rawData!=null&&rawData.length>0){
            String str = new String(rawData);
            return str;
        }
        return null;
    }
}
