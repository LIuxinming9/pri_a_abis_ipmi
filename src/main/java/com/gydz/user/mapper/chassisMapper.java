package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gydz.user.model.chassisStatus;

public interface chassisMapper {

	List<chassisStatus> getChassisInfo();

	List<chassisStatus> getChassisInfobyParam(Map map);

	List<chassisStatus> getChassiscurrentInfobyParam(HashMap<String, String> map);

}
