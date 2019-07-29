package com.gydz.user.service;

import java.util.List;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;

public interface resourcewarnsetService {

	public void delSysIP(QueryParam param);
	
	public void add(sensorrecord sensorrecord);
	
	public void update(sensorrecord sensorrecord);
	
	public List<sensorrecord> getresourcewarnsetbyparam(QueryParam param);
	
	public void refresh(String nameip, String emptyname);
}
