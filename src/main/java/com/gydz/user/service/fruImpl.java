package com.gydz.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.fruMapper;
import com.gydz.user.mapper.schemaMapper;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.chassisStatus;
import com.gydz.user.model.fruInfo2;

@Service("fruService")
public class fruImpl implements fruService{

	@Resource
	private fruMapper fruMapper;

	@Resource
	private schemaMapper schemaMapper;
	
	public List<fruInfo2> getInfobyParam(QueryParam param) {
		List<fruInfo2> fruesList = new ArrayList<fruInfo2>();
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			String ip = param.getIPType();
			if(ip==null || ip.equals("全部")) {
				if(tablename.contains("fru_info_c")) {
					List<fruInfo2> fruList = new ArrayList<fruInfo2>();
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("tablename", tablename);
					fruList = fruMapper.getInfobyParam(map);
					for (int j = 0; j < fruList.size(); j++) {
						fruesList.add(fruList.get(j));
					}
				}
			}else {
				if(tablename.contains("fru_info_c") && tablename.contains(ip)) {
					List<fruInfo2> fruList = new ArrayList<fruInfo2>();
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("tablename", tablename);
					fruList = fruMapper.getInfobyParam(map);
					for (int j = 0; j < fruList.size(); j++) {
						fruesList.add(fruList.get(j));
					}
				}
			}
		}
		return fruesList;
	}
}
