package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gydz.user.model.name;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;

public interface sensorMapper {

	public List<sensorrecord> getInfo();

	public List<sensorrecord> getInfobyParam(HashMap<String, String> map);

	public List<serversip> getServersip();

	public void addServersip();

	public void updateSysIP(HashMap<String, Object> map);

	public void delSysIP(int id);
	public void delIid(int id);

	public List<sensorrecord> getcurrentInfobyParam(HashMap<String, String> map);

	public List<name> getSensorfa_name();

	public List<name> getName(HashMap<String, String> map);

	public List<String> getEntityid(HashMap<String, String> map);
	
	public List<sensorrecord> getIPnamenum(HashMap<String, String> map);
	
	public List<serversip> selectIsOnline();
	
	public void updateIsOnline();
}
