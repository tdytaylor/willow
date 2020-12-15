package com.fourinone;

import com.fourinone.CoolHashMap.CoolKeySet;

interface CoolHash {

  Object get(String key) throws CoolHashException;

  <T> T get(String key, Class<T> valueType) throws CoolHashException;

  <T> T get(String key, Class<T> valueType, boolean point, String... pointSubKey)
      throws CoolHashException;

  <T> Object put(String key, T value) throws CoolHashException;

  Object remove(String key) throws CoolHashException;

  String putPoint(String keyPoint, String key) throws CoolHashException;

  Object getPoint(String keyPoint, String... pointSubKey) throws CoolHashException;

  <T> T getPoint(String keyPoint, Class<T> valueType, String... pointSubKey)
      throws CoolHashException;

  CoolHashMap get(CoolKeySet<String> keys);

  CoolHashMap get(CoolKeySet<String> keys, Filter filter);

  CoolHashMap get(CoolKeySet<String> keys, Filter filter, boolean point,
      String... pointSubKey);

  int put(CoolHashMap keyvalue);

  int remove(CoolKeySet<String> keys);

  CoolKeyResult findKey(String keywild);

  CoolKeyResult findKey(String keywild, Filter filter);

  CoolKeyResult findKey(String keywild, Filter filter, boolean point, String... pointSubKey);

  CoolHashResult find(String keywild);

  CoolHashResult find(String keywild, Filter filter);

  CoolHashResult find(String keywild, Filter filter, boolean point, String... pointSubKey);

  //public int remove(String keywild, Filter filter);
  //2017.10
  <T> Object putPlus(String key, T plusValue) throws CoolHashException;

  <T> Object putNx(String key, T value) throws CoolHashException;

  int putBitSet(String key, int index) throws CoolHashException;

  boolean getBitSet(String key, int index) throws CoolHashException;

  int putBitSet(String key, CoolBitSet cbs) throws CoolHashException;

  Object putBitSet(String key, CoolBitSet cbs, String logical) throws CoolHashException;
}