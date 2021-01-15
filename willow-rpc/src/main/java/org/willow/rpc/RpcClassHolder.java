package org.willow.rpc;


import java.util.List;

/**
 * @author tdytaylor
 */
public class RpcClassHolder {

  private String name;

  private Class<?> cla;

  private List<String> methods;


  public static final class RpcClassHolderBuilder {

    private String name;
    private Class<?> cla;
    private List<String> methods;

    private RpcClassHolderBuilder() {
    }

    public static RpcClassHolderBuilder aRpcClassHolder() {
      return new RpcClassHolderBuilder();
    }

    public RpcClassHolderBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public RpcClassHolderBuilder withCla(Class<?> cla) {
      this.cla = cla;
      return this;
    }

    public RpcClassHolderBuilder withMethods(List<String> methods) {
      this.methods = methods;
      return this;
    }

    public RpcClassHolder build() {
      RpcClassHolder rpcClassHolder = new RpcClassHolder();
      rpcClassHolder.name = this.name;
      rpcClassHolder.methods = this.methods;
      rpcClassHolder.cla = this.cla;
      return rpcClassHolder;
    }
  }

  public String getName() {
    return name;
  }

  public Class<?> getCla() {
    return cla;
  }

  public List<String> getMethods() {
    return methods;
  }

  @Override
  public String toString() {
    return "RpcClassHolder{" +
        "name='" + name + '\'' +
        ", cla=" + cla +
        ", methods=" + methods +
        '}';
  }
}
