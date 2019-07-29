package com.gydz.user.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.resourcewarnsetMapper;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;

@Service("resourcewarnsetService")
public class resourcewarnsetImpl implements resourcewarnsetService{

	@Resource
	resourcewarnsetMapper resourcewarnsetMapper;
	
	public void add(sensorrecord sensorrecord) {
		resourcewarnsetMapper.add(sensorrecord);
	}

	public List<sensorrecord> getresourcewarnsetbyparam(QueryParam param) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("queryType", param.getQueryType());
		map.put("IPType", param.getIPType());
		map.put("nameType", param.getNameType());
		map.put("entity_id", param.getEntity_id());
		return resourcewarnsetMapper.getresourcewarnsetbyparam(map);
	}
	
	public void delSysIP(QueryParam param) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("IPType", param.getIPType());
		map.put("entity_id", param.getEntity_id());
		resourcewarnsetMapper.delSysIP(map);
	}

	public void update(sensorrecord sensorrecord) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("IP", sensorrecord.getIP());
		map.put("entity_id", sensorrecord.getEntity_id());
		map.put("IPname", sensorrecord.getIPname());
		resourcewarnsetMapper.update(map);
	}

	public void refresh(String nameip, String emptyname) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("nameip",nameip);
		map.put("emptyname", emptyname);
		resourcewarnsetMapper.refresh(map);
	}
}
