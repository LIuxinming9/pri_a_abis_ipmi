package com.gydz.user.service;

import java.util.List;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.snmp;

public interface snmpService {

	public List<snmp> getTypeByParam(QueryParam param);
	
	public List<snmp> getNameByParam(QueryParam param);
	
	public List<snmp> getValueByParam(QueryParam param);
	
	public void addName(snmp snmp);
	
	public List<snmp> getName();
	
	public List<snmp> getAllName();
	
	public void updateName(snmp snmp);
	
	public void updateAllName(snmp snmp);
	
	public void delName(int i);
	
	public void delAllName(int i);
	
	public void addAllName(snmp snmp);
}
