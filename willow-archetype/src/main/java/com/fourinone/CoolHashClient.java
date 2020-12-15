package com.fourinone;

public interface CoolHashClient extends CoolHash {

  void begin();

  void rollback();

  void commit();

  void exit();

  Result operateAsyn(String methodname, Class[] argsType, Object[] argsValue);//2015.07.15
}