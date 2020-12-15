package com.fourinone;

import java.io.Serializable;
import java.util.Map;

public interface CacheLocal {

  /*public String add(String type, Serializable obj);
  public Object get(String type, String keyid);
  public Object putByKey(String keyId, String name, Serializable obj);
  public Object getByKey(String keyid, String name);
  */
  String add(String name, Serializable obj);

  Object put(String keyid, String name, Serializable obj);

  Object get(String keyid, String name);

  Map get(String keyid);

  Object remove(String keyid, String name);

  Map remove(String keyid);
}