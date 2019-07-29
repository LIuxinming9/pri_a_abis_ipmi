package com.gydz.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.json.GsonUtil;
import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.name;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.util.EChartsOptionParam;
import com.gydz.util.EChartsUtil;
import com.gydz.util.ExcelUtil;
import com.gydz.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sensor")
public class SensorController {

	@Resource
	private sensorImpl sensorImpl;

	@Resource
	private englishImpl englishImpl;
	
	//传感器温度
	// 传感器温度信息页面
	@RequestMapping("/sensorteinfo")
	@MethodLog(remark = "进入温度页面",openType = "3")
	public String sensorinfo(HttpServletRequest request,HttpServletResponse response) {
		String queryType = "Temperature";
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		List<name> name = null;
		QueryParam param = new QueryParam();
		param.setQueryType(queryType);
		name = sensorImpl.getName(param);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("serversip", serversip);
		request.setAttribute("name", name);
		request.setAttribute("menu", "sensorteinfo");
		return "/WEB-INF/sensor/sensorteinfo";
	}
	
	public List<sensorrecord> getIPnamenum(){
		List<sensorrecord> list = sensorImpl.getIPnamenum();
		return list;
	}
	
	//为了处理查询条件为品牌时的情况
	public Set<String> getNameSet(String IPType,String queryType){
		String brand = "";
		Set<String> set = new LinkedHashSet<String>();
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
				QueryParam param = new QueryParam();
				param.setIPType(ipList.get(i));
				param.setQueryType(queryType);
				List<sensorrecord> sensorwarnreferlist = null;
				sensorwarnreferlist = sensorImpl.getcurrentInfobyParam(param);
				for (int j = 0; j < sensorwarnreferlist.size(); j++) {
					set.add(sensorwarnreferlist.get(j).getName());
				}
			}
			
		}else {
			QueryParam param = new QueryParam();
			param.setIPType(IPType);
			param.setQueryType(queryType);
			List<sensorrecord> sensorwarnreferlist = null;
			sensorwarnreferlist = sensorImpl.getcurrentInfobyParam(param);
			for (int i = 0; i < sensorwarnreferlist.size(); i++) {
				set.add(sensorwarnreferlist.get(i).getName());
			}
		}
		return set;
	}
	
	@RequestMapping("/changetename")
	@ResponseBody
	public void changetename(HttpServletRequest request,HttpServletResponse response) throws Exception{
	HashMap<String, String> map = englishImpl.getmap();
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String IPType = request.getParameter("IPType");
	String queryType = "Temperature";
	Set<String> set = getNameSet(IPType,queryType);
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
	
	@RequestMapping("/changefaname")
	@ResponseBody
	public void changefaname(HttpServletRequest request,HttpServletResponse response) throws Exception{
	HashMap<String, String> map = englishImpl.getmap();
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String IPType = request.getParameter("IPType");
	String queryType = "Fan";
	Set<String> set = getNameSet(IPType,queryType);
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
	
	@RequestMapping("/changevoname")
	@ResponseBody
	public void changevoname(HttpServletRequest request,HttpServletResponse response) throws Exception{
	HashMap<String, String> map = englishImpl.getmap();
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String IPType = request.getParameter("IPType");
	String queryType = "Voltage";
	Set<String> set = getNameSet(IPType,queryType);
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
	
	public List<sensorrecord> getValueList(String IPType,String datemin,String datemax,String queryType,String nameType){
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
				param.setDatemin(datemin);
				param.setDatemax(datemax);
				param.setQueryType(queryType);
				param.setIPType(ipList.get(i));
				param.setNameType(nameType);
				alist = sensorImpl.getInfobyParam(param);
				for (int j = 0; j < alist.size(); j++) {
					list.add(alist.get(j));
				}
			}
			
		}else {
			param = new QueryParam();
			param.setDatemin(datemin);
			param.setDatemax(datemax);
			param.setQueryType(queryType);
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = sensorImpl.getInfobyParam(param);
			for (int i = 0; i < alist.size(); i++) {
				list.add(alist.get(i));
			}
		}
		return list;
	}
	
	public List<sensorrecord> getCurrentValueList(String IPType,String queryType,String nameType){
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
				alist = sensorImpl.getcurrentInfobyParam(param);
				for (int j = 0; j < alist.size(); j++) {
					list.add(alist.get(j));
				}
			}
			
		}else {
			param = new QueryParam();
			param.setQueryType(queryType);
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = sensorImpl.getcurrentInfobyParam(param);
			for (int i = 0; i < alist.size(); i++) {
				list.add(alist.get(i));
			}
		}
		return list;
	}
	
	//选择后的传感器温度信息页面
	@RequestMapping("/sensortequery")
	@MethodLog(remark = "查询历史温度",openType = "3")
    public String queryListH(HttpServletRequest request,
    		String datemin, String datemax, String queryType, String keyword,String IPType,String nameType){
		HashMap<String, String> map = englishImpl.getmap();		
		List<sensorrecord> list = getValueList(IPType,datemin,datemax,queryType,nameType);
		ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getSensor_type().equals("Temperature")) {
				info.add(list.get(i));
			}
		}
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
    	request.setAttribute("info",info);
    	request.setAttribute("menu", "sensorteinfo");
        return "/WEB-INF/sensor/sensorteDiv";
    }
		
	//选择后的传感器温度折线图
	@RequestMapping("/sensortechartDiv")
	@MethodLog(remark = "查询历史温度折线图",openType = "3")
    public String sensorttechartDiv(HttpServletRequest request,
    		String datemin, String datemax, String queryType, String keyword,String IPType,String nameType){
		HashMap<String, String> map = englishImpl.getmap();		
		List<sensorrecord> list = getValueList(IPType,datemin,datemax,queryType,nameType);
		ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getSensor_type().equals("Temperature")) {
				info.add(list.get(i));
			}
		}
    	try {
	    	LinkedHashMap<String,LinkedHashMap<String, Object>> lineMap = 
	    			new LinkedHashMap<String,LinkedHashMap<String,Object>>();
	    	EChartsOptionParam lineParam = new EChartsOptionParam();
			for(sensorrecord apr : info){
				String format = "";
				SimpleDateFormat dts = new SimpleDateFormat("MM-dd HH:mm");
				format = dts.format(apr.getStart_time());
	        	LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
	            data.put(map.get(apr.getName()), apr.getCurrent_num());
	            lineMap.put(format, data);
	            lineParam = new EChartsOptionParam(map.get(apr.getName()), "", false);
		        lineParam.setyUnit(map.get(apr.getSensor_base_unit()));
	        }   
	        
	        Option lineOption = EChartsUtil.generateLineMore(lineMap, lineParam);
	        System.out.println("lineOption:"+GsonUtil.format(lineOption));
	    	request.setAttribute("networkLineOption", GsonUtil.format(lineOption));
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	Map<String,String> sysnamemap = sensorImpl.getSysname();
    	request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
    	request.setAttribute("info",info);
    	request.setAttribute("menu", "sensorteinfo");
        return "/WEB-INF/sensor/sensortechartDiv";
    }
	
	//选择后的传感器实时温度数据页面
	@RequestMapping("/sensortecurrentquery")
	@MethodLog(remark = "查询实时温度",openType = "3")
    public String sensorcurrentquery(HttpServletRequest request,
    		String queryType, String keyword,String IPType,String nameType) throws Exception{
		HashMap<String, String> map = englishImpl.getmap();		
		List<sensorrecord> list = getCurrentValueList(IPType,queryType,nameType);
		ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getSensor_type().equals("Temperature")) {
				info.add(list.get(i));
			}
		}
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
    	request.setAttribute("info",info);
    	request.setAttribute("menu", "sensorteinfo");
        return "/WEB-INF/sensor/sensortecurrentDiv";
    }
	
	/**
     * 导出传感器温度数据
     */
	@RequestMapping("/exprotteList")
    @MethodLog(remark = "导出传感器历史温度",openType = "3")
	public void exportExcel(HttpServletRequest request,
    		String datemin, String datemax, String queryType,String IPType,String nameType,
    		HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap();		
    	//int width = 256*14;
		String[] excelHeader = { "时间", "服务器用途", "服务器ip地址", "传感器类型", "传感器名字", "传感器读数"};
		String[] ds_titles = { "start_time", "sysname", "IP", "sensor_type", "name","current_num"};
		int[] widths = {256*25, 256*25, 256*25,256*25, 256*25, 256*25};
		int[] ds_format = { 1,2,2,2,2,2};
		try{
			List<sensorrecord> list = getValueList(IPType,datemin,datemax,queryType,nameType);
			ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getSensor_type().equals("Temperature")) {
					info.add(list.get(i));
				}
			}
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
					map.put("name", stamap.get(rdata.getName()));
					map.put("current_num", rdata.getCurrent_num()+" "+stamap.get(rdata.getSensor_base_unit()));
					data.add(map);
				}
			}
			ExcelUtil.export("传感器历史数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
    /**
     * 导出传感器实时温度数据
     */
	@RequestMapping("/exprottecurrentList")
    @MethodLog(remark = "导出传感器实时温度",openType = "3")
	public void exprotcurrentList(HttpServletRequest request,
    		String datemin, String datemax, String queryType,String IPType,String nameType,
    		HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap();		
    	//int width = 256*14;
		String[] excelHeader = { "时间", "服务器用途", "服务器ip地址", "传感器类型", "传感器名字", "传感器读数"};
		String[] ds_titles = { "start_time", "sysname", "IP", "sensor_type", "name","current_num"};
		int[] widths = {256*25, 256*25, 256*25,256*25, 256*25, 256*25};
		int[] ds_format = { 1,2,2,2,2,2};
		try{
			List<sensorrecord> list = getCurrentValueList(IPType,queryType,nameType);
			ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getSensor_type().equals("Temperature")) {
					info.add(list.get(i));
				}
			}
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
					map.put("name", stamap.get(rdata.getName()));
					map.put("current_num", rdata.getCurrent_num()+" "+stamap.get(rdata.getSensor_base_unit()));
					data.add(map);
				}
			}
			ExcelUtil.export("传感器实时数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
		
	//传感器风扇
		
	// 传感器风扇信息页面
	@RequestMapping("/sensorfainfo")
	@MethodLog(remark = "进入风扇页面",openType = "3")
	public String sensorfainfo(HttpServletRequest request) {
		String queryType = "Fan";
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		List<name> name = null;
		QueryParam param = new QueryParam();
		param.setQueryType(queryType);
		name = sensorImpl.getName(param);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("serversip", serversip);
		request.setAttribute("name", name);
		request.setAttribute("menu", "sensorfainfo");
		return "/WEB-INF/sensor/sensorfainfo";
	}	
		
	//选择后的传感器风扇信息页面
	@RequestMapping("/sensorfaquery")
	@MethodLog(remark = "查询风扇历史信息",openType = "3")
    public String sensorfaquery(HttpServletRequest request,
    		String datemin, String datemax, String queryType, String keyword,String IPType,String nameType){
		HashMap<String, String> map = englishImpl.getmap();	
		List<sensorrecord> list = getValueList(IPType,datemin,datemax,queryType,nameType);
		ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getSensor_type().equals("Fan")) {
				info.add(list.get(i));
			}
		}
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
    	request.setAttribute("info",info);
    	request.setAttribute("menu", "sensorfainfo");
        return "/WEB-INF/sensor/sensorfaDiv";
    }	
	
	//选择后的传感器风扇折线图
	@RequestMapping("/sensorfachartDiv")
	@MethodLog(remark = "查询风扇历史信息折线图",openType = "3")
    public String sensorfachartDiv(HttpServletRequest request,
    		String datemin, String datemax, String queryType, String keyword,String IPType,String nameType){
		HashMap<String, String> map = englishImpl.getmap();	
		List<sensorrecord> list = getValueList(IPType,datemin,datemax,queryType,nameType);
		ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getSensor_type().equals("Fan")) {
				info.add(list.get(i));
			}
		}
    	try {
	    	LinkedHashMap<String,LinkedHashMap<String, Object>> lineMap = 
	    			new LinkedHashMap<String,LinkedHashMap<String,Object>>();
	    	EChartsOptionParam lineParam = new EChartsOptionParam();
			for(sensorrecord apr : info){
				String format = "";
				SimpleDateFormat dts = new SimpleDateFormat("MM-dd HH:mm");
				format = dts.format(apr.getStart_time());
	        	LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
	            data.put(map.get(apr.getName()), apr.getCurrent_num());
	            lineMap.put(format, data);
	            lineParam = new EChartsOptionParam(map.get(apr.getName()), "", false);
		        lineParam.setyUnit(map.get(apr.getSensor_base_unit()));
	        }
	        
	        Option lineOption = EChartsUtil.generateLineMore(lineMap, lineParam);
	    	request.setAttribute("networkLineOption", GsonUtil.format(lineOption));
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	request.setAttribute("map", map);
    	request.setAttribute("info",info);
    	request.setAttribute("menu", "sensorfainfo");
        return "/WEB-INF/sensor/sensorfachartDiv";
    }
	
	//选择后的传感器实时风扇数据页面
	@RequestMapping("/sensorfacurrentquery")
	@MethodLog(remark = "查询风扇实时信息",openType = "3")
    public String sensorfacurrentquery(HttpServletRequest request,
    		String queryType, String keyword,String IPType,String nameType) throws Exception{
		HashMap<String, String> map = englishImpl.getmap();		
		List<sensorrecord> list = getCurrentValueList(IPType,queryType,nameType);
		ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getSensor_type().equals("Fan")) {
				info.add(list.get(i));
			}
		}
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
    	request.setAttribute("info",info);
    	request.setAttribute("menu", "sensorfainfo");
        return "/WEB-INF/sensor/sensorfacurrentDiv";
    }
	
	/**
     * 导出传感器风扇数据
     */
	@RequestMapping("/exprotfaList")
    @MethodLog(remark = "导出传感器风扇历史信息",openType = "3")
	public void exprotfaList(HttpServletRequest request,
    		String datemin, String datemax, String queryType,String IPType,String nameType,
    		HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap();		
    	//int width = 256*14;
		String[] excelHeader = { "时间", "服务器用途", "服务器ip地址", "传感器类型", "传感器名字", "传感器读数"};
		String[] ds_titles = { "start_time", "sysname", "IP", "sensor_type", "name","current_num"};
		int[] widths = {256*25, 256*25, 256*25, 256*25, 256*25, 256*25};
		int[] ds_format = { 1,2,2,2,2,2};
		try{
			List<sensorrecord> list = getValueList(IPType,datemin,datemax,queryType,nameType);
			ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getSensor_type().equals("Fan")) {
					info.add(list.get(i));
				}
			}
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
					map.put("name", stamap.get(rdata.getName()));
					map.put("current_num", rdata.getCurrent_num()+" "+stamap.get(rdata.getSensor_base_unit()));
					data.add(map);
				}
			}
			ExcelUtil.export("传感器风扇历史数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
    /**
     * 导出传感器实时风扇数据
     */
    
	@RequestMapping("/exprotfacurrentList")
    @MethodLog(remark = "导出传感器风扇实时信息",openType = "3")
	public void exprotfacurrentList(HttpServletRequest request,
    		String datemin, String datemax, String queryType,String IPType,String nameType,
    		HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap();		
    	//int width = 256*14;
		String[] excelHeader = { "时间", "服务器用途", "服务器ip地址", "传感器类型", "传感器名字", "传感器读数"};
		String[] ds_titles = { "start_time", "sysname", "IP", "sensor_type", "name","current_num"};
		int[] widths = {256*25, 256*25, 256*25,  256*25,256*25, 256*25};
		int[] ds_format = { 1,2,2,2,2,2};
		try{
			List<sensorrecord> list = getCurrentValueList(IPType,queryType,nameType);
			ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getSensor_type().equals("Fan")) {
					info.add(list.get(i));
				}
			}
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
					map.put("name", stamap.get(rdata.getName()));
					map.put("current_num", rdata.getCurrent_num()+" "+stamap.get(rdata.getSensor_base_unit()));
					data.add(map);
				}
			}
			ExcelUtil.export("传感器实时风扇数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
    //传感器电压
    
 // 传感器电压信息页面
 	@RequestMapping("/sensorvoinfo")
 	@MethodLog(remark = "进入电压页面",openType = "3")
 	public String sensorvoinfo(HttpServletRequest request) {
 		String queryType = "Voltage";
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		List<name> name = null;
		QueryParam param = new QueryParam();
		param.setQueryType(queryType);
		name = sensorImpl.getName(param);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("serversip", serversip);
		request.setAttribute("name", name);
 		request.setAttribute("menu", "sensorvoinfo");
 		return "/WEB-INF/sensor/sensorvoinfo";
 	}	
 		
 	//选择后的传感器电压信息页面
 	@RequestMapping("/sensorvoquery")
 	@MethodLog(remark = "查询电压历史信息",openType = "3")
     public String sensorvoquery(HttpServletRequest request,
     		String datemin, String datemax, String queryType, String keyword,String IPType,String nameType){
 		HashMap<String, String> map = englishImpl.getmap(); 		
 		List<sensorrecord> list = getValueList(IPType,datemin,datemax,queryType,nameType);
 		ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
 		for (int i = 0; i < list.size(); i++) {
 			if(list.get(i).getSensor_type().equals("Voltage")) {
 				info.add(list.get(i));
 			}
 		}
 		Map<String,String> sysnamemap = sensorImpl.getSysname();
 		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
     	request.setAttribute("info",info);
     	request.setAttribute("menu", "sensorvoinfo");
         return "/WEB-INF/sensor/sensorvoDiv";
     }	
 	
 	//选择后的传感器电压折线图
 	@RequestMapping("/sensorvochartDiv")
 	@MethodLog(remark = "查询电压历史信息折线图",openType = "3")
     public String sensorvochartDiv(HttpServletRequest request,
     		String datemin, String datemax, String queryType, String keyword,String IPType,String nameType){
 		HashMap<String, String> map = englishImpl.getmap(); 		
 		List<sensorrecord> list = getValueList(IPType,datemin,datemax,queryType,nameType);
 		ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
 		for (int i = 0; i < list.size(); i++) {
 			if(list.get(i).getSensor_type().equals("Voltage")) {
 				info.add(list.get(i));
 			}
 		}
     	try {
 	    	LinkedHashMap<String,LinkedHashMap<String, Object>> lineMap = 
 	    			new LinkedHashMap<String,LinkedHashMap<String,Object>>();
 	    	EChartsOptionParam lineParam = new EChartsOptionParam();
 			for(sensorrecord apr : info){
 				String format = "";
 				SimpleDateFormat dts = new SimpleDateFormat("MM-dd HH:mm");
 				format = dts.format(apr.getStart_time());
 	        	LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
 	            data.put(map.get(apr.getName()), apr.getCurrent_num());
 	            lineMap.put(format, data);
 	            lineParam = new EChartsOptionParam(map.get(apr.getName()), "", false);
 		        lineParam.setyUnit(map.get(apr.getSensor_base_unit()));
 	        }
 	        
 	        Option lineOption = EChartsUtil.generateLineMore(lineMap, lineParam);
 	    	request.setAttribute("networkLineOption", GsonUtil.format(lineOption));
     	} catch (Exception e) {
 			e.printStackTrace();
 		}
     	request.setAttribute("map", map);
     	request.setAttribute("info",info);
     	request.setAttribute("menu", "sensorvoinfo");
         return "/WEB-INF/sensor/sensorvochartDiv";
     }
 	
 	//选择后的传感器实时电压数据页面
 	@RequestMapping("/sensorvocurrentquery")
 	@MethodLog(remark = "查询风扇实时信息",openType = "3")
     public String sensorvocurrentquery(HttpServletRequest request,
     		String queryType, String keyword,String IPType,String nameType) throws Exception{
 		HashMap<String, String> map = englishImpl.getmap(); 		
 		List<sensorrecord> list = getCurrentValueList(IPType,queryType,nameType);
 		ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
 		for (int i = 0; i < list.size(); i++) {
 			if(list.get(i).getSensor_type().equals("Voltage")) {
 				info.add(list.get(i));
 			}
 		}
 		Map<String,String> sysnamemap = sensorImpl.getSysname();
 		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
     	request.setAttribute("info",info);
     	request.setAttribute("menu", "sensorvoinfo");
         return "/WEB-INF/sensor/sensorvocurrentDiv";
     }
 	
 	/**
      * 导出传感器电压数据
      */
 	@RequestMapping("/exprotvoList")
    @MethodLog(remark = "导出传感器电压历史信息",openType = "3")
 	public void exprotvoList(HttpServletRequest request,
     		String datemin, String datemax, String queryType,String IPType,String nameType,
     		HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap(); 		
     	//int width = 256*14;
 		String[] excelHeader = { "时间", "服务器ip地址", "传感器类型", "传感器名字", "传感器读数"};
 		String[] ds_titles = { "start_time", "IP", "sensor_type", "name","current_num"};
 		int[] widths = {256*25, 256*25, 256*25, 256*25, 256*25};
 		int[] ds_format = { 1,2,2,2,2};
 		try{
 			List<sensorrecord> list = getValueList(IPType,datemin,datemax,queryType,nameType);
 			ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
 			for (int i = 0; i < list.size(); i++) {
 				if(list.get(i).getSensor_type().equals("Voltage")) {
 					info.add(list.get(i));
 				}
 			}
 			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
 			if(info != null && info.size() > 0){
 				for(sensorrecord rdata : info){
 					Map<String,Object> map = new HashMap<String,Object>();
 					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 					map.put("start_time", format.format(rdata.getStart_time()));
 					map.put("IP", rdata.getIP());
 					map.put("sensor_type", stamap.get(rdata.getSensor_type()));
 					map.put("name", stamap.get(rdata.getName()));
 					map.put("current_num", rdata.getCurrent_num()+" "+stamap.get(rdata.getSensor_base_unit()));
 					data.add(map);
 				}
 			}
 			ExcelUtil.export("传感器电压历史数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
 		}catch(Exception e){
 			e.printStackTrace();
 		}
 	}
 	
     /**
      * 导出传感器实时电压数据
      */
 	@RequestMapping("/exprotvocurrentList")
    @MethodLog(remark = "导出传感器电压实时信息",openType = "3")
 	public void exprotvocurrentList(HttpServletRequest request,
     		String datemin, String datemax, String queryType,String IPType,String nameType,
     		HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap(); 		
     	//int width = 256*14;
 		String[] excelHeader = { "时间", "服务器ip地址", "传感器类型", "传感器名字", "传感器读数"};
 		String[] ds_titles = { "start_time", "IP", "sensor_type", "name","current_num"};
 		int[] widths = {256*25, 256*25, 256*25, 256*25, 256*25};
 		int[] ds_format = { 1,2,2,2,2};
 		try{
 			List<sensorrecord> list = getCurrentValueList(IPType,queryType,nameType);
 			ArrayList<sensorrecord> info = new ArrayList<sensorrecord>();
 			for (int i = 0; i < list.size(); i++) {
 				if(list.get(i).getSensor_type().equals("Voltage")) {
 					info.add(list.get(i));
 				}
 			}
 			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
 			if(info != null && info.size() > 0){
 				for(sensorrecord rdata : info){
 					Map<String,Object> map = new HashMap<String,Object>();
 					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 					map.put("start_time", format.format(rdata.getStart_time()));
 					map.put("IP", rdata.getIP());
 					map.put("sensor_type", stamap.get(rdata.getSensor_type()));
 					map.put("name", stamap.get(rdata.getName()));
 					map.put("current_num", rdata.getCurrent_num()+" "+stamap.get(rdata.getSensor_base_unit()));
 					data.add(map);
 				}
 			}
 			ExcelUtil.export("传感器实时电压数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
 		}catch(Exception e){
 			e.printStackTrace();
 		}
 	}
}
