package com.gydz.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.name;
import com.gydz.user.model.sel_type;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;

public interface sensorService {

	public List<sensorrecord> getInfobyParam(QueryParam param);
	
	public List<sensorrecord> getcurrentInfobyParam(QueryParam param);
	
	public List<serversip> getServersip();
	
	public void addServersip();
	
	public void updateSysIP(serversip serversip);
	
	public void delSysIP(int id);
	
	public Map<String,String> getSysname();
	
	public List<name> getName(QueryParam param);
	
	public Set<String> getEntityid();
	
	public List<sensorrecord> getIPnamenum();
	
	public List<serversip> selectIsOnline();
	
	public void updateIsOnline();
}
