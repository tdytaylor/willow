package com.fourinone;

import java.util.EventListener;

public interface LastestListener extends EventListener {

  boolean happenLastest(LastestEvent le);
}