package com.gydz.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.gydz.user.model.chassisStatus;
import com.gydz.user.model.fruInfo;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.model.snmp;
import com.veraxsystems.vxipmi.coding.commands.fru.record.FruRecord;
import com.veraxsystems.vxipmi.coding.commands.sel.SelRecord;

public class insertUtil {

	public static void main(String[] args) throws Exception {
		Date date = new Date(Long.valueOf("1552924802417"));
		SimpleDateFormat dts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String format = dts.format(date);
		System.out.println(format);
		System.out.println(Double.parseDouble(new DecimalFormat("0.00").format(322.0100000001)));
	}

	public static void inserth(List<Connection> connectionlist) throws Exception {
		
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
		String tablename = dateFormat.format(date);
		createtable(connectionlist,tablename);
		
		//
		
		List<serversip> list = serversip(connectionlist);
		for (int i = 0; i < list.size(); i++) {
			//fru_info(connectionlist,list.get(i),tablename);
		}
		Date endDate = new Date();
		System.out.println("历史数据"+(endDate.getTime()-date.getTime()));
	}
	
	public static void createtable(List<Connection> connectionlist,String tablename) throws Exception {
		
		//createTableUtil.createTablefru_info(connectionlist,tablename);
		
		
		
	}

	public static List<serversip> serversip(List<Connection> connectionlist) throws Exception{
		Connection conn = connectionlist.get(0);
		List<serversip> list = new ArrayList<serversip>();
		String selectsql = "select hostname,username,password,bmc_key from serversip where snmp_onlion = 0";
		PreparedStatement selectps = conn.prepareStatement(selectsql);
		ResultSet rs = selectps.executeQuery();
		while (rs.next()) {
			serversip serversip = new serversip();
			serversip.setHostname(rs.getString(1));
			serversip.setUsername(rs.getString(2));
			serversip.setPassword(rs.getString(3));
			serversip.setBmcKey(rs.getString(4)==null?"":rs.getString(4));
			list.add(serversip);
		}
		rs.close();
		selectps.close();
		return list;
		
	}
	
	//SNMP协议
	public static List<snmp> selectGetOid(Connection conn,String hostname) throws Exception{
		List<snmp> list = new ArrayList<snmp>();
		String sql = "select o.chinese_des,o.way,i.community,"
				+ "o.oid from snmp_oid o,snmp_id_oid io,serversip"
				+ " i where o.id=io.ooid and io.iid=i.id and i.snmp_online=0 and i.hostname="+"'"+hostname+"'";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getString("way").equals("get")) {
				snmp snmp = new snmp();
				snmp.setHostname(hostname);
				snmp.setChinese_des(rs.getString("chinese_des"));
				snmp.setCommunity(rs.getString("community"));
				snmp.setOid(rs.getString("oid"));
				list.add(snmp);
			}
		}
		return list;
	}
	
	public static List<snmp> selectWalkOid(Connection conn,String hostname) throws Exception{
		List<snmp> list = new ArrayList<snmp>();
		String sql = "select i.hostname,o.chinese_des,o.way,i.community,o.oid"
				+ " from snmp_oid o,snmp_id_oid io,serversip i "
				+ "where o.id=io.ooid and io.iid=i.id and i.snmp_online=0 and i.hostname="+"'"+hostname+"'";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getString("way").equals("walk")) {
				snmp snmp = new snmp();
				snmp.setHostname(hostname);
				snmp.setChinese_des(rs.getString("chinese_des"));
				snmp.setCommunity(rs.getString("community"));
				snmp.setOid(rs.getString("oid"));
				list.add(snmp);
			}
		}
		return list;
	}
	
	public static List<snmp> snmpValue(Connection conn,String hostname) throws Exception{
		List<snmp> list = new ArrayList<snmp>();
		List<snmp> snmpList = selectGetOid(conn,hostname);
		List<snmp> walkList = selectWalkOid(conn,hostname);
		for (int i = 0; i < snmpList.size(); i++) {
			snmp snmp = new snmp();
			snmp = snmpList.get(i);
			String value = SnmpData.snmpGet(snmp.getHostname(), snmp.getCommunity(), snmp.getOid());
			snmp.setValue(value);
			list.add(snmp);
		}
		for (int i = 0; i < walkList.size(); i++) {
			List<String> strList = new ArrayList<String>();
			snmp snmp = new snmp();
			snmp = walkList.get(i);
			strList = SnmpData.snmpWalk(snmp.getHostname(), snmp.getCommunity(), snmp.getOid());
			for (int j = 0; j < strList.size(); j++) {
				String str = strList.get(j);
				snmp snmpj = new snmp();
				snmpj.setHostname(snmp.getHostname());
				snmpj.setValue(str.substring(0, str.indexOf("=")));
				snmpj.setOid(str.substring(str.indexOf('=')+1));
				snmpj.setChinese_des(snmp.getChinese_des());
				list.add(snmpj);
			}
		}
		return list;
	}
	
	public static void insertSnmp(Connection conn, String tablename,String hostname) throws Exception {
		List<snmp> list = snmpValue(conn,hostname);
		Date date = new Date();
		for (int i = 0; i < list.size(); i++) {
			snmp snmp = new snmp();
			snmp = list.get(i);
			String sql = "insert into snmp_value"  + tablename
					+ " (start_time,hostname,name,oid,value) values(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);//
			ps.setTimestamp(1, new Timestamp(date.getTime()));
			ps.setString(2, snmp.getHostname());
			ps.setString(3, snmp.getChinese_des());
			ps.setString(4,snmp.getOid());
			ps.setString(5,snmp.getValue());
			ps.executeUpdate();//
			ps.close();
		}
	}
}

