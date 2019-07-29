package com.gydz.user.service;

import java.util.List;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.ipmiwarnmodel;
import com.gydz.user.model.sensorrecord;

public interface ipmiwarnService {

	public List<sensorrecord> getsensorwarnrefer();
	
	public List<String> getType();
	
	public List<sensorrecord> getsensorwarnreferbyid(String IPType);
	
	public List<sensorrecord> getsensorwarnreferbyparam(QueryParam param);
}
