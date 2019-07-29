package com.gydz.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.schemaMapper;
import com.gydz.user.mapper.snmpMapper;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.snmp;

@Service("snmpService")
public class snmpImpl implements  snmpService{
	
	@Resource
    private snmpMapper snmpMapper;

	@Resource
	private schemaMapper schemaMapper;

	public List<snmp> getTypeByParam(QueryParam param) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("IPType", param.getIPType());
		List<snmp> List = snmpMapper.getTypeByParam(map);
		List<snmp> snmpList = new ArrayList<snmp>();
		snmpList = snmpMapper.getAllName();
		for (int i = 0; i < snmpList.size(); i++) {
			if(snmpList.get(i).getHostname().equals(param.getIPType())) {
				List.add(snmpList.get(i));
			}
		}
		return List;
	}

	public List<snmp> getNameByParam(QueryParam param) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("IPType", param.getIPType());
		map.put("queryType", param.getNameType());
		List<snmp> List = snmpMapper.getNameByParam(map);
		List<snmp> snmpList = new ArrayList<snmp>();
		snmpList = snmpMapper.getAllName();
		for (int i = 0; i < snmpList.size(); i++) {
			String hostname = snmpList.get(i).getHostname();
			String IPType = param.getIPType();
			if(hostname.equals(IPType)||IPType.equals("全部")) {
				List.add(snmpList.get(i));
			}
		}
		return List;
	}

	public List<snmp> getValueByParam(QueryParam param) {
		List<snmp> sensorRecordesList = new ArrayList<snmp>();
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			String ip = param.getIPType();
			if(ip==null || ip.equals("全部")) {
				if(tablename.contains("snmp_value_")) {
					List<snmp> sensorrecordlist = new ArrayList<snmp>();
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("queryType", param.getQueryType());
					map.put("nameType", param.getNameType());
					map.put("entity_id", param.getEntity_id());
					map.put("tablename", tablename);
					sensorrecordlist = snmpMapper.getValueByParam(map);
					for (int j = 0; j < sensorrecordlist.size(); j++) {
						sensorRecordesList.add(sensorrecordlist.get(j));
					}
				}
			}else {
				if(tablename.contains("snmp_value_") && tablename.contains(ip)) {
					List<snmp> sensorrecordlist = new ArrayList<snmp>();
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("queryType", param.getQueryType());
					map.put("nameType", param.getNameType());
					map.put("entity_id", param.getEntity_id());
					map.put("tablename", tablename);
					sensorrecordlist = snmpMapper.getValueByParam(map);
					for (int j = 0; j < sensorrecordlist.size(); j++) {
						sensorRecordesList.add(sensorrecordlist.get(j));
					}
				}
			}
			
		}
		List<snmp> snmpList = new ArrayList<snmp>();
		snmpList = snmpMapper.getAllName();
		for (int i = 0; i < snmpList.size(); i++) {
			sensorRecordesList.add(snmpList.get(i));
		}
		return sensorRecordesList;
	}

	public void addName(snmp snmp) {
		snmpMapper.addName(snmp);
	}

	public List<snmp> getName() {
		List<snmp> snmpList = new ArrayList<snmp>();
		snmpList = snmpMapper.getName();
		return snmpList;
	}

	public void updateName(snmp snmp) {
		snmpMapper.updateName(snmp);
	}

	public void delName(int id) {
		snmpMapper.delName(id);
	}

	public void addAllName(snmp snmp) {
		snmpMapper.addAllName(snmp);
	}

	public List<snmp> getAllName() {
		List<snmp> snmpList = new ArrayList<snmp>();
		snmpList = snmpMapper.getAllName();
		return snmpList;
	}

	public void updateAllName(snmp snmp) {
		snmpMapper.updateAllName(snmp);
	}

	public void delAllName(int id) {
		snmpMapper.delAllName(id);
	}
}
