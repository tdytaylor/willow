package com.fourinone;

import java.io.File;
import java.net.URI;

public interface FttpLocal {

  byte[] readByte(String f, long b, long t) throws Throwable;//FttpException

  FileResult<byte[]> readByteAsyn(String f, long b, long t, boolean locked);

  byte[] readByteLocked(String f, long b, long t) throws Throwable;

  int[] readInt(String f, long b, long t) throws Throwable;

  FileResult<int[]> readIntAsyn(String f, long b, long t, boolean locked);

  int[] readIntLocked(String f, long b, long t) throws Throwable;

  int writeByte(String f, long b, long t, byte[] bs) throws Throwable;

  int writeInt(String f, long b, long t, int[] its) throws Throwable;

  FileResult<Integer> writeIntAsyn(String f, long b, long t, int[] its, boolean locked);

  int writeIntLocked(String f, long b, long t, int[] its) throws Throwable;

  FileResult<Integer> writeByteAsyn(String f, long b, long t, byte[] bs, boolean locked);

  int writeByteLocked(String f, long b, long t, byte[] bs) throws Throwable;

  FileResult getFileMeta(String f) throws Throwable;

  FileResult[] getChildFileMeta(String f) throws Throwable;

  String[] getListRoots() throws Throwable;

  String getHost();

  File create(String f, boolean i) throws Throwable;

  boolean delete(String f) throws Throwable;

  boolean copy(String f, long e, URI t) throws Throwable;

  FileResult<FttpAdapter> copyAsyn(String f, long e, URI t);

  boolean rename(String f, String newname) throws Throwable;
}