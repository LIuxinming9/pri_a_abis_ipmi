package com.gydz.user.service;

import java.util.List;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;

public interface sensorwarnsetService {

	public List<sensorrecord> getsensorwarnsetbyparam(QueryParam param);
	
	public void delSysIP(QueryParam param);
	
	public void add(sensorrecord sensorrecord);
	
	public void update(sensorrecord sensorrecord);
	
	public List<sensorrecord> getIPname();
}
