package bjzb.simplenetdemo.request;

/**
 * Created by bjhl-penghaoyou on 16/11/12.
 * 请求方式
 */

public enum HttpMethod{
    GET("GET"),POST("POST"),PUT("PUT"),DELETE("DELETE")
    ;

    String mMethod;

    HttpMethod(String method){
        mMethod = method;
    }

    public String getmMethod() {
        return mMethod;
    }

    @Override
    public String toString() {
        return mMethod;
    }
}
