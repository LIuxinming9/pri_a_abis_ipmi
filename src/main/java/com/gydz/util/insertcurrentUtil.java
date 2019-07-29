package com.gydz.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import com.gydz.user.controller.SystemOperLogs;
import com.gydz.user.model.IpmiListener;
import com.gydz.user.model.chassisStatus;
import com.gydz.user.model.fruInfo;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.model.snmp;
import com.veraxsystems.vxipmi.coding.commands.sel.SelRecord;

public class insertcurrentUtil {

	public static void main(String[] args) throws Exception {
		
	}

	public static int fruNum;
	public static int sensorNum;
	public static int sensorNumCur;
	public static int chassisNum;
	public static int numMax;
	public static int sensorNumMax;
	
	public static void insertcurrent() throws Exception {
		
		List<Connection>  connectionlist = new ArrayList<Connection>();
		Connection ipmiconnection = insertTask.getConnection(PropertyUtil.getProperty("url_sys"));
		connectionlist.add(ipmiconnection);
		try {
			//insertcurrent(connectionlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertcurrent(final List<Connection> connectionlist, ExecutorService cachedThreadPool) throws Exception {
		Connection conn = connectionlist.get(0);
		final Date date = newDate.newDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		final String tablename = dateFormat.format(date);
		createtable(connectionlist,tablename);
		//insertSnmp(connectionlist);
		
		SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");
		String endtablename = endDateFormat.format(date);
		System.out.println("起始时间"+endtablename);
		
		final List<serversip> list = serversip(conn);
		//final List<String> snmpList = serversSnmpIp(conn);
		int num = Integer.parseInt(PropertyUtil.getProperty("numMax"));
		numMax = num;
		fruNum += 1;
		sensorNum += 1;
		chassisNum += 1;
		for (int i = 0; i < list.size(); i++) {
			final int index = i;
			cachedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						serversip serversip = list.get(index);
						String ip = serversip.getHostname().trim();
						createTableByIp(connectionlist,tablename,ip);
						long sensor_time = sensor_record_c(connectionlist,serversip,tablename,0);
						chassis_status_c(connectionlist,serversip,tablename,0);
						sel_record(connectionlist,serversip,tablename,0);
						/*if(snmpList.contains(ip)) {
							snmpValue(connectionlist,ip);
						}*/
						if(fruNum >= numMax) {
							fru_info(connectionlist,list.get(index),tablename,0);
							if(index==list.size()-1) {
								fruNum = 0;
								sensorNum = 0;
								chassisNum = 0;
							}
						}
						Date endDate = newDate.newDate();
						long program_time = endDate.getTime()-date.getTime();
						System.out.println("程序运行所花时间"+program_time);
						SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");
						String endtablename = endDateFormat.format(endDate);
						String thread = Thread.currentThread().getName();
						System.out.println(list.get(index).getHostname().trim()+"终点时间"+endtablename+"次数"+fruNum);
						System.out.println("正在运行的线程为="+thread);
						IpmiListener IpmiListener = new IpmiListener();
						IpmiListener.setEnd_time(endDate);
						IpmiListener.setSensor_time(sensor_time+"");
						IpmiListener.setProgram_time(program_time+"");
						IpmiListener.setThread(thread);
						
						insertGet_time(connectionlist, ip, tablename,IpmiListener);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
		}
	}
	
	public static void createtable(List<Connection> connectionlist,String tablename) throws Exception {
		createTableUtil.createTablewarn_refer(connectionlist,tablename);
		createTableUtil.createTablesnmp_oid(connectionlist, tablename);
		createTableUtil.createTablesnmp_type(connectionlist, tablename);
		createTableUtil.createTablesnmp_id_oid(connectionlist, tablename);
	}
	
	public static void createTableByIp(List<Connection> connectionlist,String tablename,String ip) throws Exception {
		createcurrentTableUtil.createTablesensor_record(connectionlist,tablename,ip);
		createcurrentTableUtil.createTablechassis_status(connectionlist,tablename,ip);
		createTableUtil.createTablesensor_record(connectionlist,tablename,ip);
		createTableUtil.createTablechassis_status(connectionlist,tablename,ip);
		createTableUtil.createTableget_time(connectionlist,tablename,ip);
		createcurrentTableUtil.createTablefru_info(connectionlist,tablename,ip);
		createcurrentTableUtil.createTablesel_record(connectionlist,tablename,ip);
		createcurrentTableUtil.createTablesel_type(connectionlist,tablename,ip);
		createcurrentTableUtil.createTablesel_name(connectionlist,tablename,ip);
		createcurrentTableUtil.createTablesnmp_value(connectionlist, tablename,ip);
		createTableUtil.createTablesnmpTrap(connectionlist, tablename);
	}

	public static double numformate(double d) {
		return Double.parseDouble(new DecimalFormat("0.00").format(d));
	}
	
	public static List<serversip> serversip(Connection conn) throws Exception{
		List<serversip> list = new ArrayList<serversip>();
		String selectsql = "select hostname,username,password,bmc_key from serversip where is_online = 0";
		PreparedStatement selectps = conn.prepareStatement(selectsql);
		ResultSet rs = selectps.executeQuery();
		while (rs.next()) {
			boolean bool = isOnline(rs.getString(1));
			if(bool) {
				if(!rs.getString(1).isEmpty()) {
					serversip serversip = new serversip();
					serversip.setHostname(rs.getString(1));
					serversip.setUsername(rs.getString(2));
					serversip.setPassword(rs.getString(3));
					serversip.setBmcKey(rs.getString(4)==null?"":rs.getString(4));
					list.add(serversip);
				}
			}else {
				is_online_update(conn,rs.getString(1));
			}
		}
		rs.close();
		selectps.close();
		return list;
		
	}
	
	public static List<String> serversSnmpIp(Connection conn) throws Exception{
		List<String> list = new ArrayList<String>();
		String selectsql = "select hostname,community from serversip where snmp_online = 0";
		PreparedStatement selectps = conn.prepareStatement(selectsql);
		ResultSet rs = selectps.executeQuery();
		while (rs.next()) {
			boolean bool = snmpOnline(rs.getString(1),rs.getString(2));
			if(bool) {
				if(!rs.getString(1).isEmpty()) {
					list.add(rs.getString(1));
				}
			}else {
				snmp_online_update(conn,rs.getString(1));
			}
		}
		rs.close();
		selectps.close();
		return list;
		
	}
	
	public static void is_online_update(Connection conn,String hostname) throws Exception {
		String sql = "update serversip "
				+ "set is_online=? where hostname=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, 1);
		ps.setString(2, hostname);
		ps.executeUpdate();
		ps.close();
	}
	
	public static void snmp_online_update(Connection conn,String hostname) throws Exception {
		String sql = "update serversip "
				+ "set snmp_online=? where hostname=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, 1);
		ps.setString(2, hostname);
		ps.executeUpdate();
		ps.close();
	}
	
	public static boolean isOnline(String hostname) {
        try {
            InetAddress ia;
            boolean isonline = false;
            ia = InetAddress.getByName(hostname);// 例如：www.baidu.com
            isonline = ia.isReachable(1500); //超时时间1.5秒
            return isonline;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            System.out.println("address:" + hostname + " is not unknown");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("address:" + hostname + " is not reachable");
        }
        return false;
    }
	
	public static boolean snmpOnline(String hostname,String community) {
        boolean snmpOnline = false;
		String str = SnmpData.snmpGet(hostname, community, "1.3.6.1.2.1.1.3.0");
		if(str.length()>1) {
			snmpOnline = true;
		}
        return snmpOnline;
    }
	
	public static Map<String,Integer> chassisIpNum(List<Connection> connectionlist,String ip) throws Exception{
		Connection conn = connectionlist.get(0);
		List<String> ipList = new ArrayList<String>();
		String selectsql = "select IP from "+"`"+"chassis_status_current" + "_" + ip + "`";
		PreparedStatement selectps = conn.prepareStatement(selectsql);
		ResultSet rs = selectps.executeQuery();
		while (rs.next()) {
			ipList.add(rs.getString(1));
		}
		List<serversip> serversIpList = serversip(conn);
		Map<String,Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < serversIpList.size(); i++) {
			int num = 0;
			for (int j = 0; j < ipList.size(); j++) {
				if(serversIpList.get(i).getHostname().equals(ipList.get(j))) {
					num++;
				}
			}
			map.put(serversIpList.get(i).getHostname(), num);
		}
		rs.close();
		selectps.close();
		return map;
	}
	
