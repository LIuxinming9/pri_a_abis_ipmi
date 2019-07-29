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
import com.gydz.user.service.resourcewarnsetImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.util.ExcelUtil;
import com.gydz.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/resourcewarnset")
public class ResourceWarnSetController {

	@Resource
	private resourcewarnsetImpl resourcewarnsetImpl;

	@Resource
	private sensorImpl sensorImpl;
	
	static String IP = "";
	
	@Resource
	private englishImpl englishImpl;
	
	@RequestMapping("/resourcewarnset")
	@MethodLog(remark = "进入资源告警列表页面",openType = "3")
	public String sensorwarnrefer(HttpServletRequest request) {
		HashMap<String, String> map = englishImpl.getmap();
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		if(!IP.equals("")) {
			List<sensorrecord> sensorwarnsetlist = null;
			QueryParam param = new QueryParam();
			param.setIPType(IP);
			sensorwarnsetlist = resourcewarnsetImpl.getresourcewarnsetbyparam(param);
			request.setAttribute("sensorwarnsetlist", sensorwarnsetlist);
		}
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "resourcewarnset");
		return "/WEB-INF/ipmiwarn/resourcewarnset";
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
	
	@RequestMapping("/refresh")
	public String refresh(HttpServletRequest request) {
		List<sensorrecord> warnsetlist = null;
		QueryParam param = new QueryParam();
		warnsetlist = resourcewarnsetImpl.getresourcewarnsetbyparam(param);
		for (int i = 0; i < warnsetlist.size(); i++) {
			String nameip = warnsetlist.get(i).getIPname();
			String emptyresource = warnsetlist.get(i).getEmptyresource();
			List<sensorrecord> addlist = null;
			QueryParam addparam = new QueryParam();
			addparam.setIPType(nameip.substring(nameip.indexOf('1')));
			addparam.setEntity_id(nameip.substring(0, nameip.indexOf('1')));
			addlist = sensorImpl.getcurrentInfobyParam(addparam);
			String emptyname = dealemptyname(addlist);
			if(!emptyname.equals(emptyresource)) {
				resourcewarnsetImpl.refresh(nameip,emptyname);
			}
		}
		return "redirect:/resourcewarnset/query";
	}
	
	 public String dealemptyname(List<sensorrecord> list){
    	String emptyname = "";
    	for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getStatesAsserted() != null && list.get(i).getStatesAsserted().isEmpty()) {
				emptyname += list.get(i).getName();
			}
		}
		return emptyname;
    }
	
	@RequestMapping("/changename")
	@ResponseBody
	public void changename(HttpServletRequest request,HttpServletResponse response) throws Exception{
	HashMap<String, String> map = englishImpl.getmap();
	JSONArray jsonArray=new JSONArray();
	JSONObject jsonObject=new JSONObject();
	String IPType = request.getParameter("IPType");
	List<sensorrecord> sensorwarnsetlist = getValue(IPType,null);
	Set<String> set = new LinkedHashSet<String>();
	for (int i = 0; i < sensorwarnsetlist.size(); i++) {
		set.add(sensorwarnsetlist.get(i).getEntity_id());
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
	
	
	public List<sensorrecord> getValue(String IPType,String nameType){
		List<sensorrecord> sensorwarnsetlist = new ArrayList<sensorrecord>();
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
				QueryParam param = new QueryParam();
				param.setIPType(ipList.get(i));
				param.setNameType(nameType);
				alist = resourcewarnsetImpl.getresourcewarnsetbyparam(param);
				for (int j = 0; j < alist.size(); j++) {
					sensorwarnsetlist.add(alist.get(j));
				}
			}
			
		}else {
			QueryParam param = new QueryParam();
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = resourcewarnsetImpl.getresourcewarnsetbyparam(param);
			for (int i = 0; i < alist.size(); i++) {
				sensorwarnsetlist.add(alist.get(i));
			}
		}
		return sensorwarnsetlist;
	}
	
	@RequestMapping("/query")
	@MethodLog(remark = "查询资源告警列表",openType = "3")
	public String query(HttpServletRequest request,String nameType,String IPType) {
		HashMap<String, String> map = englishImpl.getmap();
		List<sensorrecord> sensorwarnsetlist = getValue(IPType,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("sensorwarnsetlist", sensorwarnsetlist);
		request.setAttribute("menu", "resourcewarnset");
		return "/WEB-INF/ipmiwarn/resourcewarnsetDiv";
	}
	
	/**
     * 导出资源告警列表
     */
	@RequestMapping("/exprotList")
    @MethodLog(remark = "导出资源告警列表",openType = "3")
	public void exportExcel(HttpServletRequest request,String IPType,String nameType,
    		HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap();
    	//int width = 256*14;
		String[] excelHeader = {"服务器用途", "服务器ip地址", "告警部件名字"};
		String[] ds_titles = {"sysname", "IP","name"};
		int[] widths = {256*25, 256*25,256*25};
		int[] ds_format = {2,2,2};
		try{
			List<sensorrecord> list = getValue(IPType,nameType);
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
					map.put("name", stamap.get(rdata.getEntity_id()));
					data.add(map);
				}
			}
			ExcelUtil.export("资源告警列表", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	//删除用户
    @RequestMapping(value = "/delSysIP")
    @MethodLog(remark = "删除一条资源告警信息",openType = "1")
    public String delSysIP(HttpServletRequest request){
        try {
        	String IPType = request.getParameterValues("IP")[0];
        	String name = request.getParameterValues("entity_id")[0];
        	IP = IPType;
        	QueryParam param = new QueryParam();
    		param.setIPType(IPType);
    		param.setEntity_id(name);
        	resourcewarnsetImpl.delSysIP(param);
        }catch (Exception ex){
        	System.out.println("____________删除失败——————————————————————");
        }
        request.setAttribute("menu", "resourcewarnset");
        return "redirect:/resourcewarnset/resourcewarnset";
    }
    
    // 保存服务器信息页面
 	@RequestMapping(value = "/save", method = RequestMethod.POST)
 	@MethodLog(remark = "修改一条资源告警信息",openType = "2")
 	public String saveSysIP(HttpServletRequest request, Model model) {

 		List<sensorrecord> list = new ArrayList<sensorrecord>();
 		String[] IPs = request.getParameterValues("IP");
 		IP = IPs[0];
 		String[] names = request.getParameterValues("entity_id");
 		
 		for (int i = 0; i < IPs.length; i++) {
 			sensorrecord sensorrecord = new sensorrecord();
 			sensorrecord.setIP(IPs[i]);
 			sensorrecord.setEntity_id(names[i]);
 			sensorrecord.setIPname(IPs[i]+names[i]);
 			list.add(sensorrecord);
 		}
 		for (int i = 0; i < IPs.length; i++) {
 			resourcewarnsetImpl.update(list.get(i));
 		}
 		request.setAttribute("menu", "resourcewarnset");
 		return "redirect:/resourcewarnset/resourcewarnset";
 	}
}	
