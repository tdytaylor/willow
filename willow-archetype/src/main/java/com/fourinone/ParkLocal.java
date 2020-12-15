package com.fourinone;

import java.io.Serializable;
import java.util.List;

public interface ParkLocal {

  ObjectBean create(String domain, Serializable obj);

  ObjectBean create(String domain, String node, Serializable obj);

  ObjectBean create(String domain, String node, Serializable obj, AuthPolicy auth);

  ObjectBean create(String domain, String node, Serializable obj, boolean heartbeat);

  ObjectBean create(String domain, String node, Serializable obj, AuthPolicy auth,
      boolean heartbeat);

  ObjectBean update(String domain, String node, Serializable obj);

  ObjectBean get(String domain, String node);

  ObjectBean getLastest(String domain, String node, ObjectBean ob);

  List<ObjectBean> get(String domain);

  List<ObjectBean> getLastest(String domain, List<ObjectBean> oblist);

  ObjectBean delete(String domain, String node);

  boolean setDeletable(String domain);

  List<ObjectBean> delete(String domain);

  void addLastestListener(String domain, String node, ObjectBean ob, LastestListener liser);

  void addLastestListener(String domain, List<ObjectBean> oblist, LastestListener liser);
}