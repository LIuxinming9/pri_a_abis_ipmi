package com.gydz.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.ipmiwarnImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.user.service.sensorwarnsetImpl;
import com.gydz.user.service.warnhistoryImpl;
import com.gydz.util.ExcelUtil;
import com.gydz.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/warnhistory")
public class WarnHistoryController {

	@Resource
	private sensorwarnsetImpl sensorwarnsetImpl;
	
	@Resource
	private ipmiwarnImpl ipmiwarnImpl;

	@Resource
	private sensorImpl sensorImpl;
	
	@Resource
	private warnhistoryImpl warnhistoryImpl;
	
	@Resource
	private englishImpl englishImpl;
	
	static String IP = "";
	// 告警信息页面
	@RequestMapping("/warnhistory")
	@MethodLog(remark = "进入历史告警页面",openType = "3")
	public String remotesysinfo(HttpServletRequest request) {
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "warnhistory");
		return "/WEB-INF/ipmiwarn/warnhistory";
	}
	
	@RequestMapping("/changeInfo")
	@ResponseBody
	public void changeInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String infoType = request.getParameter("infoType");
	List<serversip> serversip = null;
	serversip = sensorImpl.getServersip();
	List<String> list = new ArrayList<String>();
	List<String> valueIdList = new ArrayList<String>();
	if(infoType.equals("name")) {
		for (int i = 0; i < serversip.size(); i++) {
			list.add(serversip.get(i).getName());
			valueIdList.add(serversip.get(i).getHostname());
		}
	}else if(infoType.equals("brand")){
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < serversip.size(); i++) {
			set.add(serversip.get(i).getBrand());
		}
		Iterator<String> it = set.iterator();
		while(it.hasNext()) {
			String next = it.next();
			list.add(next);
			valueIdList.add("brand="+next);
		}
	}else if(infoType.equals("hostname")) {
		for (int i = 0; i < serversip.size(); i++) {
			list.add(serversip.get(i).getHostname());
			valueIdList.add(serversip.get(i).getHostname());
		}
	}
	for(int i=0;i<list.size();i++)
	{
		jsonObject.put("id", i);
		jsonObject.put("value", list.get(i));
		jsonObject.put("valueId", valueIdList.get(i));
		jsonArray.add(jsonObject);
		
	}
	ResponseUtil.write(response, jsonArray);
	}
	
	@RequestMapping("/changename")
	@ResponseBody
	public void changename(HttpServletRequest request,HttpServletResponse response) throws Exception{
	HashMap<String, String> map = englishImpl.getmap();
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String queryType = request.getParameter("queryType");
	String IPType = request.getParameter("IPType");
	List<sensorrecord> sensorwarnreferlist = getValue(IPType,null,null,queryType,null);
	Set<String> set = new LinkedHashSet<String>();
	for (int i = 0; i < sensorwarnreferlist.size(); i++) {
		if(queryType.equals("全部")) {
			set.add(sensorwarnreferlist.get(i).getName());
		}else if(sensorwarnreferlist.get(i).getSensor_type().equals(queryType)) {
			set.add(sensorwarnreferlist.get(i).getName());
		}
	}
	List<String> list = new ArrayList<String>();
	Iterator<String> it = set.iterator();
	while(it.hasNext()) {
		list.add(it.next());
	}
	for(int i=0;i<list.size();i++)
	{
		jsonObject.put("id", i);
		jsonObject.put("name", list.get(i));
		jsonObject.put("mapname", map.get(list.get(i)));
		jsonArray.add(jsonObject);
		
	}
	ResponseUtil.write(response, jsonArray);
	}
	
	
	public List<sensorrecord> getValue(String IPType,String datemin, String datemax,String queryType,String nameType){
		List<sensorrecord> list = new ArrayList<sensorrecord>();
    	List<sensorrecord> alist = null;
		String brand = "";
		QueryParam param = new QueryParam();
		if(IPType.contains("=")) {
			brand = IPType.substring(IPType.indexOf("=")+1);
			List<serversip> serversip = null;
			serversip = sensorImpl.getServersip();
			List<String> ipList = new ArrayList<String>();
			for (int i = 0; i < serversip.size(); i++) {
				if(serversip.get(i).getBrand().equals(brand)) {
					ipList.add(serversip.get(i).getHostname());
				}
			}
			for (int i = 0; i < ipList.size(); i++) {
				param = new QueryParam();
				param.setQueryType(queryType);
				param.setIPType(ipList.get(i));
				param.setNameType(nameType);
				param.setDatemin(datemin);
				param.setDatemax(datemax);
				alist = warnhistoryImpl.getlistbyparam(param);
				for (int j = 0; j < alist.size(); j++) {
					if(alist.get(i).getWarn_state()==1) {
						list.add(alist.get(j));
					}
				}
			}
			
		}else {
			param = new QueryParam();
			param.setQueryType(queryType);
			param.setIPType(IPType);
			param.setNameType(nameType);
			param.setDatemin(datemin);
			param.setDatemax(datemax);
			alist = warnhistoryImpl.getlistbyparam(param);
			for (int i = 0; i < alist.size(); i++) {
				if(alist.get(i).getWarn_state()==1) {
					list.add(alist.get(i));
				}
			}
		}
		return list;
	}
	
	@RequestMapping("/changetype")
	@ResponseBody
	public void changetype(HttpServletRequest request,HttpServletResponse response) throws Exception{
	HashMap<String, String> map = englishImpl.getmap();
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String IPType = request.getParameter("IPType");
	List<sensorrecord> sensorwarnreferlist = getValue(IPType,null,null,null,null);
	Set<String> set = new LinkedHashSet<String>();
	for (int j = 0; j < sensorwarnreferlist.size(); j++) {
		set.add(sensorwarnreferlist.get(j).getSensor_type());
	}
	List<String> list = new ArrayList<String>();
	Iterator<String> it = set.iterator();
	while(it.hasNext()) {
		list.add(it.next());
	}
	for(int i=0;i<list.size();i++)
	{
		jsonObject.put("id", i);
		jsonObject.put("type", list.get(i));
		jsonObject.put("maptype", map.get(list.get(i)));
		jsonArray.add(jsonObject);
		
	}
	ResponseUtil.write(response, jsonArray);
	}
	
	@RequestMapping("/query")
	@MethodLog(remark = "查询历史告警",openType = "3")
	public String query(HttpServletRequest request,String datemin, String datemax,String queryType,String nameType,String IPType) {
		HashMap<String, String> map = englishImpl.getmap();
		List<sensorrecord> list = getValue(IPType,datemin,datemax,queryType,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("list", list);
		request.setAttribute("menu", "warnhistory");
		return "/WEB-INF/ipmiwarn/warnhistoryDiv";
	}
	
	@RequestMapping("/exprotList")
    @MethodLog(remark = "导出历史告警信息",openType = "3")
	public void exportExcel(HttpServletRequest request,
    		String datemin, String datemax, String queryType,String IPType,String nameType,
    		HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap();
    	//int width = 256*14;
		String[] excelHeader = {"序号","时间","服务器用途", "服务器ip地址", "告警信息类别", "告警部件名字","读值", "告警级别"};
		String[] ds_titles = {"index","start_time","sysname", "IP", "sensor_type","name","current_num","warnlevel"};
		int[] widths = {256*25, 256*25,256*25, 256*25, 156*25, 156*25,156*25,156*25};
		int[] ds_format = {2,2,2,2,2,2,2,2};
		try{
			List<sensorrecord> list = getValue(IPType,datemin,datemax,queryType,nameType);
			ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
			for (int i = 0; i < list.size(); i++) {
				info.add(list.get(i));
			}
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,String> sysnamemap = sensorImpl.getSysname();
			if(info != null && info.size() > 0){
				for (int j = 0; j < info.size(); j++) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("index", j+1);
					map.put("start_time", info.get(j).getStart_time());
					map.put("sysname", sysnamemap.get(info.get(j).getIP()));
					map.put("IP", info.get(j).getIP());
					map.put("sensor_type", stamap.get(info.get(j).getSensor_type()));
					map.put("name", stamap.get(info.get(j).getName()));
					map.put("current_num", info.get(j).getCurrent_num());
					map.put("warnlevel", info.get(j).getWarnlevel());
					data.add(map);
				}
			}
			ExcelUtil.export("历史告警", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}	
