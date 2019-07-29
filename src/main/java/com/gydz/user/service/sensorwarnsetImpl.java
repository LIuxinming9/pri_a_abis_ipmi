package com.gydz.user.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.sensorwarnsetMapper;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;

@Service("sensorwarnsetService")
public class sensorwarnsetImpl implements sensorwarnsetService{
	
	@Resource
	private sensorwarnsetMapper sensorwarnsetMapper;

	public List<sensorrecord> getsensorwarnsetbyparam(QueryParam param) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("queryType", param.getQueryType());
		map.put("IPType", param.getIPType());
		map.put("nameType", param.getNameType());
		return sensorwarnsetMapper.getsensorwarnsetbyparam(map);
	}

	public void delSysIP(QueryParam param) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("IPType", param.getIPType());
		map.put("nameType", param.getNameType());
		sensorwarnsetMapper.delSysIP(map);
	}

	public void add(sensorrecord sensorrecord) {
		sensorwarnsetMapper.add(sensorrecord);
	}

	public void update(sensorrecord sensorrecord) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("IP", sensorrecord.getIP());
		map.put("name", sensorrecord.getName());
		map.put("IPname", sensorrecord.getIPname());
		map.put("sensor_type", sensorrecord.getSensor_type());
		map.put("upper_non_critical_threshold", sensorrecord.getUpper_non_critical_threshold());
		map.put("lower_non_critical_threshold", sensorrecord.getLower_non_critical_threshold());
		map.put("upper_critical_threshold", sensorrecord.getUpper_critical_threshold());
		map.put("lower_critical_threshold", sensorrecord.getLower_critical_threshold());
		map.put("upper_non_recoverable_threshold", sensorrecord.getUpper_non_recoverable_threshold());
		map.put("lower_non_recoverable_threshold", sensorrecord.getLower_non_recoverable_threshold());
		sensorwarnsetMapper.update(map);
	}

	public List<sensorrecord> getIPname() {
		return sensorwarnsetMapper.getIPname();
	}

}
