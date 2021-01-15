package org.willow.rpc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.willow.api.HelloImpl;
import org.willow.rpc.RpcClassHolder.RpcClassHolderBuilder;

class RpcFactoryTest {

  @Test
  void register() {
    RpcFactory rpcFactory = new RpcFactory();
    final RpcClassHolder hello = RpcClassHolderBuilder.aRpcClassHolder()
        .withCla(HelloImpl.class)
        .withName("hello")
        .withInterfaceName("org.willow.api.Hello")
        .build();
    rpcFactory.register(hello);
  }

  @Test
  void invoke() {
  }
}