package com.fourinone;

public interface Workman {

  boolean receive(WareHouse inhouse);

  String getHost();

  int getPort();
}