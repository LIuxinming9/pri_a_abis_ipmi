package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gydz.user.model.ipmiwarnmodel;
import com.gydz.user.model.sensorrecord;


public interface ipmiwarnMapper {

	public List<ipmiwarnmodel> getAllipmiwarnmodel();

	public List<sensorrecord> getsensorwarnreferbyid(@Param("IPType") String IPType);
	
	public List<sensorrecord> getsensorwarnrefer();
	
	public List<sensorrecord> getsensorwarnreferbyparam(HashMap<String,String> map);
}
