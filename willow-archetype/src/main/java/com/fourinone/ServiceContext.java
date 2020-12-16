package com.fourinone;

import java.rmi.RemoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceContext extends BeanService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceContext.class);

  static <I extends ParkActive> void startService(String host, int port, String sn, I i) {
    try {
      putBean(host, false, port, sn, i);
    } catch (Exception e) {
      LogUtil.info("[ObjectService]", "[startService]", e);
    }
  }

  static <I extends ParkActive> void startService(String host, int port, String sn,
      I i, String cb, String pl) {
    try {
      putBean(host, false, port, sn, i, cb, pl, new ParkManager());
      //pm.checkPermission(new ParkPermission("park","all"));
    } catch (Exception e) {
      LogUtil.info("[ObjectService]", "[startService]", e);
    }
  }

  static <I extends ParkActive> I getService(Class<I> a, String host, int port, String sn) {
    I i = null;
    try {
      i = (I) getBean(host, port, sn);
    } catch (RemoteException e) {
      LogUtil.info("[ObjectService]", "[getService]", e);
    }
    return i;
  }
}