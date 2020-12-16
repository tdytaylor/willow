package com.fourinone;

import java.rmi.RemoteException;

/**
 * rmi 服务接口
 */
public interface Park extends ParkActive {

  //public <T extends java.io.Serializable> ObjValue create(String domain, String node, T obj, String sessionid, int auth, boolean heartbeat) throws RemoteException;//acl
  //public <T extends java.io.Serializable> ObjValue update(String domain, String node, T obj, String sessionid) throws RemoteException;//acl
  ObjValue create(String domain, String node, byte[] bts, String sessionid, int auth,
      boolean heartbeat) throws RemoteException, ClosetoOverException;//acl

  ObjValue update(String domain, String node, byte[] bts, String sessionid)
      throws RemoteException, ClosetoOverException;//acl

  boolean update(String domain, int auth, String sessionid) throws RemoteException;

  ObjValue get(String domain, String node, String sessionid)
      throws RemoteException, ClosetoOverException;

  ObjValue getLastest(String domain, String node, String sessionid, long version)
      throws RemoteException, ClosetoOverException;

  ObjValue delete(String domain, String node, String sessionid)
      throws RemoteException, ClosetoOverException;

  String getSessionId() throws RemoteException;

  boolean heartbeat(String[] domainnodekey, String sessionid) throws RemoteException;

  ObjValue getParkinfo() throws RemoteException;

  boolean setParkinfo(ObjValue ov) throws RemoteException;

  String[] askMaster() throws RemoteException;

  boolean askLeader() throws RemoteException, LeaderException;
  //public List<ObjValue> getNodesInDomain(String domain, String sessionid) throws RemoteException;
  //putInfo(parkinfo)
  //heartbeat(String sessionid)
  //put(String domain, String node, T obj, String sessionid, ACL, heartbeat)
}