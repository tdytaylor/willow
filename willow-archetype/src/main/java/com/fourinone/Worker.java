package com.fourinone;

import java.rmi.RemoteException;

public interface Worker extends ParkActive {

  void setMigrantWorker(MigrantWorker mw) throws RemoteException;

  WareHouse doTask(WareHouse inhouse) throws RemoteException;

  void stopTask() throws RemoteException, InterruptedException;

  boolean receiveMaterials(WareHouse inhouse) throws RemoteException;
}