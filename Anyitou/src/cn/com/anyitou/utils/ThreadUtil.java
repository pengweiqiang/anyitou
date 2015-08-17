package cn.com.anyitou.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程管理类
 * 
 * @author Neusoft
 * 
 */
public class ThreadUtil {

    private static ExecutorService mCached;
    private static ExecutorService mFixed;
    private static final int       THREAD_COUNT = 5;
    
    private static ExecutorService mReloadCached;
    private static ExecutorService mReloadFixed;
    private static final int       RELOAD_THREAD_COUNT = 2;
    
    private static ExecutorService mDeleteCached;
    private static ExecutorService mDeleteFixed;
    private static final int       DELETE_THREAD_COUNT = 5;

    /**
     * 返回cached线程池
     */
    public static synchronized ExecutorService getTheadPool( boolean isCached ) {
        if ( isCached ) {
            // 队列长度无限制线程池
            if ( mCached == null ) {
                mCached = Executors.newCachedThreadPool();
            }
            return mCached;
        }
        else {
            // 限制队列长度线程池
            if ( mFixed == null ) {
                mFixed = Executors.newFixedThreadPool( THREAD_COUNT );
            }
            return mFixed;
        }

    }
    
    /**
     * 返回cached线程池
     */
    public static synchronized ExecutorService getReloadTheadPool( boolean isCached ) {
        if ( isCached ) {
            // 队列长度无限制线程池
            if ( mReloadCached == null ) {
                mReloadCached = Executors.newCachedThreadPool();
            }
            return mReloadCached;
        }
        else {
            // 限制队列长度线程池
            if ( mReloadFixed == null ) {
                mReloadFixed = Executors.newFixedThreadPool( RELOAD_THREAD_COUNT );
            }
            return mReloadFixed;
        }
    }

    /**
     * 返回cached线程池
     */
    public static synchronized ExecutorService getDeleteTheadPool( boolean isCached ) {
        if ( isCached ) {
            // 队列长度无限制线程池
            if ( mDeleteCached == null ) {
                mDeleteCached = Executors.newCachedThreadPool();
            }
            return mDeleteCached;
        }
        else {
            // 限制队列长度线程池
            if ( mDeleteFixed == null ) {
                mDeleteFixed = Executors.newFixedThreadPool( DELETE_THREAD_COUNT );
            }
            return mDeleteFixed;
        }
    }

}
