package com.gydz.user.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.snmpSetMapper;
import com.gydz.user.model.snmp;

@Service("snmpSetService")
public class snmpSetImpl implements  snmpSetService{

	 @Resource
	 private snmpSetMapper snmpSetMapper;
	 
	public List<snmp> get() {
		return snmpSetMapper.get();
	}

	public List<snmp> getByKeyword(String keyword) {
		return snmpSetMapper.getByKeyword(keyword);
	}

	public List<snmp> getOID() {
		return snmpSetMapper.getOID();
	}

	public List<snmp> getOIDByIPType(String iPType) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("iPType", iPType);
		return snmpSetMapper.getOIDByIPType(map);
	}

	public void insert(snmp snmp) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("iid", snmp.getIid());
		map.put("ooid", snmp.getOoid());
		snmpSetMapper.insert(map);
	}

	public int getIidByIP(String hostname) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("hostname", hostname);
		snmp snmp = snmpSetMapper.getIidByIP(map);
		return snmp.getId();
	}

	public void delByIp(snmp snmp) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("iid", snmp.getIid());
		map.put("ooid", snmp.getOoid());
		snmpSetMapper.delByIp(map);
	}

	public void saveByBrand(snmp snmp) {
		snmpSetMapper.saveByBrand(snmp);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("oid", snmp.getOid());
		int id = snmpSetMapper.getIdByOid(map);
		HashMap<String,Object> newMap = new HashMap<String,Object>();
		newMap.put("brand", snmp.getBrand());
		List<Integer> ids = snmpSetMapper.getIdsByBrand(newMap);
		HashMap<String,Integer> map1 = new HashMap<String,Integer>();
		for (int i = 0; i < ids.size(); i++) {
			map1.put("iid", ids.get(i));
			map1.put("ooid", id);
			snmpSetMapper.setIdOid(map1);
		}
	}

	public List<snmp> getOIDByBrand(String brand) {
		List<snmp> list = snmpSetMapper.getOIDByBrand(brand);
		return list;
	}

	public snmp getByOid(String oid) {
		snmp snmp = snmpSetMapper.getByOid(oid);
		return snmp;
	}

}
