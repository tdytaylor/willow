package org.example.sayhello;

import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;
import com.fourinone.Workman;

/**
 * @author tdytaylor
 */
public class HelloWorker extends MigrantWorker {

  private String name;

  public HelloWorker(String name) {
    this.name = name;
  }

  @Override
  public WareHouse doTask(WareHouse inHouse) {
    System.out.println(inHouse.getString("word"));
    WareHouse wh = new WareHouse("word", "hello, i am " + name);
    Workman[] wms = getWorkerElse("helloworker");
    for (Workman wm : wms) {
      wm.receive(wh);
    }
    return wh;
  }

  @Override
  public boolean receive(WareHouse inhouse) {
    System.out.println(inhouse.getString("word"));
    return true;
  }

  public static void main(String[] args) {
    HelloWorker mw = new HelloWorker(args[0]);
    mw.waitWorking(args[1], Integer.parseInt(args[2]), "helloworker");
  }
}