package com.fourinone;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池
 */
//java.io.Closeable
public class PoolExecutor {

  private static final Logger LOGGER = LoggerFactory.getLogger(PoolExecutor.class);

  private static ThreadPoolExecutor tpe;//rm static, new everytime
  private static ScheduledThreadPoolExecutor stpe;//rm static, new everytime

  /**
   * 设置线程池
   *
   * @return
   */
  static synchronized ThreadPoolExecutor tpe() {
    if (tpe == null) {
      //System.out.println(Thread.currentThread()+":new ThreadPoolExecutor...");
      LOGGER.info("{}:new ThreadPoolExecutor...", Thread.currentThread());
      int corePoolSize = ConfigContext.getInitServices();
      int maximumPoolSize = ConfigContext.getMaxServices();
      long keepAliveTime = 3000;
      TimeUnit unit = TimeUnit.MILLISECONDS;
      BlockingQueue<Runnable> waitQueue = new ArrayBlockingQueue<>(2000);
      //ThreadPoolExecutor.CallerRunsPolicy();
      RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
      tpe = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
          keepAliveTime, unit, waitQueue, handler);
      //System.out.println(Thread.currentThread()+":new done.");
      LOGGER.info("{}::new done.", Thread.currentThread());
    }
    return tpe;
  }

  static ScheduledThreadPoolExecutor stpe() {
    if (stpe == null) {
      int corePoolSize = ConfigContext.getInitServices();
      stpe = new ScheduledThreadPoolExecutor(corePoolSize,
          new WillowThreadFactory("scheduled"));
    }
    return stpe;
  }

  static void execute(Runnable d, Runnable i, long t) {
    tpe().execute(d);
    if (t > 0) {
      stpe().schedule(i, t, TimeUnit.SECONDS);
    }
  }

  static synchronized void close() {
    if (tpe != null) {
      try {
        //System.out.println(Thread.currentThread()+":shutdown ThreadPoolExecutor...");
        while (tpe.getActiveCount() != 0) {
          //2015.8.13
        }
        tpe.shutdown();
        //tpe.awaitTermination(1, TimeUnit.DAYS);
        tpe = null;
      } catch (SecurityException se) {
        LogUtil.info("[tpe]", "[close]", "[Error Exception:]", se);
      }
    }
    if (stpe != null) {
      try {
        stpe.shutdown();
        stpe = null;
      } catch (SecurityException se) {
        LogUtil.info("[stpe]", "[close]", "[Error Exception:]", se);
      }
    }
  }

  /**
   * The default thread factory.
   */
  private static class WillowThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public WillowThreadFactory(String specialName) {
      SecurityManager s = System.getSecurityManager();
      group = (s != null) ? s.getThreadGroup() :
          Thread.currentThread().getThreadGroup();
      namePrefix = specialName + "-pool" + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
      Thread t = new Thread(group, r,
          namePrefix + threadNumber.getAndIncrement(),
          0);
      if (t.isDaemon()) {
        t.setDaemon(false);
      }
      if (t.getPriority() != Thread.NORM_PRIORITY) {
        t.setPriority(Thread.NORM_PRIORITY);
      }
      return t;
    }
  }
}