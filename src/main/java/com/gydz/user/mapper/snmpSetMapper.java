package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;

import com.gydz.user.model.snmp;

public interface snmpSetMapper {
	
	public List<snmp> get();

	public List<snmp> getByKeyword(String keyword);
	
	public List<snmp> getOID();
	
	public List<snmp> getOIDByIPType(HashMap<String, Object> map);
	
	public snmp getIidByIP(HashMap<String, Object> map);
	
	public void insert(HashMap<String, Object> map);
	
	public void delByIp(HashMap<String, Object> map);
	
	public void saveByBrand(snmp snmp);
	
	public int getIdByOid(HashMap<String, Object> map);
	
	public List<Integer> getIdsByBrand(HashMap<String, Object> map);
	
	public void setIdOid(HashMap<String,Integer> map);
	
	public List<snmp> getOIDByBrand(String brand);
	
	public snmp getByOid(String oid);
}