	public static Map<String,Integer> sensorIpNum(List<Connection> connectionlist,String ip) throws Exception{
		Connection conn = connectionlist.get(0);
		List<String> ipList = new ArrayList<String>();
		String selectsql = "select IP from "+"`"+"sensor_record_current" + "_" + ip + "`";
		PreparedStatement selectps = conn.prepareStatement(selectsql);
		ResultSet rs = selectps.executeQuery();
		while (rs.next()) {
			ipList.add(rs.getString(1));
		}
		List<serversip> serversIpList = serversip(conn);
		Map<String,Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < serversIpList.size(); i++) {
			int num = 0;
			for (int j = 0; j < ipList.size(); j++) {
				if(serversIpList.get(i).getHostname().equals(ipList.get(j))) {
					num++;
				}
			}
			map.put(serversIpList.get(i).getHostname(), num);
		}
		rs.close();
		selectps.close();
		return map;
	}
	
	public static Map<String,Integer> warnReferIpNum(Connection conn) throws Exception{
		List<String> ipList = new ArrayList<String>();
		String selectsql = "select IP from warn_refer";
		PreparedStatement selectps = conn.prepareStatement(selectsql);
		ResultSet rs = selectps.executeQuery();
		while (rs.next()) {
			ipList.add(rs.getString(1));
		}
		List<serversip> serversIpList = serversip(conn);
		Map<String,Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < serversIpList.size(); i++) {
			int num = 0;
			for (int j = 0; j < ipList.size(); j++) {
				if(serversIpList.get(i).getHostname().equals(ipList.get(j))) {
					num++;
				}
			}
			map.put(serversIpList.get(i).getHostname(), num);
		}
		rs.close();
		selectps.close();
		return map;
	}
	
	public static Map<String,Integer> fruIpNum(List<Connection> connectionlist,String ip) throws Exception{
		Connection conn = connectionlist.get(0);
		List<String> ipList = new ArrayList<String>();
		String selectsql = "select IP from "+"`"+"fru_info_current" + "_" + ip + "`";
		PreparedStatement selectps = conn.prepareStatement(selectsql);
		ResultSet rs = selectps.executeQuery();
		while (rs.next()) {
			ipList.add(rs.getString(1));
		}
		List<serversip> serversIpList = serversip(conn);
		Map<String,Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < serversIpList.size(); i++) {
			int num = 0;
			for (int j = 0; j < ipList.size(); j++) {
				if(serversIpList.get(i).getHostname().equals(ipList.get(j))) {
					num++;
				}
			}
			map.put(serversIpList.get(i).getHostname(), num);
		}
		rs.close();
		selectps.close();
		return map;
	}
	
	public static long sensor_record_c(List<Connection> connectionlist,serversip serversip, String tablename,int port) throws Exception {
		Connection conn = connectionlist.get(0);
		Date startDate = newDate.newDate();
		Map<String,List<sensorrecord>> listMap = ipmiutil.getfullrecord(serversip,port);
		List<sensorrecord> fulllist = listMap.get("fullList");
		List<sensorrecord> conpactlist = listMap.get("conpactList");
		Date endDate = newDate.newDate();
		long sensor_time = endDate.getTime() - startDate.getTime();
		System.out.println("获取传感器数据需要" + (startDate.getTime()-endDate.getTime())+serversip.getHostname().trim());
		Date date = newDate.newDate();
		Map<String,Integer> map = sensorIpNum(connectionlist,serversip.getHostname().trim());
		int ipNum = map.get(serversip.getHostname());
		System.out.println(ipNum+"========"+(fulllist.size() + conpactlist.size()));
		if(ipNum == 0){
			sensor_insert_all(conn,fulllist,conpactlist,serversip,tablename,date);
		}else if(ipNum == fulllist.size() + conpactlist.size()){
			sensor_update_all(conn,fulllist,conpactlist,serversip,tablename,date);
			warn_refer(conn,fulllist,serversip,tablename,date);
			System.out.println("sensorNum="+sensorNum+"        numMax="+numMax);
			if(sensorNum == numMax || sensorNum == 0) {
				sensor_record(conn,fulllist,conpactlist,serversip,tablename,date);
			}
		}else {
			sensor_delete_all(conn,serversip,tablename);
			sensor_insert_all(conn,fulllist,conpactlist,serversip,tablename,date);
		}
		Date insertDate = newDate.newDate();
		System.out.println("存储传感器数据需要" + (date.getTime()-insertDate.getTime()));
		return sensor_time;
	}
	
	public static void sensor_record(Connection conn,List<sensorrecord> fulllist,List<sensorrecord> conpactlist,serversip serversip, String tablename,Date date) throws Exception {
		String ip = serversip.getHostname().trim();
		//String lock = new String();
		//synchronized(lock) {
		for (int i = 0; i < fulllist.size(); i++) {
			String sql = "insert into "+"`"+"sensor_record_" + tablename + "_" + ip + "`" 
					+ "(start_time,IP,name,entity_id,sensor_type,sensor_owner_lun,"
					+ "record_type,current_num,sensor_base_unit,"
					+ "entity_physical,sensor_number,entity_instance_number,address_type,"
					+ "rate_unit,modifier_unit_usage,sensor_modifier_unit,sensor_direction,"
					+ "sensor_state_valid,statesAsserted,state) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);//
			ps.setTimestamp(1, new Timestamp(date.getTime()));
			ps.setString(2, ip);
			ps.setString(3,fulllist.get(i).getName().trim());
			ps.setString(4,fulllist.get(i).getEntity_id().trim());
			ps.setString(5,fulllist.get(i).getSensor_type().trim());
			ps.setByte(6,fulllist.get(i).getSensor_owner_lun());
			ps.setString(7,"FullSensorRecord");
			ps.setDouble(8,numformate(fulllist.get(i).getCurrent_num()));
			ps.setString(9,fulllist.get(i).getSensor_base_unit().trim());
			ps.setString(10, fulllist.get(i).isEntity_physical().trim());
			ps.setInt(11, fulllist.get(i).getSensor_number());
			ps.setInt(12,fulllist.get(i).getEntity_instance_number());
			ps.setString(13,fulllist.get(i).getAddress_type().trim());
			ps.setString(14,fulllist.get(i).getRate_unit().trim());
			ps.setString(15,fulllist.get(i).getModifier_unit_usage().trim());
			ps.setString(16,fulllist.get(i).getSensor_modifier_unit().trim());
			ps.setString(17,fulllist.get(i).getSensor_direction().trim());
			ps.setString(18, fulllist.get(i).isSensor_state_valid());
			ps.setString(19, fulllist.get(i).getStatesAsserted());
			ps.setString(20,fulllist.get(i).getState());
			ps.executeUpdate();//
			ps.close();
		}
		//}
		System.out.println("fullrecord插入数据成功"+ tablename);
		//String lock1 = new String();
		//synchronized(lock1) {
		for (int i = 0; i < conpactlist.size(); i++) {
			String sql = "insert into "+"`"+"sensor_record_" + tablename + "_" + ip  + "`" 
					+ "(start_time,IP,name,entity_id,sensor_type,sensor_owner_lun,"
					+ "record_type,sensor_base_unit,address_type,"
					+ "entity_instance_number,entity_physical,"
					+ "modifier_unit_usage,rate_unit,sensor_direction,"
					+ "sensor_modifier_unit,sensor_number,current_num,"
					+ "sensor_state_valid,statesAsserted,state) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);//
			ps.setTimestamp(1, new Timestamp(date.getTime()));
			ps.setString(2, ip);
			ps.setString(3,conpactlist.get(i).getName().trim());
			ps.setString(4,conpactlist.get(i).getEntity_id().trim());
			ps.setString(5,conpactlist.get(i).getSensor_type().trim());
			ps.setByte(6,conpactlist.get(i).getSensor_owner_lun());
			ps.setString(7,"CompactSensorRecord");
			ps.setString(8,conpactlist.get(i).getSensor_base_unit().trim());
			ps.setString(9,conpactlist.get(i).getAddress_type().trim());
			ps.setInt(10,conpactlist.get(i).getEntity_instance_number());
			ps.setString(11, conpactlist.get(i).isEntity_physical().trim());
			ps.setString(12,conpactlist.get(i).getModifier_unit_usage().trim());
			ps.setString(13,conpactlist.get(i).getRate_unit().trim());
			ps.setString(14,conpactlist.get(i).getSensor_direction().trim());
			ps.setString(15,conpactlist.get(i).getSensor_modifier_unit().trim());
			ps.setInt(16, conpactlist.get(i).getSensor_number());
			ps.setDouble(17,conpactlist.get(i).getCurrent_num());
			ps.setString(18, conpactlist.get(i).isSensor_state_valid());
			ps.setString(19, conpactlist.get(i).getStatesAsserted());
			ps.setString(20,conpactlist.get(i).getState());
			ps.executeUpdate();//
			ps.close();
		}
		//}
		System.out.println("conpactrecord插入数据成功"+ tablename);
	}
	
	public static void warn_refer(Connection conn,List<sensorrecord> fulllist,serversip serversip, String tablename,Date date) throws Exception {
		Map<String,Integer> map = warnReferIpNum(conn);
		String ip = "1";
		ip = serversip.getHostname().trim();
		int ipNum = 1;
		ipNum = map.get(serversip.getHostname());
		if(ipNum==0) {
			String lock = new String();
			synchronized(lock) {
				String sql = "insert into warn_refer"
						+ "(start_time,IP,name,entity_id,sensor_type,"
						+ "current_num,normal_maximum,normal_minimum,"
						+ "nominal_reading,sensor_maximum_reading,"
						+ "sensor_minmum_reading,lower_non_recoverable_threshold,"
						+ "upper_non_recoverable_threshold,upper_critical_threshold,"
						+ "lower_critical_threshold,upper_non_critical_threshold,"
						+ "lower_non_critical_threshold) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);//
				ps.setTimestamp(1, new Timestamp(date.getTime()));
				for (int i = 0; i < fulllist.size(); i++) {
					sensorrecord sensorrecord = new sensorrecord();
					sensorrecord = fulllist.get(i);
					if(sensorrecord.getSensor_type().equals("Temperature")||
							sensorrecord.getSensor_type().equals("Fan")||
							sensorrecord.getSensor_type().equals("Voltage")) {
						ps.setString(2, ip);
						ps.setString(3,sensorrecord.getName().trim());
						ps.setString(4,sensorrecord.getEntity_id().trim());
						ps.setString(5,sensorrecord.getSensor_type().trim());
						ps.setDouble(6,numformate(sensorrecord.getCurrent_num()));
						ps.setDouble(7,numformate(sensorrecord.getNormal_maximum()));
						ps.setDouble(8,numformate(sensorrecord.getNormal_minimum()));
						ps.setDouble(9,numformate(sensorrecord.getNominal_reading()));
						ps.setDouble(10,numformate(sensorrecord.getSensor_maximum_reading()));
						ps.setDouble(11,numformate(sensorrecord.getSensor_minmum_reading()));
						ps.setDouble(12,numformate(sensorrecord.getLower_non_recoverable_threshold()));
						ps.setDouble(13,numformate(sensorrecord.getUpper_non_recoverable_threshold()));
						ps.setDouble(14,numformate(sensorrecord.getUpper_critical_threshold()));
						ps.setDouble(15,numformate(sensorrecord.getLower_critical_threshold()));
						ps.setDouble(16,numformate(sensorrecord.getUpper_non_critical_threshold()));
						ps.setDouble(17,numformate(sensorrecord.getLower_non_critical_threshold()));
						ps.executeUpdate();
					}
				}
				ps.close();
				System.out.println("warn_refer插入数据成功"+ tablename);
			}
			
		}
	}
	
	
	public static void sensor_delete_all(Connection conn,serversip serversip, String tablename) throws Exception {
		String ip = serversip.getHostname().trim();
		String sql = "delete from "+ "`"+"sensor_record_current"  + "_" + ip + "`"  + "where IP=?";
		PreparedStatement ps = conn.prepareStatement(sql);//
		ps.setString(1, ip);
		ps.executeUpdate();
		ps.close();
		System.out.println("sensor_record_current删除数据成功"+ tablename);
	}
	
	public static void sensor_update_all(Connection conn,List<sensorrecord> fulllist,List<sensorrecord> conpactlist,serversip serversip, String tablename,Date date) throws Exception {
		String ip = serversip.getHostname().trim();
		//String lock = new String();
		//synchronized(lock) {
		String sql = "update "+"`"+"sensor_record_current"  + "_" + ip  + "`" 
				+ "set start_time=?,current_num=?,statesAsserted=? where name=?";
		PreparedStatement ps = conn.prepareStatement(sql);//
		ps.setTimestamp(1, new Timestamp(date.getTime()));
		for (int i = 0; i < fulllist.size(); i++) {
			ps.setDouble(2,numformate(fulllist.get(i).getCurrent_num()));
			ps.setString(3, fulllist.get(i).getStatesAsserted());
			ps.setString(4,fulllist.get(i).getName().trim());
			ps.executeUpdate();
		}
		//}
		//String lock1 = new String();
		//synchronized(lock1) {
		for (int i = 0; i < conpactlist.size(); i++) {
			ps.setDouble(2,numformate(conpactlist.get(i).getCurrent_num()));
			ps.setString(3, conpactlist.get(i).getStatesAsserted());
			ps.setString(4,conpactlist.get(i).getName().trim());
			ps.executeUpdate();
		}
		ps.close();
		//}
		System.out.println("sensor_record_current更新数据成功"+ tablename);
	}
	
	public static void sensor_insert_all(Connection conn,List<sensorrecord> fulllist,List<sensorrecord> conpactlist,serversip serversip, String tablename,Date date) throws Exception {
		String ip = serversip.getHostname().trim();
		//String lock = new String();
		//synchronized(lock) {
		for (int i = 0; i < fulllist.size(); i++) {
			String sql = "insert into "+"`"+"sensor_record_current" + "_" + ip  + "`" 
					+ "(start_time,IP,name,entity_id,sensor_type,"
					+ "record_type,current_num,sensor_base_unit,"
					+ "entity_physical,"
					+ "sensor_state_valid,statesAsserted,state) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);//
			ps.setTimestamp(1, new Timestamp(date.getTime()));
			ps.setString(2, ip);
			ps.setString(3,fulllist.get(i).getName().trim());
			ps.setString(4,fulllist.get(i).getEntity_id().trim());
			ps.setString(5,fulllist.get(i).getSensor_type().trim());
			ps.setString(6,"FullSensorRecord");
			ps.setDouble(7,numformate(fulllist.get(i).getCurrent_num()));
			ps.setString(8,fulllist.get(i).getSensor_base_unit().trim());
			ps.setString(9, fulllist.get(i).isEntity_physical().trim());
			ps.setString(10, fulllist.get(i).isSensor_state_valid());
			ps.setString(11, fulllist.get(i).getStatesAsserted());
			ps.setString(12,fulllist.get(i).getState());
			ps.executeUpdate();//
			ps.close();
		}
		//}
		//String lock1 = new String();
		//synchronized(lock1) {
		for (int i = 0; i < conpactlist.size(); i++) {
			String sql = "insert into "+"`"+"sensor_record_current" + "_" + ip  + "`" 
					+ "(start_time,IP,name,entity_id,sensor_type,"
					+ "record_type,current_num,sensor_base_unit,"
					+ "entity_physical,"
					+ "sensor_state_valid,statesAsserted,state) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);//
			ps.setTimestamp(1, new Timestamp(date.getTime()));
			ps.setString(2, ip);
			ps.setString(3,conpactlist.get(i).getName().trim());
			ps.setString(4,conpactlist.get(i).getEntity_id().trim());
			ps.setString(5,conpactlist.get(i).getSensor_type().trim());
			ps.setString(6,"CompactSensorRecord");
			ps.setDouble(7,numformate(conpactlist.get(i).getCurrent_num()));
			ps.setString(8,conpactlist.get(i).getSensor_base_unit().trim());
			ps.setString(9, conpactlist.get(i).isEntity_physical().trim());
			ps.setString(10, conpactlist.get(i).isSensor_state_valid());
			ps.setString(11, conpactlist.get(i).getStatesAsserted());
			ps.setString(12,conpactlist.get(i).getState());
			ps.executeUpdate();//
			ps.close();
		}
		//}
		System.out.println("sensor_record_current插入数据成功"+ tablename);
	}
	
	public static void chassis_status_c(List<Connection> connectionlist,serversip serversip, String tablename,int port) throws Exception {
		Connection conn = connectionlist.get(0);
		Date startDate = newDate.newDate();
		chassisStatus chassisStatus = ipmiutil.getChassisStatus(serversip,port);
		Date endDate = newDate.newDate();
		System.out.println("获取chassis数据需要" + (startDate.getTime()-endDate.getTime()));
		Date date = newDate.newDate();
		Map<String,Integer> map = chassisIpNum(connectionlist,serversip.getHostname().trim());
		int ipNum = map.get(serversip.getHostname());
		if(ipNum == 0){
			chassis_insert_all(conn,chassisStatus,serversip,tablename,date);
		}else{
			if(chassisNum >= numMax || chassisNum==0) {
				chassis_status(conn,chassisStatus,serversip,tablename,date);
			}
			chassis_update_all(conn,chassisStatus,serversip,tablename,date);
		}
		Date insertDate = newDate.newDate();
		System.out.println("存储chassis数据需要" + (date.getTime()-insertDate.getTime()));
	}
	
	public static void chassis_status(Connection conn,chassisStatus chassisStatus,serversip serversip, String tablename,Date date) throws Exception {
		String ip = serversip.getHostname().trim();
		//String lock = new String();
		//synchronized(lock) {
			String sql = "insert into "+"`"+"chassis_status_" + tablename + "_" + ip  + "`" 
					+ "(start_time,IP,is_power_control_fault,"
					+ "is_power_fault,is_interlock,"
					+ "is_power_overload,is_power_on,"
					+ "was_ipmi_power_on,was_power_fault,"
					+ "was_interlock,ac_failed,"
					+ "is_chassis_identify_command_supported,"
					+ "cooling_fault_detected,drive_fault_detected,"
					+ "is_front_panel_lockout_active,"
					+ "is_chassis_intrusion_active,"
					+ "is_standby_button_disable_allowed,"
					+ "is_diagnostic_interrupt_button_disable_allowed,"
					+ "is_reset_button_disable_allowed,"
					+ "is_power_off_button_disable_allowed,"
					+ "is_standby_button_disabled,"
					+ "is_diagnostic_interrupt_button_disabled,"
					+ "is_reset_button_disabled,"
					+ "is_power_off_button_disabled,"
					+ "is_front_panel_button_capabilities_set) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);//
			ps.setTimestamp(1, new Timestamp(date.getTime()));
			ps.setString(2, ip);
			ps.setInt(3, chassisStatus.isIs_power_control_fault()==true?1:0);
			ps.setInt(4, chassisStatus.isIs_power_fault()==true?1:0);
			ps.setInt(5, chassisStatus.isIs_interlock()==true?1:0);
			ps.setInt(6, chassisStatus.isIs_power_overload()==true?1:0);
			ps.setInt(7, chassisStatus.isIs_power_on()==true?1:0);
			ps.setInt(8, chassisStatus.isWas_ipmi_power_on()==true?1:0);
			ps.setInt(9, chassisStatus.isWas_power_fault()==true?1:0);
			ps.setInt(10, chassisStatus.isWas_interlock()==true?1:0);
			ps.setInt(11, chassisStatus.isAc_failed()==true?1:0);
			ps.setInt(12, chassisStatus.isIs_chassis_identify_command_supported()==true?1:0);
			ps.setInt(13, chassisStatus.isCooling_fault_detected()==true?1:0);
			ps.setInt(14, chassisStatus.isDrive_fault_detected()==true?1:0);
			ps.setInt(15, chassisStatus.isIs_front_panel_lockout_active()==true?1:0);
			ps.setInt(16, chassisStatus.isIs_chassis_intrusion_active()==true?1:0);
			ps.setInt(25, chassisStatus.isIs_front_panel_button_capabilities_set()==true?1:0);
			if(chassisStatus.isIs_front_panel_button_capabilities_set()) {
				ps.setInt(17, chassisStatus.isIs_standby_button_disable_allowed()==true?1:0);
				ps.setInt(18, chassisStatus.isIs_diagnostic_interrupt_button_disable_allowed()==true?1:0);
				ps.setInt(19, chassisStatus.isIs_reset_button_disable_allowed()==true?1:0);
				ps.setInt(20, chassisStatus.isIs_power_off_button_disable_allowed()==true?1:0);
				ps.setInt(21, chassisStatus.isIs_standby_button_disabled()==true?1:0);
				ps.setInt(22, chassisStatus.isIs_diagnostic_interrupt_button_disabled()==true?1:0);
				ps.setInt(23, chassisStatus.isIs_reset_button_disabled()==true?1:0);
				ps.setInt(24, chassisStatus.isIs_power_off_button_disabled()==true?1:0);
			}else {
				ps.setInt(17, 2);
				ps.setInt(18, 2);
				ps.setInt(19, 2);
				ps.setInt(20, 2);
				ps.setInt(21, 2);
				ps.setInt(22, 2);
				ps.setInt(23, 2);
				ps.setInt(24, 2);
			}
			ps.executeUpdate();//
			ps.close();
		System.out.println("chassis_status插入数据成功"+ tablename);
		//}
	}
	
	public static void chassis_update_all(Connection conn,chassisStatus chassisStatus,serversip serversip, String tablename,Date date) throws Exception {
		String ip = serversip.getHostname().trim();
		//String lock = new String();
		//synchronized(lock) {
		String sql = "update "+"`"+"chassis_status_current" + "_" + ip  + "`" 
				+ "set start_time=?,is_power_fault=?,is_interlock=?,"
				+ "is_power_overload=?,is_power_on=?,was_power_fault=?,"
				+ "was_interlock=?,ac_failed=?,"
				+ "cooling_fault_detected=?,drive_fault_detected=?,"
				+ "is_chassis_intrusion_active=? where IP=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setTimestamp(1, new Timestamp(date.getTime()));
		ps.setInt(2, chassisStatus.isIs_power_fault()==true?1:0);
		ps.setInt(3, chassisStatus.isIs_interlock()==true?1:0);
		ps.setInt(4, chassisStatus.isIs_power_overload()==true?1:0);
		ps.setInt(5, chassisStatus.isIs_power_on()==true?1:0);
		ps.setInt(6, chassisStatus.isWas_power_fault()==true?1:0);
		ps.setInt(7, chassisStatus.isWas_interlock()==true?1:0);
		ps.setInt(8, chassisStatus.isAc_failed()==true?1:0);
		ps.setInt(9, chassisStatus.isCooling_fault_detected()==true?1:0);
		ps.setInt(10, chassisStatus.isDrive_fault_detected()==true?1:0);
		ps.setInt(11, chassisStatus.isIs_chassis_intrusion_active()==true?1:0);
		ps.setString(12, ip);
		ps.executeUpdate();
		ps.close();
		System.out.println("chassis_status_current更新数据成功"+ tablename);
		//}
	}
	
	public static void chassis_insert_all(Connection conn,chassisStatus chassisStatus,serversip serversip, String tablename,Date date) throws Exception {
		String ip = serversip.getHostname().trim();
		//String lock = new String();
		//synchronized(lock) {
		String sql = "insert into "+"`"+"chassis_status_current" + "_" + ip  + "`" 
				+ "(start_time,IP,"
				+ "is_power_fault,is_interlock,"
				+ "is_power_overload,is_power_on,"
				+ "was_power_fault,"
				+ "was_interlock,ac_failed,"
				+ "cooling_fault_detected,drive_fault_detected,"
				+ "is_chassis_intrusion_active) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);//
		ps.setTimestamp(1, new Timestamp(date.getTime()));
		ps.setString(2, ip);
		ps.setInt(3, chassisStatus.isIs_power_fault()==true?1:0);
		ps.setInt(4, chassisStatus.isIs_interlock()==true?1:0);
		ps.setInt(5, chassisStatus.isIs_power_overload()==true?1:0);
		ps.setInt(6, chassisStatus.isIs_power_on()==true?1:0);
		ps.setInt(7, chassisStatus.isWas_power_fault()==true?1:0);
		ps.setInt(8, chassisStatus.isWas_interlock()==true?1:0);
		ps.setInt(9, chassisStatus.isAc_failed()==true?1:0);
		ps.setInt(10, chassisStatus.isCooling_fault_detected()==true?1:0);
		ps.setInt(11, chassisStatus.isDrive_fault_detected()==true?1:0);
		ps.setInt(12, chassisStatus.isIs_chassis_intrusion_active()==true?1:0);
		ps.executeUpdate();//
		ps.close();
		System.out.println("chassis_status_current插入数据成功"+ tablename);
		//}
	}
	
	public static void fru_info(List<Connection> connectionlist,serversip serversip, String tablename,int port) throws Exception {
		Connection conn = connectionlist.get(0);
		List<fruInfo> list = ipmiutil.getFruInfo(serversip,port);
		Date date = newDate.newDate();
		Map<String,Integer> map = fruIpNum(connectionlist,serversip.getHostname().trim());
		int ipNum = map.get(serversip.getHostname());
		if(ipNum == 0){
			fru_insert_all(conn,list,serversip,tablename,date);
		}else if(ipNum == list.size()){
			fru_update_all(conn,list,serversip,tablename,date);
		}else {
			fru_delete_all(conn,serversip,tablename);
			fru_insert_all(conn,list,serversip,tablename,date);
		}
		
	}
	
	public static void fru_delete_all(Connection conn,serversip serversip, String tablename) throws Exception {
		String ip = serversip.getHostname().trim();
		String sql = "delete from "+"`"+"fru_info_current"+ "_" + ip  + "`" + " where IP=?";
		PreparedStatement ps = conn.prepareStatement(sql);//
		ps.setString(1, ip);
		ps.executeUpdate();
		ps.close();
		System.out.println("fru_info_current删除数据成功"+ tablename);
	}
	
	public static void fru_update_all(Connection conn,List<fruInfo> list,serversip serversip, String tablename,Date date) throws Exception {
		String ip = serversip.getHostname().trim();
		//String lock = new String();
		//synchronized(lock) {
		for (int i = 0; i < list.size(); i++) {
			String sql = "update "+"`"+"fru_info_current" + "_" + ip  + "`" 
					+ "set start_time=?,product_name=?,mfg_date=?,manufacturer=?," + 
					"serial_number=?,part_number=?,custom_info=?,model_number=? where name=? and IP=?";
			PreparedStatement ps = conn.prepareStatement(sql);//
			ps.setTimestamp(1, new Timestamp(date.getTime()));
			ps.setString(2,list.get(i).getProductName());
			ps.setTimestamp(3, list.get(i).getMfgDate()==null?null:new Timestamp(list.get(i).getMfgDate().getTime()));
			ps.setString(4,list.get(i).getManufacturer());
			ps.setString(5,list.get(i).getSerialNumber());
			ps.setString(6,list.get(i).getPartNumber());
			ps.setString(7,list.get(i).getCustomInfo());
			ps.setString(8,list.get(i).getModelNumber());
			ps.setString(9,list.get(i).getName());
			ps.setString(10, serversip.getHostname());
			ps.executeUpdate();
			ps.close();
		}
		System.out.println("fru_info_current更新数据成功"+ tablename);
		//}
	}
	
	public static void fru_insert_all(Connection conn,List<fruInfo> list,serversip serversip, String tablename,Date date) throws Exception {
		String ip = serversip.getHostname().trim();
		//String lock = new String();
		//synchronized(lock) {
		for (int i = 0; i < list.size(); i++) {
			String sql = "insert into "+"`"+"fru_info_current" + "_" + ip  + "`" 
					+ "(start_time,IP,name,product_name,mfg_date,manufacturer,"
					+ "serial_number,part_number,custom_info,model_number) "
					+ "values(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, new Timestamp(date.getTime()));
			ps.setString(2, serversip.getHostname());
			ps.setString(3,list.get(i).getName());
			ps.setString(4,list.get(i).getProductName());
			ps.setTimestamp(5, list.get(i).getMfgDate()==null?null:new Timestamp(list.get(i).getMfgDate().getTime()));
			ps.setString(6,list.get(i).getManufacturer());
			ps.setString(7,list.get(i).getSerialNumber());
			ps.setString(8,list.get(i).getPartNumber());
			ps.setString(9,list.get(i).getCustomInfo());
			ps.setString(10,list.get(i).getModelNumber());
			ps.executeUpdate();//
			ps.close();
		}
		System.out.println("fru_info_current插入数据成功"+ tablename);
		//}
	}
	
	public static void sel_record(List<Connection> connectionlist,serversip serversip, String tablename,int port) throws Exception {
		String ip = serversip.getHostname().trim();
		Connection conn = connectionlist.get(0);
		Map<String,Integer> map = a_num(connectionlist);
		int sel_num_max = map.get(serversip.getHostname());
		List<SelRecord> list = ipmiutil.getselrecord(serversip,sel_num_max,conn,port);
		Date date = newDate.newDate();
		sel_type(connectionlist,list,serversip,tablename);
		sel_name(connectionlist,list,serversip,tablename);
		//String lock = new String();
		//synchronized(lock) {
		if(list.get(0).getRecordId()==1) {
			for (int i = 0; i < list.size(); i++) {
				String sql = "insert into "+"`"+"sel_record" + "_" + ip  + "`" 
						+ "(start_time,IP,recordId,recordType,timestamp,sensorType,"
						+ "sensorNumber,eventDirection,event,reading) "
						+ "values(?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setTimestamp(1, new Timestamp(date.getTime()));
				ps.setString(2, ip);
				ps.setInt(3,list.get(i).getRecordId());
				ps.setString(4,list.get(i).getRecordType().toString().trim());
				ps.setTimestamp(5, list.get(i).getTimestamp()==null?null:new Timestamp(list.get(i).getTimestamp().getTime()));
				ps.setString(6,list.get(i).getSensorType().toString().trim());
				ps.setInt(7,list.get(i).getSensorNumber());
				ps.setString(8,list.get(i).getEventDirection().toString().trim());
				ps.setString(9,list.get(i).getEvent().toString().trim());
				ps.setInt(10,list.get(i).getReading());
				ps.executeUpdate();//
				ps.close();
			}
		}else {
			for (int i = 1; i < list.size(); i++) {
				String sql = "insert into "+"`"+"sel_record" + "_" + ip  + "`" 
						+ "(start_time,IP,recordId,recordType,timestamp,sensorType,"
						+ "sensorNumber,eventDirection,event,reading) "
						+ "values(?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setTimestamp(1, new Timestamp(date.getTime()));
				ps.setString(2, ip);
				ps.setInt(3,list.get(i).getRecordId());
				ps.setString(4,list.get(i).getRecordType().toString().trim());
				ps.setTimestamp(5, list.get(i).getTimestamp()==null?null:new Timestamp(list.get(i).getTimestamp().getTime()));
				ps.setString(6,list.get(i).getSensorType().toString().trim());
				ps.setInt(7,list.get(i).getSensorNumber());
				ps.setString(8,list.get(i).getEventDirection().toString().trim());
				ps.setString(9,list.get(i).getEvent().toString().trim());
				ps.setInt(10,list.get(i).getReading());
				ps.executeUpdate();//
				ps.close();
			}
		}
		System.out.println("sel_record插入数据成功"+ tablename);
		//}
	}
	
	public static Map<String,Integer> a_num(List<Connection> connectionlist) throws Exception{
		Connection conn = connectionlist.get(0);
		Map<String,Integer> map = new HashMap<String, Integer>();
		String selectsql = "select name,value from a_num";
		PreparedStatement selectps = conn.prepareStatement(selectsql);
		ResultSet rs = selectps.executeQuery();
		while (rs.next()) {
			map.put(rs.getString(1),rs.getInt(2));
		}
		rs.close();
		selectps.close();
		return map;
	}
	
	public static void a_num_update(Connection conn,List<String> list) throws Exception {
			String sql = "update a_num "
					+ "set value=? where name=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(list.get(0)));
			ps.setString(2, list.get(1));
			ps.executeUpdate();
			ps.close();
	}
	
	public static void sel_type(List<Connection> connectionlist,List<SelRecord> sellist,serversip serversip, String tablename) throws Exception {
		String ip = serversip.getHostname().trim();
		Connection conn = connectionlist.get(0);
		Set<String> set = new HashSet<String>();
		List<String> list = sel_type_select(connectionlist,serversip);
		for (int i = 1; i < sellist.size(); i++) {
			String sel_type = sellist.get(i).getSensorType().toString();
			if(!list.contains(sel_type)) {
				set.add(sel_type);
			}
		}
		
		Iterator<String> it = set.iterator();
		while(it.hasNext()) {
			String sql = "insert into "+"`"+"sel_type"+  "_" + ip + "`"  + " (sel_type) values(?)";
			PreparedStatement ps = conn.prepareStatement(sql);//
			ps.setString(1,it.next());
			ps.executeUpdate();
			ps.close();
		}
		System.out.println("sel_type插入数据成功"+ tablename);
	}
	
	public static List<String> sel_type_select(List<Connection> connectionlist,serversip serversip) throws Exception{
		String ip = serversip.getHostname().trim();
		Connection conn = connectionlist.get(0);
		List<String> list = new ArrayList<String>();
		String selectsql = "select sel_type from" +"`"+"sel_type"+"_" + ip + "`" ;
		PreparedStatement selectps = conn.prepareStatement(selectsql);
		ResultSet rs = selectps.executeQuery();
		while (rs.next()) {
			list.add(rs.getString(1));
		}
		rs.close();
		selectps.close();
		return list;
	}
	
	public static void sel_name(List<Connection> connectionlist,List<SelRecord> sellist,serversip serversip, String tablename) throws Exception {
		String ip = serversip.getHostname().trim();
		Connection conn = connectionlist.get(0);
		Set<String> set = new HashSet<String>();
		List<String> list = sel_name_select(connectionlist,serversip);
		for (int i = 1; i < sellist.size(); i++) {
			String sel_name = sellist.get(i).getEvent().toString();
			if(!list.contains(sel_name)) {
				set.add(sel_name);
			}
		}
		
		Iterator<String> it = set.iterator();
		while(it.hasNext()) {
			String sql = "insert into "+"`"+"sel_name"  + "_" + ip + "`"  + " (sel_name) values(?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,it.next());
			ps.executeUpdate();
			ps.close();
		}
		System.out.println("sel_name插入数据成功"+ tablename);
	}
	
	public static List<String> sel_name_select(List<Connection> connectionlist,serversip serversip) throws Exception{
		String ip = serversip.getHostname().trim();
		Connection conn = connectionlist.get(0);
		List<String> list = new ArrayList<String>();
		String selectsql = "select sel_name from " +"`"+ "sel_name" + "_" + ip + "`";
		PreparedStatement selectps = conn.prepareStatement(selectsql);
		ResultSet rs = selectps.executeQuery();
		while (rs.next()) {
			list.add(rs.getString(1));
		}
		rs.close();
		selectps.close();
		return list;
	}
	
	public static void insertGet_time(List<Connection> connectionlist, String ip,String tableName,IpmiListener IpmiListener) throws Exception {
		Connection conn = connectionlist.get(0);
		String newTableName = tableName.substring(0,tableName.length()-2);
		String sql = "insert into "+"`"+"get_time_"  + newTableName + "_" + ip + "`"  + " (end_time,sensor_time,program_time,thread) values(?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);//
		ps.setTimestamp(1, new Timestamp(IpmiListener.getEnd_time().getTime()));
		ps.setString(2,IpmiListener.getSensor_time());
		ps.setString(3,IpmiListener.getProgram_time());
		ps.setString(4,IpmiListener.getThread());
		ps.executeUpdate();//
		ps.close();
	}
	
	public static void insertSnmp(Connection conn,String hostname,List<snmp> list,Date date) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			snmp snmp = new snmp();
			snmp = list.get(i);
			String sql = "insert into"+"`"+"snmp_value"+ "_" + hostname  + "`"+
			"(start_time,hostname,name,oid,value) values(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);//
			ps.setTimestamp(1, new Timestamp(date.getTime()));
			ps.setString(2, snmp.getHostname());
			ps.setString(3, snmp.getChinese_des());
			ps.setString(4, snmp.getOid());
			ps.setString(5,snmp.getValue());
			ps.executeUpdate();//
			ps.close();
		}
	}
	
	public static void updateSnmp(Connection conn,String hostname,List<snmp> list,Date date) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			snmp snmp = new snmp();
			snmp = list.get(i);
			String sql = "update "+"`"+"snmp_value" + "_" + hostname  + "`" 
					+ "set start_time=?,value=? where oid=?";
			PreparedStatement ps = conn.prepareStatement(sql);//
			ps.setTimestamp(1, new Timestamp(date.getTime()));
			ps.setString(2,snmp.getValue());
			ps.setString(3, snmp.getOid());
			ps.executeUpdate();
			ps.close();
		}
	}
	
	public static void deleteSnmp(Connection conn,String hostname) throws Exception {
		String sql = "delete from "+"`"+"snmp_value"+ "_" + hostname  + "`";
		PreparedStatement ps = conn.prepareStatement(sql);//
		ps.executeUpdate();
		ps.close();
	}
	
	public static void snmpValue(List<Connection> connectionlist,String hostname) throws Exception {
		Connection conn = connectionlist.get(0);
		List<snmp> list = insertUtil.snmpValue(conn,hostname);
		Date date = newDate.newDate();
		Map<String,Integer> map = snmpValueIpNum(connectionlist,hostname);
		int ipNum = map.get(hostname);
		System.out.println(ipNum+"=="+list.size()+"snmpValue");
		if(ipNum == 0){
			insertSnmp(conn,hostname,list,date);
		}else if(ipNum == list.size()){
			updateSnmp(conn,hostname,list,date);
		}else {
			deleteSnmp(conn,hostname);
			insertSnmp(conn,hostname,list,date);
		}
		
	}
	public static Map<String,Integer> snmpValueIpNum(List<Connection> connectionlist,String hostname) throws Exception{
		Connection conn = connectionlist.get(0);
		List<String> ipList = new ArrayList<String>();
		String selectsql = "select oid from "+"`"+"snmp_value" + "_" + hostname + "`";
		PreparedStatement selectps = conn.prepareStatement(selectsql);
		ResultSet rs = selectps.executeQuery();
		while (rs.next()) {
			ipList.add(rs.getString(1));
		}
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put(hostname, ipList.size());
		rs.close();
		selectps.close();
		return map;
	}
}

