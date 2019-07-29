package com.gydz.user.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.chassiswarnsetMapper;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;

@Service("chassiswarnsetService")
public class chassiswarnsetImpl implements chassiswarnsetService{

	@Resource
	private chassiswarnsetMapper chassiswarnsetMapper;
	
	public void add(String iPType, String nameType) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("IPType",iPType);
		map.put("nameType",nameType);
		map.put("IPname", iPType+nameType);
		chassiswarnsetMapper.add(map);
	}

	public List<sensorrecord> getIPname(QueryParam param) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("queryType", param.getQueryType());
		map.put("IPType", param.getIPType());
		map.put("nameType", param.getNameType());
		return chassiswarnsetMapper.getIPname(map);
	}

	public void del(QueryParam param) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("queryType", param.getQueryType());
		map.put("IPType", param.getIPType());
		map.put("nameType", param.getNameType());
		chassiswarnsetMapper.del(map);
	}

	public void update(sensorrecord sensorrecord) {
		chassiswarnsetMapper.update(sensorrecord);
	}

}
