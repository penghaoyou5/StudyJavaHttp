package bjzb.simplenetdemo.request;

/**
 * Created by bjhl-penghaoyou on 16/11/12.
 */

public interface RequestListener<T> {
    public void onComplete(int code,T result,String errMg);
}
