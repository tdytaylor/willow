package org.willow.rpc;

/**
 * rpc-core
 *
 * @author tdytaylor
 */
public abstract class RpcBuilder<T> {

  private String address;
  private int port;
  private Class<T> targetClass;

  public RpcBuilder(String address, int port, Class<T> targetClass) {
    this.address = address;
    this.port = port;
    this.targetClass = targetClass;
  }

  /**
   * 获取代理类
   *
   * @return
   */
  public abstract T instance();
}
