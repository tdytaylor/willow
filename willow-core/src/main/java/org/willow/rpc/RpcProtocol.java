package org.willow.rpc;

/**
 * @author tdytaylor
 * <p>
 * Rpc 协议
 */
public class RpcProtocol {

  /**
   * 魔数
   */
  private short magic;

  /**
   * 版本号
   */
  private byte version;

  /**
   * 消息ID
   */
  private long messageId;

  /**
   * 序列化方式
   */
  private byte serializationType;

  /**
   * 整体长度
   */
  private int length;

  /**
   * header长度
   */
  private short headLength;
}
