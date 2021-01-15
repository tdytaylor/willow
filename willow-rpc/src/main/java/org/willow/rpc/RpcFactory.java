package org.willow.rpc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tdytaylor
 */
public class RpcFactory {

  private static final Map<Class<?>, RpcClassHolder> RPC_CLASS_HOLDER_MAP
      = new ConcurrentHashMap<>(128);

  private static final Map<String, Class<?>> NAME_CLASS_MAP
      = new ConcurrentHashMap<>(128);

  private static final Map<Class<?>, Object> NAME_INSTANCE_MAP
      = new ConcurrentHashMap<>(128);

  private static ReentrantLock LOCK = new ReentrantLock();

  /**
   * 注册服务
   *
   * @param holder
   * @return
   */
  public Object register(RpcClassHolder holder) {
    if (holder.getCla().isInterface()) {
      throw new IllegalArgumentException("注册的实例为null");
    }
    Object instance = null;
    LOCK.lock();
    try {
      RPC_CLASS_HOLDER_MAP.put(holder.getCla(), holder);
      for (Class<?> anInterface : holder.getCla().getInterfaces()) {
        NAME_CLASS_MAP.put(anInterface.getName(), holder.getCla());
      }
      instance = holder.getCla().newInstance();
      NAME_INSTANCE_MAP.put(holder.getCla(), instance);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } finally {
      LOCK.unlock();
    }
    return instance;
  }

  /**
   * @param cla
   * @param params
   * @return
   */
  public Object invoke(Class<?> cla, Object[] params) {

    return null;
  }
}
