package com.fourinone;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * park leader
 *
 * @author null
 */
public class ParkLeader {

  private static final Logger LOGGER = LoggerFactory.getLogger(ParkLeader.class);

  private final LinkedBlockingQueue<String> bq = new LinkedBlockingQueue<>();
  boolean ismaster = false;
  boolean alwaystry = false;
  String[][] groupServerAddrs = new String[][]{{"localhost", "1888"}, {"localhost", "1889"},
      {"localhost", "1890"}};
  private String parkservicecfg = "ParkService";

  /**
   * thishost,thisport;cur host for service and cur leader for proxy
   */
  private String[] currentServer;

  private AsyncExecutor rpl = null;

  ParkLeader(String host, int port, String parkservicecfg) {
    this.parkservicecfg = parkservicecfg;
    currentServer = new String[]{host, "" + port};
    this.alwaystry = Boolean
        .valueOf(ConfigContext.getConfig("PARK", "ALWAYSTRYLEADER", null, "false"));
  }

  ParkLeader(String host, int port, String[][] groupServerAddrs, String parkservicecfg) {
    this(host, port, parkservicecfg);
    this.groupServerAddrs = groupServerAddrs;
  }

  public static void main(String[] args) {
    ParkLeader pl = new ParkLeader("localhost", 1888, "ParkService");
    String[] sv = new String[2];
    System.out.println(pl.getOtherMasterPark(sv));
    System.out.println(sv[1]);
  }

  protected Park getLeaderPark() {
    LogUtil.info("", "", "getLeaderPark...................");
    int index = getLeaderIndex(currentServer);
    return electionLeader(-1, index);
  }

  protected Park getNextLeader() {
    LogUtil.info("", "", "getNextLeader...................");
    int index = getLeaderIndex(currentServer);
    return electionLeader(index, index + 1);
  }

  private int getLeaderIndex(String[] sa) {
    int i = 0;
    for (; i < groupServerAddrs.length; i++) {
      if (Arrays.equals(sa, groupServerAddrs[i])) {
        break;
      }
    }
    return i;
  }

  protected Park electionLeader(int b, int i) {
    Park pk = null;
    boolean thesarrok = true;
    i = i < groupServerAddrs.length ? i : 0;
    //b=b<0?groupserver.length-1:b;
    String[] sarr = groupServerAddrs[i];
    try {
      pk = (Park) BeanService.getBean(sarr[0], Integer.parseInt(sarr[1]), parkservicecfg);
      if (pk != null) {
        pk.askLeader();
      }
    } catch (RemoteException re) {
      LogUtil.info("electionLeader", "(" + sarr[0] + ":" + sarr[1] + "):", re.getMessage());
      thesarrok = false;
      if (re instanceof ConnectException) {
        //one cycle
        if (b != i) {
          b = !alwaystry && b < 0 ? i : b;
          pk = electionLeader(b, i + 1);
        }
      }
    } catch (LeaderException le) {
      //le.printStackTrace();
      LogUtil.info("[electionLeader]", "[LeaderException]", le.getMessage());
      thesarrok = false;
      String[] ls = le.getLeaderServer();
      int leaderindex = getLeaderIndex(ls);
      pk = electionLeader(-1, leaderindex);
    }
    if (thesarrok) {
      currentServer = sarr;
      LogUtil.info("", "", "leader server is(" + currentServer[0] + ":" + currentServer[1] + ")");
    }
    return pk;
  }
	
	/*
	protected Park getNextMaster(){//boolean includethisserver
		Park pk = null;
		for(int i=0;i<groupserver.length;i++)
		{
			String[] sarr = groupserver[i];
			if(Arrays.equals(thisserver,sarr))
			{
				sarr = (i+1)<groupserver.length?groupserver[i+1]:groupserver[0];
				pk = (Park)BeanService.getBean(sarr[0],Integer.parseInt(sarr[1]),"ParkService");
				thisserver=sarr;
				break;
			}
		}
		return pk;
	}*/

  protected Park electionLeader(int i) {
    Park pk = null;
    boolean thesarrok = true;
    i = i < groupServerAddrs.length ? i : 0;
    String[] sarr = groupServerAddrs[i];
    try {
      pk = (Park) BeanService.getBean(sarr[0], Integer.parseInt(sarr[1]), parkservicecfg);
      if (pk != null) {
        pk.askLeader();
      }
    } catch (RemoteException re) {
      LogUtil.info("electionLeader", "(" + sarr[0] + ":" + sarr[1] + "):", re.getMessage());
      thesarrok = false;
      if (re instanceof ConnectException) {
        pk = electionLeader(i + 1);
      }
    } catch (LeaderException le) {
      //le.printStackTrace();
      LogUtil.info("electionLeader", "LeaderException", le);
      thesarrok = false;
      String[] ls = le.getLeaderServer();
      int leaderindex = getLeaderIndex(ls);
      pk = electionLeader(leaderindex);
    }
    if (thesarrok) {
      currentServer = sarr;
      LogUtil.info("", "", "leader server is(" + currentServer[0] + ":" + currentServer[1] + ")");
    }
    return pk;
  }

