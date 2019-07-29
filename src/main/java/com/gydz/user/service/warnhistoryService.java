package com.gydz.user.service;

import java.util.List;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;

public interface warnhistoryService {

	public void add(sensorrecord sensorrecord);
	
	public List<sensorrecord> getlistbyparam(QueryParam param);
	
	public List<sensorrecord> getwarnnotok();
	
	public void update(QueryParam param);
}
