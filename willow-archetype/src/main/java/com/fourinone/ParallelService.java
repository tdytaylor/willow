package com.fourinone;

import java.util.ArrayList;
import java.util.List;

abstract class ParallelService {

  /**
   * @param host
   * @param port
   * @param workerType
   */
  abstract public void waitWorking(String host, int port, String workerType);

  /**
   * @param workerType
   */
  abstract public void waitWorking(String workerType);
	
	/*WorkerLocal[] getWorkersService(String workerType)
	{
		//LogUtil.fine("", "", "getWorkersService:"+workerType);
		List<ObjectBean> oblist = ParkPatternExector.getWorkerTypeList(workerType);
		List<WorkerLocal> wklist = new ArrayList<WorkerLocal>();
		for(ObjectBean ob:oblist)
		{
			String[] hostport = ((String)ob.toObject()).split(":");
			wklist.add(BeanContext.getWorkerLocal(hostport[0], Integer.parseInt(hostport[1]), workerType));
		}
		return wklist.toArray(new WorkerLocal[wklist.size()]);
	}
	
	Workman[] getWorkersService(String host, int port, String workerType)
	{
		//LogUtil.fine("", "", "getWorkersService:"+workerType);
		List<ObjectBean> oblist = ParkPatternExector.getWorkerTypeList(workerType);
		List<Workman> wklist = new ArrayList<Workman>();
		for(ObjectBean ob:oblist)
		{
			String[] hostport = ((String)ob.toObject()).split(":");
			if(!hostport[0].equals(host)&&!Integer.parseInt(hostport[1])!=port)
				wklist.add(BeanContext.getWorkman(hostport[0], Integer.parseInt(hostport[1]), workerType));
		}
		return wklist.toArray(new WorkerLocal[wklist.size()]);
	}*/

  /**
   * 获取work列表
   *
   * @param parkHost
   * @param parkPort
   * @param workerType
   * @return
   */
  List<String[]> getWorkersServicePark(String parkHost, int parkPort, String workerType) {
    List<ObjectBean> objectBeanList = ParkPatternExecutor
        .getWorkerTypeList(parkHost, parkPort, workerType);
    List<String[]> wslist = new ArrayList<>();
    for (ObjectBean ob : objectBeanList) {
      String[] hostPort = ((String) ob.toObject()).split(":");
      if (!hostPort[0].equals(null) || Integer.parseInt(hostPort[1]) != 0) {
        wslist.add(new String[]{hostPort[0], hostPort[1], workerType});
      }

    }
    return wslist;
  }

  /**
   * @param host
   * @param port
   * @param workerType
   * @return
   */
  List<String[]> getWorkersService(String host, int port, String workerType) {
    //LogUtil.fine("", "", "getWorkersService:"+workerType);

    // getWorkerTypeList(String host, int port, String workerType)
    List<ObjectBean> oblist = ParkPatternExecutor.getWorkerTypeList(workerType);
    List<String[]> wslist = new ArrayList<>();
    for (ObjectBean ob : oblist) {
      //System.out.println("ob.toObject():"+ob.toObject());
      String[] hostport = ((String) ob.toObject()).split(":");
      //&&
      if (!hostport[0].equals(host) || Integer.parseInt(hostport[1]) != port) {
        wslist.add(new String[]{hostport[0], hostport[1], workerType});
      }

    }
    return wslist;
  }

  List<String[]> getWorkersService(String workerType) {
    return getWorkersService(null, 0, workerType);
  }
}