package com.fourinone;

import java.rmi.RemoteException;
import java.util.List;

public interface Cache extends ParkActive {
  //public String add(String type, Serializable obj) throws RemoteException;
  //public String add(String type, byte[] obj) throws RemoteException;
  //public Object get(String type, String keyid) throws RemoteException;
  //public Object putByKey(String keyId, String name, Serializable obj) throws RemoteException;
  //public Object putByKey(String keyId, String name, byte[] obj) throws RemoteException;
  //public Object getByKey(String keyid, String name) throws RemoteException;

  String add(String name, byte[] obj) throws RemoteException;

  ObjectBean put(String keyid, String name, byte[] obj) throws RemoteException;

  ObjectBean get(String keyid, String name) throws RemoteException;

  List<ObjectBean> get(String keyid) throws RemoteException;

  ObjectBean remove(String keyid, String name) throws RemoteException;

  List<ObjectBean> remove(String keyid) throws RemoteException;
}