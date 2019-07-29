package com.gydz.user.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.english;
import com.gydz.user.model.selrecord;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.selImpl;
import com.gydz.user.service.sensorImpl;

@Controller
@RequestMapping("/english")
public class EnglishController {

	@Resource
	private sensorImpl sensorImpl;
	
	@Resource
	private englishImpl englishImpl;
	
	@Resource
	private selImpl selImpl;
	
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
	
	@RequestMapping("/english")
	public String sensorwarnrefer(HttpServletRequest request) {
		List<String> list = getallenglish();
		List<String> elist = englishImpl.getlist();
		for (int i = 0; i < list.size(); i++) {
			if(!elist.contains(list.get(i))) {
				englishImpl.add(list.get(i));
			}
		}
		request.setAttribute("elist", elist);
		request.setAttribute("list", list);
		request.setAttribute("menu", "english");
		return "/WEB-INF/english/english";
	}
	
	@RequestMapping("/query")
    @MethodLog(remark = "查询用户列表",openType = "3")
    public String list(HttpServletRequest request, String keyword){
    	List<String> list = new ArrayList<String>();
    	List<String> elist = englishImpl.getlist();
    	if(StringUtils.isBlank(keyword)){
    		list = elist;
    	}else{
    		for (int i = 0; i < elist.size(); i++) {
				if(elist.get(i).equals(keyword)) {
					list.add(keyword);
				}
			}
    	}
    	request.getSession().setAttribute("list",list);
    	request.setAttribute("elist", elist);
    	request.setAttribute("menu","english");
    	request.setAttribute("keyword",keyword);
        return "/WEB-INF/english/english";
    }
	
	//增加服务器
	@RequestMapping("/add")
	@MethodLog(remark = "增加",openType = "0")
    public String add(HttpServletRequest request){
		String word = request.getParameterValues("word")[0];
		englishImpl.add(word);
		return "redirect:/english/english";
    }
}	
