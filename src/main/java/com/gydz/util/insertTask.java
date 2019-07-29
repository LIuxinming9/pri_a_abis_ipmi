package com.gydz.util;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gydz.user.model.serversip;
import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;

public class insertTask {
	public static List<Connection>  connectionlist = new ArrayList<Connection>();
	public static Map<String,IpmiConnector>  IpmiConnectormap = new HashMap<String,IpmiConnector>();
	public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	public static void main(String[] args) {
		try {
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void runConnection() throws Exception {
		Connection ipmiconnection = getConnection(PropertyUtil.getProperty("url_sys"));
		connectionlist.add(ipmiconnection);
	}
	
	
	/*public static void runIpmiConnector() throws Exception {
		List<serversip> serversip = insertUtil.serversip(connectionlist);
		for (int i = 0; i < serversip.size(); i++) {
			IpmiConnector connector = new IpmiConnector(0);
	         ConnectionHandle handle = ipmiutil.startSession(connector, InetAddress.getByName(serversip.get(i).getHostname()), serversip.get(i).getUsername(), serversip.get(i).getPassword(), "",
	                 PrivilegeLevel.User);
	         connector.setTimeout(handle, 2750);
	         IpmiConnectormap.put(serversip.get(i).getHostname(),connector);
		}
	}*/
	
	public static void run() throws Exception {
		//runinsertUtilh();
		runinsertUtilcurrent();
		/*SnmpTrapReceive multithreadedtrapreceiver = new SnmpTrapReceive();  
        multithreadedtrapreceiver.run();*/
	}
	
	

	public static void runinsertUtilh() {
		Timer timer = new Timer();
		Long period = PropertyUtil.getProperty("restful_periodh") != null
				? Long.valueOf(PropertyUtil.getProperty("restful_periodh"))
				: 900000L;
		timer.schedule(new insertUtilh(), 6000, period);
	}
	public static void runinsertUtilcurrent() {
		Timer timer = new Timer();
		Long period = PropertyUtil.getProperty("restful_periodc") != null
				? Long.valueOf(PropertyUtil.getProperty("restful_periodc"))
				: 900000L;
		timer.schedule(new insertUtilcurrent(), 8000, period);
	}
	public static Connection getConnection(String database) throws Exception {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = database;
			String user = PropertyUtil.getProperty("username_sys");// 
			// String password = "guangyuan_gsmr_2018";// 
			String password = PropertyUtil.getProperty("password_sys");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

}

class insertUtilh extends TimerTask {
	@Override
	public void run() {
		try {
			Date now = new Date();
			String endTime = String.valueOf(now.getTime());
			insertUtil.inserth(insertTask.connectionlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class insertUtilcurrent extends TimerTask {
	@Override
	public void run() {
		try {
			Date now = new Date();
			String endTime = String.valueOf(now.getTime());
			insertcurrentUtil.insertcurrent(insertTask.connectionlist,insertTask.cachedThreadPool);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


