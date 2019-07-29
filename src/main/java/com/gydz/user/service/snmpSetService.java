package com.gydz.user.service;

import java.util.List;

import com.gydz.user.model.snmp;

public interface snmpSetService {
	

	public List<snmp> get();

	public List<snmp> getByKeyword(String keyword);
	
	public List<snmp> getOID();
	
	public List<snmp> getOIDByIPType(String iPType);
	
	public void insert(snmp snmp);
	
	public void delByIp(snmp snmp);
	
	public int getIidByIP(String ip);
	
	public void saveByBrand(snmp snmp);
	
	public List<snmp> getOIDByBrand(String brand);
	
	public snmp getByOid(String oid);
}
