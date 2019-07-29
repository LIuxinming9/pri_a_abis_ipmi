package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;

import com.gydz.user.model.snmp;

public interface snmpMapper {

	public List<snmp> getTypeByParam(HashMap<String,String> map);
	
	public List<snmp> getNameByParam(HashMap<String,String> map);
	
	public List<snmp> getValueByParam(HashMap<String,String> map);
	
	public void addName(snmp snmp);
	
	public void addAllName(snmp snmp);
	
	public List<snmp> getName();
	
	public List<snmp> getAllName();
	
	public void updateName(snmp snmp);
	
	public void updateAllName(snmp snmp);
	
	public void delName(int id);
	
	public void delAllName(int id);
}
