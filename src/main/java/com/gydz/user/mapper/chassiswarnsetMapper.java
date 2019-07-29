package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;

import com.gydz.user.model.sensorrecord;

public interface chassiswarnsetMapper {

	public void add(HashMap<String,String> map);
	
	public List<sensorrecord> getIPname(HashMap<String, String> map);
	
	public void del(HashMap<String,String> map);
	
	public void update(sensorrecord sensorrecord);
}
