package com.gydz.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.name;
import com.gydz.user.model.sel_type;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.model.snmp;
import com.gydz.util.stritodate;
import com.sun.org.apache.bcel.internal.generic.StoreInstruction;
import com.ctc.wstx.util.StringUtil;
import com.gydz.user.mapper.schemaMapper;
import com.gydz.user.mapper.sensorMapper;

@Service("sensorService")
public class sensorImpl implements sensorService{

	@Resource
	private sensorMapper sensorMapper;
	
	@Resource
	private schemaMapper schemaMapper;
	
	public List<sensorrecord> getInfobyParam(QueryParam param) {
		List<sensorrecord> sensorRecordesList = new ArrayList<sensorrecord>();
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			String ip = param.getIPType();
			if(ip==null || ip.equals("全部")) {
				if(tablename.contains("sensor_record_2")) {
					List<sensorrecord> sensorrecordlist = new ArrayList<sensorrecord>();
					sensorrecordlist = selectSensorInfobyParam(tablename,param);
					for (int j = 0; j < sensorrecordlist.size(); j++) {
						sensorRecordesList.add(sensorrecordlist.get(j));
					}
				}
			}else {
				if(tablename.contains("sensor_record_2") && tablename.contains(ip)) {
					List<sensorrecord> sensorrecordlist = new ArrayList<sensorrecord>();
					sensorrecordlist = selectSensorInfobyParam(tablename,param);
					for (int j = 0; j < sensorrecordlist.size(); j++) {
						sensorRecordesList.add(sensorrecordlist.get(j));
					}
				}
			}
		}
		return sensorRecordesList;
	}
	
	public List<sensorrecord> selectSensorInfobyParam(String tablename,QueryParam param) {
		List<sensorrecord> sensorrecordlist = new ArrayList<sensorrecord>();
		long tabletime = Long.parseLong(tablename.substring(tablename.indexOf("192")-9, tablename.indexOf("192")-1));
		long datemin = Long.parseLong(stritodate.strtodatetostr(param.getDatemin()));
		long datemax = Long.parseLong(stritodate.strtodatetostr(param.getDatemax()));
		if(tabletime>=datemin && tabletime<=datemax) {
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("queryType", param.getQueryType());
			map.put("nameType", param.getNameType());
			map.put("tablename", tablename);
			List<sensorrecord> sensorrecord = new ArrayList<sensorrecord>();
			sensorrecord = sensorMapper.getInfobyParam(map);
			for (int j = 0; j < sensorrecord.size(); j++) {
				sensorrecordlist.add(sensorrecord.get(j));
			}
		}
		return sensorrecordlist;
	}

	public List<sensorrecord> getcurrentInfobyParam(QueryParam param) {
		List<sensorrecord> sensorRecordesList = new ArrayList<sensorrecord>();
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			String ip = param.getIPType();
			if(ip==null || ip.equals("全部")) {
				if(tablename.contains("sensor_record_c")) {
					List<sensorrecord> sensorrecordlist = new ArrayList<sensorrecord>();
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("queryType", param.getQueryType());
					map.put("nameType", param.getNameType());
					map.put("entity_id", param.getEntity_id());
					map.put("tablename", tablename);
					sensorrecordlist = sensorMapper.getcurrentInfobyParam(map);
					for (int j = 0; j < sensorrecordlist.size(); j++) {
						sensorRecordesList.add(sensorrecordlist.get(j));
					}
				}
			}else {
				if(tablename.contains("sensor_record_c") && tablename.contains(ip)) {
					List<sensorrecord> sensorrecordlist = new ArrayList<sensorrecord>();
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("queryType", param.getQueryType());
					map.put("nameType", param.getNameType());
					map.put("entity_id", param.getEntity_id());
					map.put("tablename", tablename);
					sensorrecordlist = sensorMapper.getcurrentInfobyParam(map);
					for (int j = 0; j < sensorrecordlist.size(); j++) {
						sensorRecordesList.add(sensorrecordlist.get(j));
					}
				}
			}
			
		}
		return sensorRecordesList;
	}
	
	public List<serversip> getServersip() {
		return sensorMapper.getServersip();
	}

	public void addServersip() {
		sensorMapper.addServersip();
	}

	public void updateSysIP(serversip serversip) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", serversip.getId());
		map.put("name", serversip.getName());
		map.put("brand", serversip.getBrand());
		map.put("location", serversip.getLocation());
		map.put("note", serversip.getNote());
		map.put("hostname", serversip.getHostname());
		map.put("username", serversip.getUsername());
		map.put("password", serversip.getPassword());
		map.put("bmcKey", serversip.getBmcKey());
		map.put("is_online", serversip.getIs_online());
		sensorMapper.updateSysIP(map);
	}

	public void delSysIP(int id) {
		sensorMapper.delIid(id);
		sensorMapper.delSysIP(id);
	}


	public List<name> getName(QueryParam param) {
		List<name> strsList = new ArrayList<name>();
		
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			String ip = param.getIPType();
			if(tablename.contains("sensor_record_c")) {
				List<name> strList = new ArrayList<name>();
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("queryType", param.getQueryType());
				map.put("tablename", tablename);
				strList = sensorMapper.getName(map);
				for (int j = 0; j < strList.size(); j++) {
					strsList.add(strList.get(j));
				}
			}
			
		}
		return strsList;
	}

	public Set<String> getEntityid() {
		Set<String> entityidset = new LinkedHashSet<String>();
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			if(tablename.contains("sensor_record_c")) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("tablename", tablename);
				List<String> entityid = null;
		  		entityid = sensorMapper.getEntityid(map);
		  		for (int j = 0; j < entityid.size(); j++) {
		  			entityidset.add(entityid.get(j));
				}
			}
		}
		return entityidset;
	}

	@Override
	public Map<String, String> getSysname() {
		List<serversip> serversiplist = getServersip();
		Map<String,String> map = new HashMap<String,String>();
		for (int i = 0; i < serversiplist.size(); i++) {
			map.put(serversiplist.get(i).getHostname(), serversiplist.get(i).getName());
			//map.put(serversiplist.get(i).getIp(), serversiplist.get(i).getName());
		}
		return map;
	}

	public List<sensorrecord> getIPnamenum() {
		List<sensorrecord> sensorRecordesList = new ArrayList<sensorrecord>();
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			if(tablename.contains("sensor_record_c")) {
				List<sensorrecord> sensorRecordList = new ArrayList<sensorrecord>();
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("tablename", tablename);
				sensorRecordList = sensorMapper.getIPnamenum(map);
				for (int j = 0; j < sensorRecordList.size(); j++) {
					sensorRecordesList.add(sensorRecordList.get(j));
				}
			}
		}
		return sensorRecordesList;
	}

	public List<serversip> selectIsOnline() {
		return sensorMapper.selectIsOnline();
	}

	public void updateIsOnline() {
		sensorMapper.updateIsOnline();
	}

}
