package com.gydz.user.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.warnhistoryMapper;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;

@Service("warnhistoryService")
public class warnhistoryImpl implements warnhistoryService{
	
	@Resource
    private warnhistoryMapper warnhistoryMapper;

	public void add(sensorrecord sensorrecord) {
		warnhistoryMapper.add(sensorrecord);
	}

	public List<sensorrecord> getlistbyparam(QueryParam param) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("queryType", param.getQueryType());
		map.put("IPType", param.getIPType());
		map.put("nameType", param.getNameType());
		map.put("datemin", param.getDatemin());
		map.put("datemax", param.getDatemax());
		return warnhistoryMapper.getlistbyparam(map);
	}

	public List<sensorrecord> getwarnnotok() {
		return warnhistoryMapper.getwarnnotok();
	}

	public void update(QueryParam param) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("end_time", param.getEnd_time());
		map.put("IPnamelevel", param.getNameType());
		warnhistoryMapper.update(map);
	}
}
