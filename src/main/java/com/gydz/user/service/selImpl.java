package com.gydz.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.schemaMapper;
import com.gydz.user.mapper.selMapper;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sel_name;
import com.gydz.user.model.sel_type;
import com.gydz.user.model.selrecord;
import com.gydz.user.model.sensorrecord;
import com.gydz.util.stritodate;

@Service("selService")
public class selImpl implements selService{

	
	@Resource
	private selMapper selMapper;
	
	@Resource
	private schemaMapper schemaMapper;
	
	public List<sel_type> getsel_type() {
		List<sel_type> selTypesList = new ArrayList<sel_type>();
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			if(tablename.contains("sel_type")) {
				List<sel_type> selTypList = new ArrayList<sel_type>();
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("tablename", tablename);
				selTypList = selMapper.getsel_type(map);
				for (int j = 0; j < selTypList.size(); j++) {
					selTypesList.add(selTypList.get(j));
				}
			}
		}
		return selTypesList;
	}

	public List<sel_name> getsel_name() {
		List<sel_name> selNamesList = new ArrayList<sel_name>();
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			if(tablename.contains("sel_name")) {
				List<sel_name> selNameList = new ArrayList<sel_name>();
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("tablename", tablename);
				selNameList = selMapper.getsel_name(map);
				for (int j = 0; j < selNameList.size(); j++) {
					selNamesList.add(selNameList.get(j));
				}
			}
		}
		return selNamesList;
	}

	public List<selrecord> getInfobyParam(QueryParam param) {
		List<selrecord> selRecordesList = new ArrayList<selrecord>();
		List<String> list = new ArrayList<String>();
		list = schemaMapper.getTables();
		for (int i = 0; i < list.size(); i++) {
			String tablename = "`" + list.get(i) + "`";
			String ip = param.getIPType();
			if(ip==null || ip.equals("全部")) {
				if(tablename.contains("sel_record")) {
					List<selrecord> selRecordList = new ArrayList<selrecord>();
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("tablename", tablename);
					map.put("queryType", param.getQueryType());
					map.put("nameType", param.getNameType());
					map.put("datemax", param.getDatemax());
					map.put("datemin", param.getDatemin());
					selRecordList = selMapper.getInfobyParam(map);
					for (int j = 0; j < selRecordList.size(); j++) {
						selRecordesList.add(selRecordList.get(j));
					}
				}
			}else {
				if(tablename.contains("sel_record") && tablename.contains(ip)) {
					List<selrecord> selRecordList = new ArrayList<selrecord>();
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("tablename", tablename);
					map.put("queryType", param.getQueryType());
					map.put("nameType", param.getNameType());
					map.put("datemax", param.getDatemax());
					map.put("datemin", param.getDatemin());
					selRecordList = selMapper.getInfobyParam(map);
					for (int j = 0; j < selRecordList.size(); j++) {
						selRecordesList.add(selRecordList.get(j));
					}
				}
			}
		}
		return selRecordesList;
	}

	public List<String> selectANum() {
		return selMapper.selectANum();
	}

	public void addANum(String name) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		selMapper.addANum(map);
	}

	public void delANum(String hostname) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("name", hostname);
		selMapper.delANum(map);
	}
}
