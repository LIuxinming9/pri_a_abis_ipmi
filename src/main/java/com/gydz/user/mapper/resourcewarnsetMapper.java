package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;

import com.gydz.user.model.sensorrecord;

public interface resourcewarnsetMapper {

	public void delSysIP(HashMap<String,String> map);
	
	public void add(sensorrecord sensorrecord);
	
	public void update(HashMap<String, Object> map);
	
	public void refresh(HashMap<String, Object> map);
	
	public List<sensorrecord> getresourcewarnsetbyparam(HashMap<String,String> map);
}
