package com.gydz.user.controller;

import java.text.SimpleDateFormat;
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
import com.gydz.user.model.ipmiwarnmodel;
import com.gydz.user.model.sel_type;
import com.gydz.user.model.selrecord;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.ipmiwarnImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.user.service.sensorwarnsetImpl;
import com.gydz.util.ExcelUtil;
import com.gydz.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sensorwarnrefer")
public class SensorWarnReferController {

	@Resource
	private sensorwarnsetImpl sensorwarnsetImpl;
	
	@Resource
	private ipmiwarnImpl ipmiwarnImpl;

	@Resource
	private sensorImpl sensorImpl;
	
	@Resource
	private englishImpl englishImpl;
	
	static String IP = "";

	public List<String> getIPname(){
		List<sensorrecord> sensorwarnsetlist = null;
		QueryParam param = new QueryParam();
		sensorwarnsetlist = sensorwarnsetImpl.getsensorwarnsetbyparam(param);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < sensorwarnsetlist.size(); i++) {
			list.add(sensorwarnsetlist.get(i).getIPname());
		}
		return list;
	}
	
	@RequestMapping("/sensorwarnrefer")
	@MethodLog(remark = "进入传感器监控列表页面",openType = "3")
	public String sensorwarnrefer(HttpServletRequest request) {
		HashMap<String, String> map = englishImpl.getmap();
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		List<sensorrecord> sensorwarnreferlist = new ArrayList<sensorrecord>();
		if(!IP.equals("")) {
			sensorwarnreferlist = null;
			QueryParam param = new QueryParam();
			param.setIPType(IP);
			sensorwarnreferlist = ipmiwarnImpl.getsensorwarnreferbyparam(param);
			List<String> IPnames = 	getIPname();
			for (int i = 0; i < sensorwarnreferlist.size(); i++) {
			}
			request.setAttribute("IPnames", IPnames);
			request.setAttribute("sensorwarnreferlist", sensorwarnreferlist);
		}
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("sensorwarnreferlist", sensorwarnreferlist);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("map", map);
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "sensorwarnrefer");
		return "/WEB-INF/ipmiwarn/sensorwarnrefer";
	}
	
	
	public List<sensorrecord> getValue(String IPType,String queryType,String nameType){
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
				alist = ipmiwarnImpl.getsensorwarnreferbyparam(param);
				for (int j = 0; j < alist.size(); j++) {
					list.add(alist.get(j));
				}
			}
			
		}else {
			param = new QueryParam();
			param.setQueryType(queryType);
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = ipmiwarnImpl.getsensorwarnreferbyparam(param);
			for (int i = 0; i < alist.size(); i++) {
				list.add(alist.get(i));
			}
		}
		return list;
	}
	
	@RequestMapping("/changename")
	@ResponseBody
	public void changename(HttpServletRequest request,HttpServletResponse response) throws Exception{
	HashMap<String, String> map = englishImpl.getmap();
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String queryType = request.getParameter("queryType");
	String IPType = request.getParameter("IPType");
	List<sensorrecord> sensorwarnreferlist = getValue(IPType,queryType,null);
	Set<String> set = new LinkedHashSet<String>();
	for (int i = 0; i < sensorwarnreferlist.size(); i++) {
		set.add(sensorwarnreferlist.get(i).getName());
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
	
	@RequestMapping("/changetype")
	@ResponseBody
	public void changetype(HttpServletRequest request,HttpServletResponse response) throws Exception{
	HashMap<String, String> map = englishImpl.getmap();
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String IPType = request.getParameter("IPType");
	List<sensorrecord> sensorwarnreferlist = getValue(IPType,null,null);
	Set<String> set = new LinkedHashSet<String>();
	for (int i = 0; i < sensorwarnreferlist.size(); i++) {
		set.add(sensorwarnreferlist.get(i).getSensor_type());
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
	@MethodLog(remark = "查询传感器监控列表",openType = "3")
	public String query(HttpServletRequest request,String queryType,String nameType,String IPType) {
		HashMap<String, String> map = englishImpl.getmap();
		List<sensorrecord> sensorwarnreferlist = getValue(IPType,queryType,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		List<String> IPnames = 	getIPname();
		request.setAttribute("map", map);
		request.setAttribute("IPnames", IPnames);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("sensorwarnreferlist", sensorwarnreferlist);
		request.setAttribute("menu", "sensorwarnrefer");
		return "/WEB-INF/ipmiwarn/sensorwarnreferDiv";
	}
	
	/**
     * 导出传感器告警列表
     */
	@RequestMapping("/exprotList")
    @MethodLog(remark = "导出传感器监控列表",openType = "3")
	public void exportExcel(HttpServletRequest request,
    		String datemin, String datemax, String queryType,String IPType,String nameType,
    		HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap();
    	//int width = 256*14;
		String[] excelHeader = { "时间", "服务器用途", "服务器ip地址", "告警信息类别", "告警部件名字", "加入告警", "读值",
				"正常最大值", "正常最小值", "正常值", "最大警告阈值", "最小警告阈值", "最大严重阈值",
				"最小严重阈值", "最大故障阈值", "最小故障阈值", "传感器最大值", "传感器最小值"};
		String[] ds_titles = { "start_time", "sysname", "IP", "sensor_type",
				"name","insert","current_num","normal_maximum","normal_minimum",
				"nominal_reading","upper_non_critical_threshold",
				"lower_non_critical_threshold","upper_critical_threshold",
				"lower_critical_threshold","upper_non_recoverable_threshold",
				"lower_non_recoverable_threshold","sensor_maximum_reading",
				"sensor_minmum_reading"};
		int[] widths = {256*25, 256*25, 256*25,256*25,256*25, 256*25, 156*25, 156*25,
				156*25,156*25, 156*25, 156*25, 156*25, 156*25,156*25, 156*25,
				156*25, 156*25};
		int[] ds_format = { 1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};
		try{
			List<sensorrecord> list = getValue(IPType,queryType,nameType);
			ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
			for (int i = 0; i < list.size(); i++) {
				info.add(list.get(i));
			}
			List<String> IPnames = 	getIPname();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,String> sysnamemap = sensorImpl.getSysname();
			if(info != null && info.size() > 0){
				for(sensorrecord rdata : info){
					Map<String,Object> map = new HashMap<String,Object>();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("start_time", format.format(rdata.getStart_time()));
					map.put("sysname", sysnamemap.get(rdata.getIP()));
					map.put("IP", rdata.getIP());
					map.put("sensor_type", stamap.get(rdata.getSensor_type()));
					if(IPnames.contains(rdata.getIP()+rdata.getName())) {
						map.put("insert", "已添加");
					}else {
						map.put("insert", "未添加");
					}
					map.put("name", stamap.get(rdata.getName()));
					map.put("current_num", rdata.getCurrent_num());
					map.put("normal_maximum", rdata.getNormal_maximum());
					map.put("normal_minimum", rdata.getNormal_minimum());
					map.put("nominal_reading", rdata.getNominal_reading());
					map.put("upper_non_critical_threshold", rdata.getUpper_non_critical_threshold());
					map.put("lower_non_critical_threshold", rdata.getLower_non_critical_threshold());
					map.put("upper_critical_threshold", rdata.getUpper_critical_threshold());
					map.put("lower_critical_threshold", rdata.getLower_critical_threshold());
					map.put("upper_non_recoverable_threshold", rdata.getUpper_non_recoverable_threshold());
					map.put("lower_non_recoverable_threshold", rdata.getLower_non_recoverable_threshold());
					map.put("sensor_maximum_reading", rdata.getSensor_maximum_reading());
					map.put("sensor_minmum_reading", rdata.getSensor_minmum_reading());
					data.add(map);
				}
			}
			ExcelUtil.export("传感器监控列表", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//增加服务器
	@RequestMapping("/add")
	@MethodLog(remark = "将传感器监控列表的一条数据加入到实时告警",openType = "0")
    public String add(HttpServletRequest request){
		String IPType = request.getParameterValues("IP")[0];
    	String nameType = request.getParameterValues("name")[0];
    	
		List<sensorrecord> addlist = new ArrayList<sensorrecord>();
		List<sensorrecord> alist = null;
		String brand = "";
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
				IP = ipList.get(i);
				QueryParam param = new QueryParam();
				param.setIPType(ipList.get(i));
				param.setNameType(nameType);
				alist = ipmiwarnImpl.getsensorwarnreferbyparam(param);
				for (int j = 0; j < alist.size(); j++) {
					addlist.add(alist.get(j));
				}
			}
			
		}else {
			QueryParam param = new QueryParam();
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = ipmiwarnImpl.getsensorwarnreferbyparam(param);
			for (int i = 0; i < alist.size(); i++) {
				addlist.add(alist.get(i));
			}
			IP = IPType;
		}
		
		addlist.get(0).setIPname(IPType+nameType);
		sensorwarnsetImpl.add(addlist.get(0));
		return "redirect:/sensorwarnrefer/sensorwarnrefer";
    }
}	
