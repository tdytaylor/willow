package org.willow.core;

import io.grpc.ServerBuilder;
import java.util.List;

/**
 * @author tdytaylor
 */
public class DataNode {

  private List<String> clusterAddress;

  public void run() {
    ServerBuilder.forPort(1089);
  }
}
