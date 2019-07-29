package com.gydz.user.service;

import java.util.List;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;

public interface chassiswarnsetService {

	public void add(String iPType, String nameType);
	
	public List<sensorrecord> getIPname(QueryParam param);
	
	public void del(QueryParam param);
	
	public void update(sensorrecord sensorrecord);
}
