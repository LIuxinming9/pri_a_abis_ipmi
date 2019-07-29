package com.gydz.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.chassisMapper;
import com.gydz.user.mapper.schemaMapper;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.chassisStatus;
import com.gydz.user.model.sensorrecord;
import com.gydz.util.stritodate;

@Service("chassisService")
public class chassisImpl implements chassisService{

	@Resource
	private chassisMapper chassisMapper;
	
	@Resource
	private schemaMapper schemaMapper;
	
	public List<chassisStatus> getChassisInfobyParam(QueryParam param) {
		List<chassisStatus> chassisStatuseslist = new ArrayList<chassisStatus>();
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			String ip = param.getIPType();
			if(ip==null || ip.equals("全部")) {
				if(tablename.contains("chassis_status_2")) {
					List<chassisStatus> chassisStatuslist = new ArrayList<chassisStatus>();
					chassisStatuslist = selectChassisInfobyParam(param,tablename);
					for (int j = 0; j < chassisStatuslist.size(); j++) {
						chassisStatuseslist.add(chassisStatuslist.get(j));
					}
				}
			}else {
				if(tablename.contains("chassis_status_2") && tablename.contains(ip)) {
					List<chassisStatus> chassisStatuslist = new ArrayList<chassisStatus>();
					chassisStatuslist = selectChassisInfobyParam(param,tablename);
					for (int j = 0; j < chassisStatuslist.size(); j++) {
						chassisStatuseslist.add(chassisStatuslist.get(j));
					}
				}
			}
		}
		return chassisStatuseslist;
	}

	public List<chassisStatus> selectChassisInfobyParam(QueryParam param,String tablename) {
		List<chassisStatus> chassisStatuslist = new ArrayList<chassisStatus>();
		long tabletime = Long.parseLong(tablename.substring(tablename.indexOf("192")-9, tablename.indexOf("192")-1));
		long datemin = Long.parseLong(stritodate.strtodatetostr(param.getDatemin()));
		long datemax = Long.parseLong(stritodate.strtodatetostr(param.getDatemax()));
		if(tabletime>=datemin && tabletime<=datemax) {
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("tablename",tablename);
			List<chassisStatus> chassisStatus = new ArrayList<chassisStatus>();
			chassisStatus = chassisMapper.getChassisInfobyParam(map);
			for (int j = 0; j < chassisStatus.size(); j++) {
				chassisStatuslist.add(chassisStatus.get(j));
			}
		}
		return chassisStatuslist;
	}
	
	public List<chassisStatus> getChassiscurrentInfobyParam(QueryParam param) {
		List<chassisStatus> chassisesList = new ArrayList<chassisStatus>();
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			String ip = param.getIPType();
			if(ip==null || ip.equals("全部")) {
				if(tablename.contains("chassis_status_c")) {
					List<chassisStatus> chassisList = new ArrayList<chassisStatus>();
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("tablename", tablename);
					chassisList = chassisMapper.getChassiscurrentInfobyParam(map);
					for (int j = 0; j < chassisList.size(); j++) {
						chassisesList.add(chassisList.get(j));
					}
				}
			}else {
				if(tablename.contains("chassis_status_c") && tablename.contains(ip)) {
					List<chassisStatus> chassisList = new ArrayList<chassisStatus>();
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("tablename", tablename);
					chassisList = chassisMapper.getChassiscurrentInfobyParam(map);
					for (int j = 0; j < chassisList.size(); j++) {
						chassisesList.add(chassisList.get(j));
					}
				}
			}
		}
		return chassisesList;
	}
}