  protected Park[] getOtherPark() {
    ArrayList<Park> pklist = new ArrayList<>();
    for (String[] sarr : groupServerAddrs) {
      if (!Arrays.equals(currentServer, sarr)) {
        try {
          // try catch cant null
          Park pk = (Park) BeanService.getBean(sarr[0], Integer.parseInt(sarr[1]), parkservicecfg);
          pklist.add(pk);
        } catch (RemoteException re) {
          LogUtil.fine("getOtherPark", "(" + sarr[0] + ":" + sarr[1] + "):", re.getMessage());
          //re.printStackTrace();
        }
      }
    }
    return pklist.toArray(new Park[pklist.size()]);
  }

  protected boolean checkMasterPark(String[] sv, Park pk) {
    if (ismaster
        || getOtherMasterPark(sv) == null) {//cant ismaster for double conflict in net break
      copyArray(currentServer, sv);
      setMaster(true, pk);
      return true;
    } else {
      return false;
    }
		/*if(ismaster){
			sv = thisserver;
			return true;
		}
		else{
			if(getOtherMasterPark(sv)==null){
				sv = thisserver;
				return true;
			}
			else
				return false;
		}*/
  }

  /**
   * try to be master
   *
   * @param pk
   */
  protected void wantBeMaster(Park pk) {
    // LogUtil.info("", "", "wantBeMaster.............................");
    LOGGER.info("{}{} {}", "", "", "wantBeMaster.............................");
    String[] sv = new String[2];
    Park otherMaster = getOtherMasterPark(sv);
    if (otherMaster == null) {
      LogUtil.info("", "", "get one of other parks for init parkInfo.........");
      Park[] pks = getOtherPark();
      if (pks.length > 0) {
        setInitParkInfo(pks[0], pk);
      }
      setMaster(true, pk);
    } else {
      LogUtil.info("", "", "wantBeMaster,master is (" + sv[0] + ":" + sv[1] + ")");
      setInitParkInfo(otherMaster, pk);
    }
  }

  private void setInitParkInfo(Park fromPk, Park toPk) {
    try {
      toPk.setParkinfo(fromPk.getParkinfo());
    } catch (Exception re) {
      //re.printStackTrace();
      LogUtil.info("[ParkLeader]", "[setInitParkInfo]", re);
    }
  }

  private void setMaster(boolean ismaster, Park pk) {
    this.ismaster = ismaster;
    LogUtil.info("", "", "setMaster(" + currentServer[0] + ":" + currentServer[1] + "):" + ismaster);
    if (this.ismaster) {
      HbDaemo.runClearTask((ParkService) pk);
    }
  }

  protected String[] isMaster() {
    return ismaster ? currentServer : null;
  }

  protected Park getOtherMasterPark(String[] sv) {
    Park pkmaster = null;
    try {
      Park[] pks = getOtherPark();
      for (Park pk : pks) {
        String[] ask = pk.askMaster();
        if (ask != null) {
          pkmaster = pk;
          //sv=ask;
          copyArray(ask, sv);
          //System.out.println("getOtherMasterPark, ask is("+ask[0]+":"+ask[1]+")");
          //System.out.println("getOtherMasterPark, sv is("+sv[0]+":"+sv[1]+")");
        }
      }
    } catch (Exception re) {
      //re.printStackTrace();
      LogUtil.info("getOtherMasterPark", "exception", re);
    }
    return pkmaster;

		/*
		if(masterserver==null)
		{

			if(masterserver==null)
				masterserver=thisserver;
		}
		return masterserver;
		*/
  }

  protected void runCopyTask(String domainnodekey, final Park pk) {
    //put key into queue
    //laze run thread
    LogUtil.fine("", "", "runCopyTask:" + domainnodekey + "............................");

    try {
      bq.put(domainnodekey);
    } catch (InterruptedException ie) {
      ie.printStackTrace();
    }

    //static{}
    if (rpl == null) {
      LogUtil.fine("", "", "runCopyTask AsyncExector:");
      (rpl = new AsyncExecutor() {
        @Override
        public void task() {
          try {
            while (true) {
              String curkey = bq.take();
              LogUtil.fine("", "", "runCopyTask bq.size():" + bq.size());
              if (bq.size() == 0) {
                LogUtil.fine("", "", "curkey:" + curkey);
                //Thread.sleep(1000);
                copyParkinfo(pk.getParkinfo());
              }
            }
          } catch (Exception e) {
            //e.printStackTrace();
            LogUtil.info("runCopyTask", "exception", e);
          }
        }
      }).run();
    }
  }

  private Boolean[] copyParkinfo(ObjValue pov) {
    ArrayList<Boolean> sendlist = new ArrayList<Boolean>();
    try {
      Park[] pks = getOtherPark();
      for (Park pk : pks) {
        sendlist.add(pk.setParkinfo(pov));
      }
    } catch (Exception re) {
      LogUtil.info("copyParkinfo", "exception", re);
    }
    return sendlist.toArray(new Boolean[sendlist.size()]);
  }

  private void copyArray(String[] fromArr, String[] toArr) {
    for (int i = 0; i < toArr.length; i++) {
      toArr[i] = fromArr[i];
    }
  }

  public String[] getCurrentServer() {
    return currentServer;
  }
}