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
import com.gydz.user.model.chassisStatus;
import com.gydz.user.model.name;
import com.gydz.user.model.resource_name;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.util.ExcelUtil;
import com.gydz.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	
	@Resource
	private sensorImpl sensorImpl;
	
	@Resource
	private englishImpl englishImpl;
	//资源管理管理
  	@RequestMapping(value = "/resourceinfo")
  	@MethodLog(remark = "进入资源管理页面",openType = "3")
  	public String resourceinfo(HttpServletRequest request){
  		List<serversip> serversip = null;
  		serversip = sensorImpl.getServersip();
  		Set<String> entityid = null;
  		entityid = sensorImpl.getEntityid();
  		request.setAttribute("entityid", entityid);
  		request.setAttribute("serversip", serversip);
  		request.setAttribute("menu", "resourceinfo");
  		return "/WEB-INF/resource/resourceinfo";
  	}
  	
  	@RequestMapping("/changename")
	@ResponseBody
	public void changename(HttpServletRequest request,HttpServletResponse response) throws Exception{
  	HashMap<String, String> map = englishImpl.getmap();
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String IPType = request.getParameter("IPType");
	List<sensorrecord> sensorwarnreferlist = new ArrayList<sensorrecord>();
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
			param.setIPType(ipList.get(i));
			alist = sensorImpl.getcurrentInfobyParam(param);
			for (int j = 0; j < alist.size(); j++) {
				sensorwarnreferlist.add(alist.get(j));
			}
		}
		
	}else {
		param.setIPType(IPType);
		alist = sensorImpl.getcurrentInfobyParam(param);
		for (int i = 0; i < alist.size(); i++) {
			sensorwarnreferlist.add(alist.get(i));
		}
	}
	Set<String> set = new LinkedHashSet<String>();
	for (int i = 0; i < sensorwarnreferlist.size(); i++) {
		set.add(sensorwarnreferlist.get(i).getEntity_id());
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
	
  	
  	@RequestMapping(value = "/resourceDiv")
  	@MethodLog(remark = "查询资源管理信息",openType = "3")
	public String resourceDiv(HttpServletRequest request,String IPType,String nameType){
  		HashMap<String, String> map = englishImpl.getmap();
		List<serversip> serversip = null;
		List<resource_name> resource_name = null;
		List<sensorrecord> list = new ArrayList<sensorrecord>();
    	List<sensorrecord> alist = null;
		String brand = "";
		QueryParam param = new QueryParam();
		if(IPType.contains("=")) {
			brand = IPType.substring(IPType.indexOf("=")+1);
			serversip = sensorImpl.getServersip();
			List<String> ipList = new ArrayList<String>();
			for (int i = 0; i < serversip.size(); i++) {
				if(serversip.get(i).getBrand().equals(brand)) {
					ipList.add(serversip.get(i).getHostname());
				}
			}
			for (int i = 0; i < ipList.size(); i++) {
				param.setIPType(ipList.get(i));
				param.setNameType(nameType);
				alist = sensorImpl.getcurrentInfobyParam(param);
				for (int j = 0; j < alist.size(); j++) {
					list.add(alist.get(j));
				}
			}
			
		}else {
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = sensorImpl.getcurrentInfobyParam(param);
			for (int i = 0; i < alist.size(); i++) {
				list.add(alist.get(i));
			}
		}
		
		resource_name = dealres(list,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("resource_name", resource_name);
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "resourceinfo");
		return "/WEB-INF/resource/resourceDiv";
	}
  	
  	@RequestMapping(value = "/resourcesubDiv")
  	@MethodLog(remark = "查询已添加资源信息",openType = "3")
	public String resourcesubDiv(HttpServletRequest request,String entity_id,String IP,String nameType,String IPType){
  		HashMap<String, String> map = englishImpl.getmap();
		List<sensorrecord> list = null;
		QueryParam param = new QueryParam();
    	param.setIPType(IP);
    	param.setEntity_id(entity_id);
		list = sensorImpl.getcurrentInfobyParam(param);
		List<String> hardname = dealhardname(list);
		
		List<sensorrecord> list1 = new ArrayList<sensorrecord>();
		List<sensorrecord> alist = null;
		String brand = "";
		QueryParam param1 = new QueryParam();
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
				param1.setIPType(ipList.get(i));
				param1.setEntity_id(nameType);
				alist = sensorImpl.getcurrentInfobyParam(param1);
				for (int j = 0; j < alist.size(); j++) {
					list1.add(alist.get(j));
				}
			}
			
		}else {
			param1.setIPType(IPType);
			param1.setEntity_id(nameType);
			alist = sensorImpl.getcurrentInfobyParam(param1);
			for (int i = 0; i < alist.size(); i++) {
				list1.add(alist.get(i));
			}
		}
		
		List<resource_name> resource_name = null;
		resource_name = dealres(list1,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("resource_name", resource_name);
		request.setAttribute("map", map);
		request.setAttribute("entity_id", entity_id);
		request.setAttribute("hardname", hardname);
		request.setAttribute("menu", "resourceinfo");
		
		if(hardname.size() < 8) {
			return "/WEB-INF/resource/resourcesubsmallDiv";
		}else {
			return "/WEB-INF/resource/resourcesubDiv";
		}
		
	}
  	
  	@RequestMapping(value = "/resourcesubenptyDiv")
  	@MethodLog(remark = "查询未添加资源信息",openType = "3")
	public String resourcesubenptyDiv(HttpServletRequest request,String entity_id,String IP,String nameType,String IPType){
  		HashMap<String, String> map = englishImpl.getmap();
		System.out.println(entity_id+IP);
		List<sensorrecord> list = null;
		QueryParam param = new QueryParam();
    	param.setIPType(IP);
    	param.setEntity_id(entity_id);
		list = sensorImpl.getcurrentInfobyParam(param);
		List<String> emptyname = dealemptyname(list);
		
		List<sensorrecord> list1 = new ArrayList<sensorrecord>();
		List<sensorrecord> alist = null;
		String brand = "";
		QueryParam param1 = new QueryParam();
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
				param1.setIPType(ipList.get(i));
				param1.setEntity_id(nameType);
				alist = sensorImpl.getcurrentInfobyParam(param1);
				for (int j = 0; j < alist.size(); j++) {
					list1.add(alist.get(j));
				}
			}
			
		}else {
			param1.setIPType(IPType);
			param1.setEntity_id(nameType);
			alist = sensorImpl.getcurrentInfobyParam(param1);
			for (int i = 0; i < alist.size(); i++) {
				list1.add(alist.get(i));
			}
		}
		
		List<resource_name> resource_name = null;
		resource_name = dealres(list1,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("resource_name", resource_name);
		request.setAttribute("map", map);
		request.setAttribute("entity_id", entity_id);
		request.setAttribute("emptyname", emptyname);
		request.setAttribute("menu", "resourceinfo");
		if(emptyname.size() < 8) {
			return "/WEB-INF/resource/resourcesubenptysmallDiv";
		}else {
			return "/WEB-INF/resource/resourcesubenptyDiv";
		}
		
	}
	
	/**
     * 导出资源管理数据
     */
    @RequestMapping("/exprotresourceList")
    @MethodLog(remark = "导出资源管理信息",openType = "3")
	public void exprotresourceList(HttpServletRequest request,String IPType,String nameType,HttpServletResponse response){
    	//int width = 256*14;
    	HashMap<String, String> stamap = englishImpl.getmap();
		String[] excelHeader = { "时间", "服务器用途","服务器ip地址", "资源名", "卡槽数量", "硬件数量", "可补充数量"};
		String[] ds_titles = { "start_time", "sysname","IP", "name", "cardnum","hardnum","emptynum"};
		int[] widths = {256*30, 256*25,256*25, 256*25, 256*25, 256*25, 256*25};
		int[] ds_format = { 1,2,2,2,2,2,2};
		try{
			List<resource_name> resource_name = null;
			
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
					param.setIPType(ipList.get(i));
					param.setNameType(nameType);
					alist = sensorImpl.getcurrentInfobyParam(param);
					for (int j = 0; j < alist.size(); j++) {
						list.add(alist.get(j));
					}
				}
				
			}else {
				param.setIPType(IPType);
				param.setNameType(nameType);
				alist = sensorImpl.getcurrentInfobyParam(param);
				for (int i = 0; i < alist.size(); i++) {
					list.add(alist.get(i));
				}
			}
			
			resource_name = dealres(list,nameType);
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,String> sysnamemap = sensorImpl.getSysname();
			if(list != null && list.size() > 0){
				for(resource_name rdata : resource_name){
					Map<String,Object> map = new HashMap<String,Object>();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("start_time", format.format(rdata.getStart_time()));
					map.put("sysname", sysnamemap.get(rdata.getIP()));
					map.put("IP", rdata.getIP());
					map.put("name", stamap.get(rdata.getEntity_id()));
					map.put("cardnum", rdata.getCardnum());
					map.put("hardnum", rdata.getHardnum());
					map.put("emptynum", rdata.getEmptynum());
					data.add(map);
				}
			}
			ExcelUtil.export("资源管理数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
    
    public List<resource_name> dealres(List<sensorrecord> list,String nameType){
    	Set<String> entityidset = new LinkedHashSet<String>();
  		for (int i = 0; i < list.size(); i++) {
  			entityidset.add(list.get(i).getEntity_id()+list.get(i).getIP());
		}
  		List<resource_name> resourcebyname = new ArrayList<resource_name>();
  		Iterator<String> Iterator = entityidset.iterator();
  		
		if(nameType==null||nameType==""||nameType.equals("全部")) {
			while(Iterator.hasNext()) {
				
				int cardnum = 0;
				int hardnum = 0;
				String next = Iterator.next();
				for (int j = 0; j < list.size(); j++) {
					if(next.equals(list.get(j).getEntity_id()+list.get(j).getIP())) {
						cardnum++;
						if(list.get(j).getStatesAsserted()==null) {
							hardnum++;
						}else if(!list.get(j).getStatesAsserted().isEmpty()) {
							hardnum++;
						}
					}
				}
				resource_name resource_name = new resource_name();
				resource_name.setStart_time(list.get(0).getStart_time());
				resource_name.setIP(next.substring(next.indexOf('1')));
				resource_name.setEntity_id(next.substring(0, next.indexOf('1')));
				resource_name.setCardnum(cardnum);
				resource_name.setHardnum(hardnum);
				resource_name.setEmptynum(cardnum - hardnum);
				resourcebyname.add(resource_name);
	  		}
		}else {
			while(Iterator.hasNext()) {
				int cardnum = 0;
				int hardnum = 0;
				String next = Iterator.next();
				if(next.equals(nameType+list.get(0).getIP())){
					for (int j = 0; j < list.size(); j++) {
						if(next.equals(list.get(j).getEntity_id()+list.get(j).getIP())) {
							cardnum++;
							if(list.get(j).getStatesAsserted()==null) {
								hardnum++;
							}else if(!list.get(j).getStatesAsserted().isEmpty()) {
								hardnum++;
							}
						}
					}
					resource_name resource_name = new resource_name();
					resource_name.setStart_time(list.get(0).getStart_time());
					resource_name.setIP(list.get(0).getIP());
					resource_name.setEntity_id(next.substring(0,next.indexOf('1')));
					resource_name.setCardnum(cardnum);
					resource_name.setHardnum(hardnum);
					resource_name.setEmptynum(cardnum - hardnum);
					resourcebyname.add(resource_name);
				}
				
	  		}
		}
		return resourcebyname;
    }
    
    public List<String> dealhardname(List<sensorrecord> list){
    	List<String> hardname = new ArrayList<String>();
    	for (int i = 0; i < list.size(); i++) {
    		if(list.get(i).getStatesAsserted()==null) {
    			hardname.add(list.get(i).getName());
			}else if(!list.get(i).getStatesAsserted().isEmpty()) {
				hardname.add(list.get(i).getName());
			}
		}
		return hardname;
    }
    public List<String> dealemptyname(List<sensorrecord> list){
    	List<String> emptyname = new ArrayList<String>();
    	for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getStatesAsserted() != null && list.get(i).getStatesAsserted().isEmpty()) {
				emptyname.add(list.get(i).getName());
			}
		}
		return emptyname;
    }
}
	
