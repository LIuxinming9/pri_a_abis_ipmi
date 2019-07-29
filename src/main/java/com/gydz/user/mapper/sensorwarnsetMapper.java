package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;

import com.gydz.user.model.sensorrecord;

public interface sensorwarnsetMapper {

	public List<sensorrecord> getsensorwarnsetbyparam(HashMap<String,String> map);
	
	public void delSysIP(HashMap<String,String> map);
	
	public void add(sensorrecord sensorrecord);
	
	public void update(HashMap<String, Object> map);
	
	public List<sensorrecord> getIPname();
}
