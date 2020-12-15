package com.fourinone;

public interface ObjectBean extends java.io.Serializable {

  Object toObject();

  String getName();

  String getDomain();

  String getNode();
}