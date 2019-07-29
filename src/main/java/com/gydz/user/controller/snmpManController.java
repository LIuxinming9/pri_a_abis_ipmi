package com.gydz.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.User;
import com.gydz.user.model.english;
import com.gydz.user.model.selrecord;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.model.snmp;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.selImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.user.service.snmpImpl;
import com.gydz.util.ipmiutil;

@Controller
@RequestMapping("/snmpMan")
public class snmpManController {

	@Resource
	private sensorImpl sensorImpl;
	
	@Resource
	private englishImpl englishImpl;
	
	@Resource
	private selImpl selImpl;
	
	@Resource
	private snmpImpl snmpImpl;
	
	@RequestMapping("/snmpMan")
	@MethodLog(remark = "进入手动设置页面",openType = "3")
	public String sensorwarnrefer(HttpServletRequest request) {
		List<snmp> snmpList = new ArrayList<snmp>();
		snmpList = snmpImpl.getAllName();
    	request.setAttribute("snmpList", snmpList);
		request.setAttribute("menu","snmpMan");
		return "/WEB-INF/snmp/snmpMan";
	}
	
	@RequestMapping("/query")
    @MethodLog(remark = "查询手动设置数据",openType = "3")
    public String list(HttpServletRequest request, String keyword){
    	List<snmp> snmpList = new ArrayList<snmp>();
    	List<snmp> elist = new ArrayList<snmp>();
    	elist = snmpImpl.getAllName();
    	if(StringUtils.isBlank(keyword)){
    		snmpList = elist;
    	}else{
    		for (int i = 0; i < elist.size(); i++) {
				if(elist.get(i).getHostname().equals(keyword)||elist.get(i).getName().equals(keyword)) {
					snmp snmp = new snmp();
					snmp.setHostname(elist.get(i).getHostname());
					snmp.setName(elist.get(i).getName());
					snmpList.add(snmp);
				}
			}
    	}
    	request.setAttribute("snmpList", snmpList);
    	request.setAttribute("menu","snmpMan");
    	request.setAttribute("keyword",keyword);
        return "/WEB-INF/snmp/snmpMan";
    }
    
	// 保存服务器信息页面
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@MethodLog(remark = "修改手动设置数据",openType = "2")
	public String saveSysIP(HttpServletRequest request, Model model) {
		List<snmp> snmpList = new ArrayList<snmp>();
		String[] ids = request.getParameterValues("id");
		String[] hostnames = request.getParameterValues("hostname");
		String[] names = request.getParameterValues("name");
		String[] values = request.getParameterValues("value");
		if(hostnames!=null) {
			for (int i = 0; i < hostnames.length; i++) {
				snmp snmp = new snmp();
				snmp.setStart_time(new Date());
				snmp.setId(Integer.parseInt(ids[i]));
				snmp.setHostname(hostnames[i]);
				snmp.setName(names[i]);
				snmp.setValue(values[i]);
				snmp.setOid("无");
				snmpList.add(snmp);
			}
		}
		for (int i = 0; i < snmpList.size(); i++) {
			snmpImpl.updateAllName(snmpList.get(i));
		}
		request.setAttribute("menu", "snmpMan");
		return "redirect:/snmpMan/snmpMan";
	}
	
	//删除用户
    @RequestMapping(value = "/del")
    @MethodLog(remark = "删除一个英语翻译",openType = "1")
    public String delSysIP(HttpServletRequest request){
        try {
        	String[] ids = request.getParameterValues("id");
    		snmpImpl.delAllName(Integer.parseInt(ids[0]));
        }catch (Exception ex){
        	System.out.println("____________删除失败——————————————————————");
        }
        request.setAttribute("menu", "snmpMan");
        return "redirect:/snmpMan/snmpMan";
    }

}	
