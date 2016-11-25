package bjzb.simplenetdemo.queue;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import bjzb.simplenetdemo.poolandstack.HttpStack;
import bjzb.simplenetdemo.poolandstack.HttpUrlConnStack;
import bjzb.simplenetdemo.request.Request;
import bjzb.simplenetdemo.response.Response;

/**
 * ？泛型通配符
 *
 * Created by bjhl-penghaoyou on 16/11/12.
 * 请求队列的管理类
 */

public class RequestQueue {



    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    /**
     * An {@link Executor} that can be used to execute tasks in parallel.
     */
    public static final Executor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);



    private RequestQueue(){}

    public static RequestQueue requestQueue;

    public static final RequestQueue getInstance(){
        if(requestQueue==null){
            synchronized (RequestQueue.class){
                if(requestQueue==null){
                    requestQueue = new RequestQueue();
                }
            }
        }
        return requestQueue;
    }

    /**
     * 线程安全的请求队列  默认可放128个请求
     */
    private BlockingQueue<Request<?>> mRequestQueue = new ArrayBlockingQueue<Request<?>>(128);

    /**
     * @param request 添加请求到队列中
     */
    public void addRequest(Request<?> request){
        if(mRequestQueue.contains(request))
            mRequestQueue.offer(request);
       startNetWOrkExectors(request);
    }

    /**
     * 用系统线程池进行 请求
     * @param request
     */
    private void startNetWOrkExectors(final Request<?> request){
        THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                	Response response =  new HttpUrlConnStack().performRequest(request);
                  request.diviveryResponseInZiThread(response);
                  request.diviveryResponseInMainThread(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 用自己的请求队列进行 请求
     */
    private void startNetWOrkExectors(){
//        THREAD_POOL_EXECUTOR.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Request<?> request = mRequestQueue.take();
//                    new HttpUrlConnStack().performRequest(request);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
