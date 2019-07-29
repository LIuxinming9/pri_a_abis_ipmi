package com.gydz.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.ipmiwarnMapper;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.ipmiwarnmodel;
import com.gydz.user.model.sensorrecord;

@Service("ipmiwarnService")
public class ipmiwarnImpl implements ipmiwarnService {

	@Resource
	private ipmiwarnMapper ipmiwarnMapper;

	public List<sensorrecord> getsensorwarnrefer() {
		return ipmiwarnMapper.getsensorwarnrefer();
	}

	public List<String> getType() {
		List<sensorrecord> list = getsensorwarnrefer();
		LinkedHashSet<String> set = new LinkedHashSet<String>();
		List<String> typeList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			set.add(list.get(i).getSensor_type());
		}
		Iterator<String> it = set.iterator();
		while(it.hasNext()) {
			typeList.add(it.next());
		}
		return typeList;
	}

	public List<sensorrecord> getsensorwarnreferbyid(String IPType) {
		return ipmiwarnMapper.getsensorwarnreferbyid(IPType);
	}

	public List<sensorrecord> getsensorwarnreferbyparam(QueryParam param) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("queryType", param.getQueryType());
		map.put("IPType", param.getIPType());
		map.put("nameType", param.getNameType());
		return ipmiwarnMapper.getsensorwarnreferbyparam(map);
	}

}
