package bjzb.simplenetdemo.poolandstack;

import java.io.IOException;

import bjzb.simplenetdemo.request.Request;
import bjzb.simplenetdemo.response.Response;

/**
 * Created by bjhl-penghaoyou on 16/11/12.
 * 真正的网络执行者
 */

public interface HttpStack {
    /**
     * 执行http网络请求
     * @param request 待执行的请求
     * @return 响应
     */
    Response performRequest(Request request) throws IOException;
}
