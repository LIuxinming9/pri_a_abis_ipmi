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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.user.service.sensorwarnsetImpl;
import com.gydz.util.ExcelUtil;
import com.gydz.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sensorwarnset")
public class SensorWarnSetController {

	@Resource
	private sensorwarnsetImpl sensorwarnsetImpl;

	@Resource
	private sensorImpl sensorImpl;
	
	static String IP = "";
	
	@Resource
	private englishImpl englishImpl;
	
	@RequestMapping("/sensorwarnset")
	@MethodLog(remark = "进入传感器告警列表页面",openType = "3")
	public String sensorwarnrefer(HttpServletRequest request) {
		HashMap<String, String> map = englishImpl.getmap();
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		if(!IP.equals("")) {
			List<sensorrecord> sensorwarnsetlist = null;
			QueryParam param = new QueryParam();
			param.setIPType(IP);
			sensorwarnsetlist = sensorwarnsetImpl.getsensorwarnsetbyparam(param);
			request.setAttribute("sensorwarnsetlist", sensorwarnsetlist);
		}
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "sensorwarnset");
		return "/WEB-INF/ipmiwarn/sensorwarnset";
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
				alist = sensorwarnsetImpl.getsensorwarnsetbyparam(param);
				for (int j = 0; j < alist.size(); j++) {
					list.add(alist.get(j));
				}
			}
			
		}else {
			param = new QueryParam();
			param.setQueryType(queryType);
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = sensorwarnsetImpl.getsensorwarnsetbyparam(param);
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
	@MethodLog(remark = "查询传感器告警列表",openType = "3")
	public String query(HttpServletRequest request,String queryType,String nameType,String IPType) {
		HashMap<String, String> map = englishImpl.getmap();
		List<sensorrecord> sensorwarnsetlist = getValue(IPType,queryType,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("sensorwarnsetlist", sensorwarnsetlist);
		request.setAttribute("menu", "sensorwarnset");
		return "/WEB-INF/ipmiwarn/sensorwarnsetDiv";
	}
	
	@RequestMapping("/exprotList")
    @MethodLog(remark = "导出传感器告警列表",openType = "3")
	public void exportExcel(HttpServletRequest request,
    		String datemin, String datemax, String queryType,String IPType,String nameType,
    		HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap();
    	//int width = 256*14;
		String[] excelHeader = {"服务器用途", "服务器ip地址", "告警信息类别", "告警部件名字",
				"最大警告阈值", "最小警告阈值", "最大严重阈值","最小严重阈值", "最大故障阈值", "最小故障阈值"};
		String[] ds_titles = {"sysname", "IP", "sensor_type",
				"name","upper_non_critical_threshold",
				"lower_non_critical_threshold","upper_critical_threshold",
				"lower_critical_threshold","upper_non_recoverable_threshold",
				"lower_non_recoverable_threshold"};
		int[] widths = {256*25, 256*25,256*25, 256*25, 156*25, 156*25,
				156*25,156*25, 156*25, 156*25};
		int[] ds_format = {2,2,2,2,2,2,2,2,2,2};
		try{
			List<sensorrecord> list = getValue(IPType,queryType,nameType);
			ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
			for (int i = 0; i < list.size(); i++) {
				info.add(list.get(i));
			}
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,String> sysnamemap = sensorImpl.getSysname();
			if(info != null && info.size() > 0){
				for(sensorrecord rdata : info){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("sysname", sysnamemap.get(rdata.getIP()));
					map.put("IP", rdata.getIP());
					map.put("sensor_type", stamap.get(rdata.getSensor_type()));
					map.put("name", stamap.get(rdata.getName()));
					map.put("upper_non_critical_threshold", rdata.getUpper_non_critical_threshold());
					map.put("lower_non_critical_threshold", rdata.getLower_non_critical_threshold());
					map.put("upper_critical_threshold", rdata.getUpper_critical_threshold());
					map.put("lower_critical_threshold", rdata.getLower_critical_threshold());
					map.put("upper_non_recoverable_threshold", rdata.getUpper_non_recoverable_threshold());
					map.put("lower_non_recoverable_threshold", rdata.getLower_non_recoverable_threshold());
					data.add(map);
				}
			}
			ExcelUtil.export("传感器告警列表", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
    @RequestMapping(value = "/delSysIP")
    @MethodLog(remark = "删除一条传感器告警信息",openType = "1")
    public String delSysIP(HttpServletRequest request){
        try {
        	String IPType = request.getParameterValues("IP")[0];
        	String name = request.getParameterValues("name")[0];
        	IP = IPType;
        	QueryParam param = new QueryParam();
    		param.setIPType(IPType);
    		param.setNameType(name);
        	sensorwarnsetImpl.delSysIP(param);
        }catch (Exception ex){
        	System.out.println("____________删除失败——————————————————————");
        }
        request.setAttribute("menu", "sensorwarnset");
        return "redirect:/sensorwarnset/sensorwarnset";
    }
    
    // 保存服务器信息页面
 	@RequestMapping(value = "/save", method = RequestMethod.POST)
 	@MethodLog(remark = "修改传感器告警信息",openType = "2")
 	public String saveSysIP(HttpServletRequest request, Model model) {

 		List<sensorrecord> list = new ArrayList<sensorrecord>();
 		String[] IPs = request.getParameterValues("IP");
 		IP = IPs[0];
 		String[] names = request.getParameterValues("name");
 		String[] sensor_types = request.getParameterValues("sensor_type");
 		String[] upper_non_critical_thresholds = request.getParameterValues("upper_non_critical_threshold");
 		String[] lower_non_critical_thresholds = request.getParameterValues("lower_non_critical_threshold");
 		String[] upper_critical_thresholds = request.getParameterValues("upper_critical_threshold");
 		String[] lower_critical_thresholds = request.getParameterValues("lower_critical_threshold");
 		String[] upper_non_recoverable_thresholds = request.getParameterValues("upper_non_recoverable_threshold");
 		String[] lower_non_recoverable_thresholds = request.getParameterValues("lower_non_recoverable_threshold");
 		
 		for (int i = 0; i < IPs.length; i++) {
 			sensorrecord sensorrecord = new sensorrecord();
 			sensorrecord.setIP(IPs[i]);
 			sensorrecord.setName(names[i]);
 			sensorrecord.setIPname(IPs[i]+names[i]);
 			sensorrecord.setSensor_type(sensor_types[i]);
 			sensorrecord.setUpper_non_critical_threshold(Double.parseDouble(upper_non_critical_thresholds[i]));
 			sensorrecord.setLower_non_critical_threshold(Double.parseDouble(lower_non_critical_thresholds[i]));
 			sensorrecord.setUpper_critical_threshold(Double.parseDouble(upper_critical_thresholds[i]));
 			sensorrecord.setLower_critical_threshold(Double.parseDouble(lower_critical_thresholds[i]));
 			sensorrecord.setUpper_non_recoverable_threshold(Double.parseDouble(upper_non_recoverable_thresholds[i]));
 			sensorrecord.setLower_non_recoverable_threshold(Double.parseDouble(lower_non_recoverable_thresholds[i]));
 			list.add(sensorrecord);
 		}
 		for (int i = 0; i < upper_non_critical_thresholds.length; i++) {
 			sensorwarnsetImpl.update(list.get(i));
 		}
 		request.setAttribute("menu", "sensorwarnset");
 		return "redirect:/sensorwarnset/sensorwarnset";
 	}
}	
