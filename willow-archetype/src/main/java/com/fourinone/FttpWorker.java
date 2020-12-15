package com.fourinone;

import java.io.File;
import java.net.URI;
import java.rmi.RemoteException;

public interface FttpWorker extends ParkActive {

  byte[] read(String f, long b, long t) throws RemoteException, FttpException;

  byte[] readLocked(String f, long b, long t) throws RemoteException, FttpException;

  int[] readInt(String f, long b, long t) throws RemoteException, FttpException;

  int[] readIntLocked(String f, long b, long t) throws RemoteException, FttpException;

  int write(String f, long b, long t, byte[] bs) throws RemoteException, FttpException;

  int writeLocked(String f, long b, long t, byte[] bs) throws RemoteException, FttpException;

  int writeInt(String f, long b, long t, int[] its) throws RemoteException, FttpException;

  int writeIntLocked(String f, long b, long t, int[] its)
      throws RemoteException, FttpException;

  FileResult getFileMeta(String f) throws RemoteException, FttpException;

  FileResult[] getChildFileMeta(String f) throws RemoteException, FttpException;

  String[] listRoots() throws RemoteException, FttpException;

  File createFile(String f, boolean isFile) throws RemoteException, FttpException;

  boolean deleteFile(String f) throws RemoteException, FttpException;

  boolean copyFile(String f, long e, URI t) throws RemoteException, FttpException;

  boolean renameFile(String f, String n) throws RemoteException, FttpException;
}