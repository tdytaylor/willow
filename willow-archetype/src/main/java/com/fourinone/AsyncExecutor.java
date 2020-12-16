package com.fourinone;

/**
 * @author null
 * AsyncExecutor
 */
public abstract class AsyncExecutor {

  public abstract void task();

  public void run() {
    try {
      new Thread(() -> task()).start();
    } catch (Exception e) {
      //e.printStackTrace();
      LogUtil.info("AsyncExector", "task", e);
    }
  }
}