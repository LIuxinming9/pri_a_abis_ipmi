package com.gydz.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.chassisStatus;
import com.gydz.user.model.sel_name;
import com.gydz.user.model.sel_type;
import com.gydz.user.model.selrecord;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.selImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.util.ExcelUtil;
import com.gydz.util.ResponseUtil;
import com.sun.jna.Platform;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sel")
public class SelController {

	@Resource
	private sensorImpl sensorImpl;
	
	@Resource
	private selImpl selImpl;

	@Resource
	private englishImpl englishImpl;
	
	
	//日志管理
	@RequestMapping(value = "/selinfo")
	@MethodLog(remark = "进入服务器日志页面",openType = "3")
	public String selinfo(HttpServletRequest request){
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		List<sel_type> sel_type = null;
		sel_type = selImpl.getsel_type();
		List<sel_name> sel_name = null;
		sel_name = selImpl.getsel_name();
		String datemin = "";
		String datemax = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date nowDate = new Date();
    	if(!Platform.isWindows()){
        	TimeZone timeZone = TimeZone.getTimeZone("CST");
    		format.setTimeZone(timeZone);
    	}
    	datemin = format.format(nowDate)+" 00:00:00";
    	datemax = format.format(nowDate)+" 23:59:59";
    	List<selrecord> info = null;
    	QueryParam param = new QueryParam();
    	param.setDatemin(datemin);
		param.setDatemax(datemax);
		info = selImpl.getInfobyParam(param);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("param",param);
    	request.setAttribute("info",info);
    	request.setAttribute("datemin",datemin);
    	request.setAttribute("datemax",datemax);
		request.setAttribute("serversip", serversip);
		request.setAttribute("sel_name", sel_name);
		request.setAttribute("sel_type", sel_type);
		request.setAttribute("menu", "selinfo");
	    return "/WEB-INF/sel/selinfo";
	}
	
	@RequestMapping("/changename")
	@ResponseBody
	public void changename(HttpServletRequest request,HttpServletResponse response) throws Exception{
	HashMap<String, String> map = englishImpl.getmap();
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String queryType = request.getParameter("queryType");
	String IPType = request.getParameter("IPType");
	List<selrecord> info = getValue(IPType,null,null,queryType,null);
	Set<String> set = new HashSet<String>();
	for (int i = 0; i < info.size(); i++) {
		set.add(info.get(i).getEvent());
	}
	List<String> list = new ArrayList<String>();
	Iterator<String> it = set.iterator();
	while(it.hasNext()) {
		list.add(it.next());
	}
	for(int i=0;i<list.size();i++)
	{
		jsonObject.put("id", i);
		jsonObject.put("sel_name", list.get(i));
		jsonObject.put("mapsel_name", map.get(list.get(i)));
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
	List<selrecord> sensorwarnreferlist = getValue(IPType,null,null,null,null);
	Set<String> set = new LinkedHashSet<String>();
	for (int i = 0; i < sensorwarnreferlist.size(); i++) {
		set.add(sensorwarnreferlist.get(i).getSensorType());
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
	
	public List<selrecord> getValue(String IPType,String datemin,String datemax,String queryType,String nameType){
		List<selrecord> info = new ArrayList<selrecord>();
    	List<selrecord> alist = null;
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
				param.setDatemin(datemin);
				param.setDatemax(datemax);
				param.setIPType(ipList.get(i));
				param.setQueryType(queryType);
				param.setNameType(nameType);
				alist = selImpl.getInfobyParam(param);
				for (int j = 0; j < alist.size(); j++) {
					info.add(alist.get(j));
				}
			}
			
		}else {
			param.setDatemin(datemin);
			param.setDatemax(datemax);
			param.setIPType(IPType);
			param.setQueryType(queryType);
			param.setNameType(nameType);
			alist = selImpl.getInfobyParam(param);
			for (int i = 0; i < alist.size(); i++) {
				info.add(alist.get(i));
			}
		}
		return info;
	}
	
	//选择后的日志管理页面
	@RequestMapping("/selquery")
	@MethodLog(remark = "查询服务器日志信息",openType = "3")
    public String selquery(HttpServletRequest request,
    		String datemin, String datemax, String queryType, String keyword,String IPType,String nameType){
		HashMap<String, String> map = englishImpl.getmap();
		List<selrecord> info = getValue(IPType,datemin,datemax,queryType,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
    	request.setAttribute("info",info);
    	request.setAttribute("menu", "selinfo");
        return "/WEB-INF/sel/selDiv";
    }
	
	/**
     * 导出日志管理数据
     */
    @RequestMapping("/exprotselList")
    @MethodLog(remark = "导出服务器日志信息",openType = "3")
	public void exprotselList(HttpServletRequest request,
    		String datemin, String datemax, String queryType,String IPType,String nameType,
    		HttpServletResponse response){
    	HashMap<String, String> stamap = englishImpl.getmap();
    	//int width = 256*14;
		String[] excelHeader = { "时间", "服务器用途", "服务器ip地址", "日志生成时间", "日志类别", "日志名"};
		String[] ds_titles = { "start_time","sysname", "IP", "timestamp", "sensorType","event"};
		int[] widths = {256*25, 256*25, 256*25,256*25, 256*25, 256*25};
		int[] ds_format = { 1,2,2,2,2,2,2};
		try{
			List<selrecord> list = getValue(IPType,datemin,datemax,queryType,nameType);
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,String> sysnamemap = sensorImpl.getSysname();
			if(list != null && list.size() > 0){
				for(selrecord rdata : list){
					Map<String,Object> map = new HashMap<String,Object>();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("start_time", format.format(rdata.getStart_time()));
					map.put("sysname", sysnamemap.get(rdata.getIP()));
					map.put("IP", rdata.getIP());
					map.put("timestamp", rdata.getTimestamp());
					map.put("sensorType", stamap.get(rdata.getSensorType()));
					map.put("event", stamap.get(rdata.getEvent()));
					data.add(map);
				}
			}
			ExcelUtil.export("日志管理数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
