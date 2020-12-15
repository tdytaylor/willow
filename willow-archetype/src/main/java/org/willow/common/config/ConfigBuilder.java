package org.willow.common.config;

/**
 * @author tdytaylor
 */
public final class ConfigBuilder {

  private static String configPath = null;

  /**
   * 设置配置文件路径
   *
   * @param path
   */
  public static void init(String path) {
    ConfigBuilder.configPath = path;
  }
}
