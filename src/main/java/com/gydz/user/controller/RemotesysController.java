package com.gydz.user.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.selrecord;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.service.chassiswarnsetImpl;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.selImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.util.ipmiutil;

@Controller
@RequestMapping("/remotesys")
public class RemotesysController {
	
	@Resource
	private chassiswarnsetImpl chassiswarnsetImpl;
	
	@Resource
	private sensorImpl sensorImpl;
	
	@Resource
	private selImpl selImpl;
	
	@Resource
	private englishImpl englishImpl;
	
	public List<String> getallenglish(){
		List<String> list = new ArrayList<String>();
		QueryParam param = new QueryParam();
		List<sensorrecord> senlist = sensorImpl.getcurrentInfobyParam(param);
		Set<String> idset = new LinkedHashSet<String>();
		Set<String> typeset = new LinkedHashSet<String>();
		Set<String> unitset = new LinkedHashSet<String>();
		List<selrecord> sensorwarnreferlist = null;
		sensorwarnreferlist = selImpl.getInfobyParam(param);
		Set<String> seltypeset = new LinkedHashSet<String>();
		Set<String> selnameset = new LinkedHashSet<String>();
		for (int i = 0; i < sensorwarnreferlist.size(); i++) {
			selnameset.add(sensorwarnreferlist.get(i).getEvent());
		}
		for (int i = 0; i < sensorwarnreferlist.size(); i++) {
			seltypeset.add(sensorwarnreferlist.get(i).getSensorType());
		}
		for (int i = 0; i < senlist.size(); i++) {
			idset.add(senlist.get(i).getEntity_id());
			typeset.add(senlist.get(i).getSensor_type());
			list.add(senlist.get(i).getName());
			unitset.add(senlist.get(i).getSensor_base_unit());
		}
		Iterator<String> idit = idset.iterator();
		while(idit.hasNext()) {
			list.add(idit.next());
		}
		Iterator<String> typeit = typeset.iterator();
		while(typeit.hasNext()) {
			list.add(typeit.next());
		}
		Iterator<String> unitit = unitset.iterator();
		while(unitit.hasNext()) {
			list.add(unitit.next());
		}
		Iterator<String> seltypeit = seltypeset.iterator();
		while(seltypeit.hasNext()) {
			list.add(seltypeit.next());
		}
		Iterator<String> selnameit = selnameset.iterator();
		while(selnameit.hasNext()) {
			list.add(selnameit.next());
		}
		return list;
	}
	
	 // 服务器信息页面
	@RequestMapping("/updateSysIP")
	@MethodLog(remark = "进入监控对象页面",openType = "3")
	public String serviceipinfo(HttpServletRequest request) throws Exception {
		List<String> alist = getallenglish();
		List<String> elist = englishImpl.getlist();
		for (int i = 0; i < alist.size(); i++) {
			if(!elist.contains(alist.get(i))) {
				englishImpl.add(alist.get(i));
			}
		}
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "updateSysIP");
		return "/WEB-INF/remotesys/updateSysIP";
	}
	
	//增加服务器
	@RequestMapping("/addSysIP")
	@MethodLog(remark = "增加一个监控对象",openType = "0")
    public String add(HttpServletRequest request){
		sensorImpl.addServersip();
		request.setAttribute("menu", "updateSysIP");
        return "redirect:/remotesys/updateSysIP";
    }
	
	// 保存服务器信息页面
	
	@RequestMapping(value = "/saveSysIP", method = RequestMethod.POST)
	@MethodLog(remark = "修改监控对象信息",openType = "2")
	public String saveSysIP(HttpServletRequest request, Model model) {
		
		List<String> chassisList = new ArrayList<String>();
		chassisList.add("is_power_fault");
		chassisList.add("is_interlock");
		chassisList.add("is_power_overload");
		chassisList.add("is_power_on");
		chassisList.add("was_power_fault");
		chassisList.add("was_interlock");
		chassisList.add("ac_failed");
		chassisList.add("cooling_fault_detected");
		chassisList.add("drive_fault_detected");
		chassisList.add("is_chassis_intrusion_active");
		List<String> list = selImpl.selectANum();
		List<serversip> serversiplist = new ArrayList<serversip>();
		String[] ids = request.getParameterValues("id");
		String[] hostnames = request.getParameterValues("hostname");
		String[] usernames = request.getParameterValues("username");
		String[] passwords = request.getParameterValues("password");
		String[] names = request.getParameterValues("name");
		String[] brands = request.getParameterValues("brand");
		String[] locations = request.getParameterValues("location");
		String[] notes = request.getParameterValues("note");
		for (int i = 0; i < hostnames.length; i++) {
			serversip serversip = new serversip();
			serversip.setId(Integer.parseInt(ids[i]));
			serversip.setHostname(hostnames[i]);
			serversip.setUsername(usernames[i]);
			serversip.setPassword(passwords[i]);
			serversip.setName(names[i]);
			serversip.setBrand(brands[i]);
			serversip.setLocation(locations[i]);
			serversip.setNote(notes[i]);
			serversip.setIs_online(0);
			serversiplist.add(serversip);
		}
		for (int i = 0; i < hostnames.length; i++) {
			sensorImpl.updateSysIP(serversiplist.get(i));
			if(!list.contains(hostnames[i])) {
				selImpl.addANum(hostnames[i]);
				for (int j = 0; j < chassisList.size(); j++) {
					chassiswarnsetImpl.add(hostnames[i],chassisList.get(j));
				}
			}
		}
		request.setAttribute("menu", "updateSysIP");
		return "redirect:/remotesys/updateSysIP";
	}
	
	//删除用户
    @RequestMapping(value = "/delSysIP")
    @MethodLog(remark = "删除一个监控对象",openType = "1")
    public String delSysIP(HttpServletRequest request){
        try {
        	String hostname = request.getParameterValues("hostname")[0];
        	int id = Integer.parseInt(request.getParameterValues("id")[0]);
        	System.out.println(hostname+id);
        	
        	sensorImpl.delSysIP(id);
        	selImpl.delANum(hostname);
        }catch (Exception ex){
        	System.out.println("____________删除失败——————————————————————");
        }
        request.setAttribute("menu", "updateSysIP");
        return "redirect:/remotesys/updateSysIP";
    }

}
