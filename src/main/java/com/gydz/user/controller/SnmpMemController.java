package com.gydz.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.gydz.user.model.snmp;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.ipmiwarnImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.user.service.sensorwarnsetImpl;
import com.gydz.user.service.snmpImpl;
import com.gydz.user.service.warnhistoryImpl;
import com.gydz.util.ExcelUtil;
import com.gydz.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/snmpMem")
public class SnmpMemController {

	@Resource
	private sensorwarnsetImpl sensorwarnsetImpl;
	
	@Resource
	private ipmiwarnImpl ipmiwarnImpl;

	@Resource
	private sensorImpl sensorImpl;
	
	@Resource
	private snmpImpl snmpImpl;
	
	@Resource
	private englishImpl englishImpl;
	
	static String IP = "";
	// 告警信息页面
	@RequestMapping("/snmpMem")
	public String remotesysinfo(HttpServletRequest request) {
		List<serversip> serversIpList = null;
		List<serversip> serversip = new ArrayList<serversip>();
		serversIpList = sensorImpl.getServersip();
		for (int i = 0; i < serversIpList.size(); i++) {
			if(serversIpList.get(i).getSnmp_online()==0) {
				serversip.add(serversIpList.get(i));
			}
		}
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "snmpMem");
		return "/WEB-INF/snmp/snmpMem";
	}
	
	@RequestMapping("/changename")
	@ResponseBody
	public void changename(HttpServletRequest request,HttpServletResponse response) throws Exception{
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String IPType = request.getParameter("IPType");
	List<snmp> nameList = null;
	nameList = getName(IPType,null);
	Set<String> set = new LinkedHashSet<String>();
	for (int i = 0; i < nameList.size(); i++) {
		set.add(nameList.get(i).getName());
	}
	List<String> list = new ArrayList<String>();
	Iterator<String> it = set.iterator();
	while(it.hasNext()) {
		String name = it.next();
		if(name.contains("内存")) {
			list.add(name);
		}
	}
	for(int i=0;i<list.size();i++)
	{
		jsonObject.put("id", i);
		jsonObject.put("name", list.get(i));
		jsonArray.add(jsonObject);
		
	}
	ResponseUtil.write(response, jsonArray);
	}
	
	public List<snmp> getName(String IPType,String nameType){
		List<snmp> list = new ArrayList<snmp>();
    	List<snmp> alist = null;
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
				param.setIPType(ipList.get(i));
				param.setNameType(nameType);
				alist = snmpImpl.getNameByParam(param);
				for (int j = 0; j < alist.size(); j++) {
					list.add(alist.get(j));
				}
			}
			
		}else {
			param = new QueryParam();
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = snmpImpl.getNameByParam(param);
			for (int i = 0; i < alist.size(); i++) {
				list.add(alist.get(i));
			}
		}
		return list;
	}
	
	public List<snmp> getValue(String IPType,String nameType){
		List<snmp> list = new ArrayList<snmp>();
    	List<snmp> alist = null;
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
				param.setIPType(ipList.get(i));
				param.setNameType(nameType);
				alist = snmpImpl.getValueByParam(param);
				for (int j = 0; j < alist.size(); j++) {
					list.add(alist.get(j));
				}
			}
			
		}else {
			param = new QueryParam();
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = snmpImpl.getValueByParam(param);
			for (int i = 0; i < alist.size(); i++) {
				list.add(alist.get(i));
			}
		}
		return list;
	}
	
	@RequestMapping("/changetype")
	@ResponseBody
	public void changetype(HttpServletRequest request,HttpServletResponse response) throws Exception{
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String IPType = request.getParameter("IPType");
	QueryParam param = new QueryParam();
	param.setIPType(IPType);
	List<snmp> typeList = null;
	typeList = snmpImpl.getTypeByParam(param);
	Set<String> set = new LinkedHashSet<String>();
	for (int i = 0; i < typeList.size(); i++) {
		set.add(typeList.get(i).getType());
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
		jsonArray.add(jsonObject);
		
	}
	ResponseUtil.write(response, jsonArray);
	}
	
	@RequestMapping("/query")
	public String query(HttpServletRequest request,String queryType,String nameType,String IPType) {
		List<snmp> list = null;
		list = getValue(IPType,nameType);
		List<snmp> alist = new ArrayList<snmp>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getName().contains("内存")) {
				alist.add(list.get(i));
			}
		}
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("alist", alist);
		request.setAttribute("menu", "snmpMem");
		return "/WEB-INF/snmp/snmpMemDiv";
	}
	
	/**
     * 导出传感器参考数据
     */
	@RequestMapping("/exprotList")
    @MethodLog(remark = "导出SNMP数据",openType = "3")
	public void exportExcel(HttpServletRequest request,
    		String datemin, String datemax, String IPType,String nameType,
    		HttpServletResponse response){
    	//int width = 256*14;
		String[] excelHeader = {"序号","时间","服务器名字", "服务器ip地址", "OID","OID描述", "读值"};
		String[] ds_titles = {"index","start_time","sysname", "hostname", "oid","name","value"};
		int[] widths = {100*25, 256*25,156*25, 256*25, 256*25,256*25,356*25};
		int[] ds_format = {2,2,2,2,2,2,2};
		try{
	    	List<snmp> list = null;
			list = getValue(IPType,nameType);
			ArrayList<snmp> info = new ArrayList<snmp>();
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getName().contains("内存")) {
					info.add(list.get(i));
				}
			}
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,String> sysnamemap = sensorImpl.getSysname();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(info != null && info.size() > 0){
				for (int j = 0; j < info.size(); j++) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("index", j+1);
					map.put("start_time", format.format(info.get(j).getStart_time()));
					map.put("sysname", sysnamemap.get(info.get(j).getHostname()));
					map.put("hostname", info.get(j).getHostname());
					map.put("oid", info.get(j).getOid());
					map.put("name", info.get(j).getName());
					map.put("value", info.get(j).getValue());
					data.add(map);
				}
			}
			ExcelUtil.export("SNMP数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}	
