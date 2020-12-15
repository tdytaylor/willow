package com.fourinone;

public interface WorkerLocal extends WorkerProxy {

  WareHouse doTask(WareHouse inhouse);

  WareHouse doTask(WareHouse inhouse, long timeoutseconds);

  void interrupt();

  String getHost();

  int getPort();
}