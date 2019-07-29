package com.gydz.user.service;

import java.util.List;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.chassisStatus;

public interface chassisService {

	public List<chassisStatus> getChassisInfobyParam(QueryParam param);
	
	public List<chassisStatus> getChassiscurrentInfobyParam(QueryParam param);
}
