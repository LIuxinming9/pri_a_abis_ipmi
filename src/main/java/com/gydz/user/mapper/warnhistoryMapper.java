package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;

import com.gydz.user.model.sensorrecord;

public interface warnhistoryMapper {

	public void add(sensorrecord sensorrecord);
	
	public List<sensorrecord> getlistbyparam(HashMap<String,String> map);
	
	public List<sensorrecord> getwarnnotok();
	
	public void update(HashMap<String, String> map);
}
