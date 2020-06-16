package com.zzh.basemodule.base.util;

import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Time 2020/3/28
 * Author Zzh
 * Description
 */
public class ThreadPoolUtils {
    private String TAG = ThreadPoolUtils.class.getSimpleName();
    private HashMap<String, ThreadPoolExecutor> threadMap = new HashMap<>();
    private volatile static ThreadPoolUtils INSTANCE = null;
    //cpu数量
    private int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数为手机cpu数量+1
    private int CORE_POOL_SIZE = CPU_COUNT + 1;
    //最大线程数为手机CPU数量*2+1
    private int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    //线程超时时间,超时会被回收
    private long KEEP_ALIVE_TIME = 3;
    //等待队列大小
    private int QUENE_SIZE = 128;

    private ThreadPoolUtils() {
    }

    public static ThreadPoolUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (ThreadPoolUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ThreadPoolUtils();
                }
            }
        }
        return INSTANCE;
    }

    public void executeTask(String tag, Runnable r) {
        ThreadPoolExecutor threadPool = getThreadPool(tag);
        threadPool.execute(r);

    }

    public boolean removeTask(String tag, Runnable r) {
        ThreadPoolExecutor threadPool = getThreadPool(tag);
        if (threadPool != null) {
            return threadPool.getQueue().remove(r);
        }
        return false;
    }

    //shutDown() : 关闭线程池 不影响已经提交的任务
    //shutDownNow() : 关闭线程池 并尝试终止在执行的任务
    public void quitThreadPool(String tag) {
        ThreadPoolExecutor threadPoolExecutor = threadMap.get(tag);
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
//            threadMap.remove(tag);
        }
    }

    public void delThreadPool(String tag) {
        threadMap.remove(tag);
    }

    public boolean isShutDown(String tag) {
        boolean isShutDown = false;
        ThreadPoolExecutor threadPoolExecutor = threadMap.get(tag);
        if (threadPoolExecutor != null) {
            isShutDown = threadPoolExecutor.isShutdown();
        }
        return isShutDown;
    }

    public boolean isTerminated(String tag) {
        boolean isTerminated = false;
        ThreadPoolExecutor threadPoolExecutor = threadMap.get(tag);
        if (threadPoolExecutor != null) {
            isTerminated = threadPoolExecutor.isTerminated();
        }
        return isTerminated;
    }

    private ThreadPoolExecutor getThreadPool(String tag) {
        ThreadPoolExecutor threadPoolExecutor = threadMap.get(tag);
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(QUENE_SIZE),
                    Executors.defaultThreadFactory(),
                    new RejectedExecutionHandler() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            Log.e(TAG, "RejectedExecutionHandler");
                        }
                    }
            );
            threadPoolExecutor.allowCoreThreadTimeOut(true);
            threadMap.put(tag, threadPoolExecutor);
        }
        return threadPoolExecutor;
    }

}
