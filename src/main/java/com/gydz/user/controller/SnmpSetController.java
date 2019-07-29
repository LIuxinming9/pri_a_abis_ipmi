package com.gydz.user.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.User;
import com.gydz.user.model.chassisList;
import com.gydz.user.model.selrecord;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.model.snmp;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.selImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.user.service.snmpSetImpl;
import com.gydz.util.ipmiutil;

@Controller
@RequestMapping("/snmpSet")
public class SnmpSetController {
	
	@Resource
	private snmpSetImpl snmpSetImpl;
	
	@Resource
	private sensorImpl sensorImpl;
	
	static String IP = "";
	 // 服务器信息页面
	@RequestMapping("/list")
	@MethodLog(remark = "查询OID信息",openType = "3")
	public String serviceipinfo(HttpServletRequest request) throws Exception {
		List<snmp> list = null;
		list = snmpSetImpl.get();
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("list", list);
		request.setAttribute("menu", "snmpSet");
		return "/WEB-INF/snmp/snmpSet";
	}
	
	@ResponseBody
    @RequestMapping(value = "/isExist",method = RequestMethod.POST) 
    public String isExist(Model model,String oid) {
    	
    	snmp snmp = snmpSetImpl.getByOid(oid);
    	if(snmp == null){
    		return "success";
    	}else{
    		return "error";
    	}
    }
	
	@RequestMapping("/query")
	@MethodLog(remark = "查询OID信息",openType = "3")
	public String query(HttpServletRequest request, String keyword) throws Exception {
		List<snmp> listAll = null;
		List<snmp> list = null;
		listAll = snmpSetImpl.get();
		if(StringUtils.isBlank(keyword)){
			list = listAll;
		}else{
			list = snmpSetImpl.getByKeyword(keyword);
		}
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("list", list);
		request.setAttribute("menu", "snmpSet");
		return "/WEB-INF/snmp/snmpSet";
	}
	
	//增加服务器
	@RequestMapping("/del")
	@MethodLog(remark = "删除一个OID",openType = "0")
    public String del(HttpServletRequest request){
		List<serversip> serversIpList = null;
		List<serversip> serversip = new ArrayList<serversip>();
		serversIpList = sensorImpl.getServersip();
		for (int i = 0; i < serversIpList.size(); i++) {
			if(serversIpList.get(i).getSnmp_online()==0) {
				serversip.add(serversIpList.get(i));
			}
		}
		List<snmp> list = new ArrayList<snmp>();
		List<snmp> idlist = new ArrayList<snmp>();;
		List<Integer> idList = new ArrayList<Integer>();
		if(!IP.equals("")) {
			list = snmpSetImpl.getOID();
			idlist = snmpSetImpl.getOIDByIPType(IP);
			for (int i = 0; i < idlist.size(); i++) {
				idList.add(idlist.get(i).getId());
			}
		}
		request.setAttribute("list", list);
		request.setAttribute("idList", idList);
		request.setAttribute("idlist", idlist);
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu","snmpSet"); 
        return "/WEB-INF/snmp/del";
    }
	
	//增加一个OID
	@RequestMapping("/add")
	@MethodLog(remark = "增加一个OID",openType = "0")
    public String add(HttpServletRequest request){
		request.setAttribute("menu","snmpSet"); 
        return "/WEB-INF/snmp/addByBrand";
    }
	
	@RequestMapping(value = "/saveByBrand",method = RequestMethod.POST)
    @MethodLog(remark = "添加OID",openType = "0")
    public String saveByBrand(@ModelAttribute("snmp") snmp snmp, HttpServletRequest request, Model model){
        snmpSetImpl.saveByBrand(snmp);
        return "redirect:/snmpSet/list";
    }
	
	@RequestMapping("/queryByIp")
	@MethodLog(remark = "增加一个OID",openType = "0")
    public String addByIp(HttpServletRequest request, String IPType){
		List<serversip> serversIpList = null;
		List<serversip> serversip = new ArrayList<serversip>();
		serversIpList = sensorImpl.getServersip();
		for (int i = 0; i < serversIpList.size(); i++) {
			if(serversIpList.get(i).getSnmp_online()==0) {
				serversip.add(serversIpList.get(i));
			}
		}
		String brand = "";
		for (int i = 0; i < serversip.size(); i++) {
			if(serversip.get(i).getHostname().equals(IPType)) {
				brand = serversip.get(i).getBrand();
			}
		}
		List<snmp> list = null;
		list = snmpSetImpl.getOIDByBrand(brand);
		List<snmp> idlist = null;
		idlist = snmpSetImpl.getOIDByIPType(IPType);
		List<Integer> idList = new ArrayList<Integer>();
		for (int i = 0; i < idlist.size(); i++) {
			idList.add(idlist.get(i).getId());
		}
		
		request.setAttribute("serversip", serversip);
		request.setAttribute("list", list);
		request.setAttribute("idList", idList);
		request.setAttribute("idlist", idlist);
		request.setAttribute("menu","snmpSet"); 
        return "/WEB-INF/snmp/delDiv";
    }
	
	@RequestMapping("/insert")
	@MethodLog(remark = "增加一个OID",openType = "0")
    public String insert(HttpServletRequest request){
		String id = request.getParameterValues("id")[0];
		String ip = request.getParameterValues("hostname")[0];
		IP = ip;
		int iid = snmpSetImpl.getIidByIP(ip);
		snmp snmp = new snmp();
		snmp.setIid(iid);
		snmp.setOoid(Integer.parseInt(id));
		snmpSetImpl.insert(snmp);
        return "redirect:/snmpSet/del";
    }
	
	//删除用户
    @RequestMapping(value = "/delByIp")
    @MethodLog(remark = "删除选定服务器的OID",openType = "1")
    public String delByIp(HttpServletRequest request){
        try {
        	String id = request.getParameterValues("id")[0];
    		String ip = request.getParameterValues("hostname")[0];
    		IP = ip;
    		int iid = snmpSetImpl.getIidByIP(ip);
    		snmp snmp = new snmp();
    		snmp.setIid(iid);
    		snmp.setOoid(Integer.parseInt(id));
    		snmpSetImpl.delByIp(snmp);
        }catch (Exception ex){
        	System.out.println("____________删除失败——————————————————————");
        }
        return "redirect:/snmpSet/del";
    }
	
	// 保存服务器信息页面
	
	@RequestMapping(value = "/saveSysIP", method = RequestMethod.POST)
	@MethodLog(remark = "修改服务器信息",openType = "2")
	public String saveSysIP(HttpServletRequest request, Model model) {

		List<serversip> serversiplist = null;
		serversiplist = new ArrayList<serversip>();
		String[] ids = request.getParameterValues("id");
		String[] hostnames = request.getParameterValues("hostname");
		String[] usernames = request.getParameterValues("username");
		String[] passwords = request.getParameterValues("password");
		String[] names = request.getParameterValues("name");
		for (int i = 0; i < hostnames.length; i++) {
			serversip serversip = new serversip();
			serversip.setId(Integer.parseInt(ids[i]));
			serversip.setHostname(hostnames[i]);
			serversip.setUsername(usernames[i]);
			serversip.setPassword(passwords[i]);
			serversip.setName(names[i]);
			serversiplist.add(serversip);
		}
		for (int i = 0; i < hostnames.length; i++) {
			sensorImpl.updateSysIP(serversiplist.get(i));
		}
		request.setAttribute("menu", "updateSysIP");
		return "redirect:/remotesys/updateSysIP";
	}
}
