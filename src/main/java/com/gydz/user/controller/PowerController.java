package com.gydz.user.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.chassisStatus;
import com.gydz.user.model.fruInfo2;
import com.gydz.user.model.powerInfo;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.service.chassisImpl;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.fruImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.user.service.warnhistoryImpl;
import com.gydz.util.ipmiutil;

@Controller
@RequestMapping("/power")
public class PowerController {

	@Resource
	private sensorImpl sensorImpl;
    
	@Resource
	private fruImpl fruImpl;
	
	@Resource
	private chassisImpl chassisImpl;
	
	@Resource
	private warnhistoryImpl warnhistoryImpl;
	
	@Resource
	private englishImpl englishImpl;
	
    // 电源管理页面
	@RequestMapping("/power")
	@MethodLog(remark = "进入应急处置页面",openType = "3")
	public String power(HttpServletRequest request) {
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "power");
		return "/WEB-INF/power/power";
	}
	
	@RequestMapping("/query")
	@MethodLog(remark = "应急处置页面选定服务器",openType = "3")
	public String powerquery(HttpServletRequest request,String IPType) throws Exception {
		HashMap<String, String> map = englishImpl.getmap();	
		powerInfo powerInfo = new powerInfo();
		List<serversip> serversIpList = null;
		serversIpList = sensorImpl.getServersip();
		serversip serversIp = new serversip();
		for (int i = 0; i < serversIpList.size(); i++) {
			if(serversIpList.get(i).getHostname().equals(IPType)) {
				serversIp = serversIpList.get(i);
			}
		}
		//获取监控对象有关信息
		powerInfo.setServiceName(serversIp.getName().isEmpty()?"无":serversIp.getName());
		powerInfo.setServiceIP(serversIp.getHostname().isEmpty()?"无":serversIp.getHostname());
		powerInfo.setServiceLocation(serversIp.getLocation().isEmpty()?"无":serversIp.getLocation());
		//获取FRU的产品和系统板信息
		QueryParam param = new QueryParam();
		param.setIPType(IPType);
		List<fruInfo2> fruInfo2List = fruImpl.getInfobyParam(param);
		fruInfo2 BoardInfo = new fruInfo2();
		fruInfo2 ProductInfo = new fruInfo2();
		for (int i = 0; i < fruInfo2List.size(); i++) {
			String name = fruInfo2List.get(i).getName();
			if(name.equals("BoardInfo")) {
				BoardInfo = fruInfo2List.get(i);
			}else if(name.equals("ProductInfo")) {
				ProductInfo = fruInfo2List.get(i);
			}
		}
		powerInfo.setServiceManufacturer(ProductInfo.getManufacturer().isEmpty()?"无":ProductInfo.getManufacturer());
		powerInfo.setProductInfoProductName(ProductInfo.getProduct_name().isEmpty()?"无":ProductInfo.getProduct_name());
		powerInfo.setBoardInfoMfgDate(BoardInfo.getMfg_date().isEmpty()?"无":BoardInfo.getMfg_date());
		powerInfo.setProductInfoSerialNumber(ProductInfo.getSerial_number().isEmpty()?"无":ProductInfo.getSerial_number());
		powerInfo.setBoardInfoPartNumber(BoardInfo.getPart_number().isEmpty()?"无":BoardInfo.getPart_number());
		powerInfo.setProductInfoModelNumber(ProductInfo.getModel_number().isEmpty()?"无":ProductInfo.getModel_number());
		powerInfo.setBoardInfoSerialNumber(BoardInfo.getSerial_number().isEmpty()?"无":BoardInfo.getSerial_number());
		//获取告警信息
		QueryParam warnParam = new QueryParam();
		warnParam.setIPType(IPType);
		List<sensorrecord> warnList = warnhistoryImpl.getlistbyparam(warnParam);
		String warnCurrentCategory = "";
		String warnCurrentLevel = "";
		int sum = 0;
		for (int i = 0; i < warnList.size(); i++) {
			if(warnList.get(i).getWarn_state()==0) {
				warnCurrentCategory += (map.get(warnList.get(i).getSensor_type())+";");
				warnCurrentLevel += (warnList.get(i).getWarnlevel()+";"); 
				sum += 1;
			}
		}
		powerInfo.setWarnCurrentCategory(warnCurrentCategory.isEmpty()?"无":warnCurrentCategory);
		powerInfo.setWarnCurrentLevel(warnCurrentLevel.isEmpty()?"无":warnCurrentLevel);
		powerInfo.setWarnCurrentSum(sum+"");
		powerInfo.setWarnHistorySum((warnList.size()-sum)+"");
		//获取电源状态
		QueryParam powerParam = new QueryParam();
		powerParam.setIPType(IPType);
		List<chassisStatus> powerList = chassisImpl.getChassiscurrentInfobyParam(powerParam);
		powerInfo.setIsWarn(powerList.get(0).isIs_power_fault()?"电源有故障":"电源没有故障");
		powerInfo.setIsStart(powerList.get(0).isIs_power_on()?"电源已开启":"电源未开启");
		request.setAttribute("powerInfo", powerInfo);
		request.setAttribute("menu", "power");
		return "/WEB-INF/power/powerDiv";
	}
	
	
	@RequestMapping("/powerup")
	@MethodLog(remark = "打开电源",openType = "2")
	public String powerup(HttpServletRequest request,String IPType) throws Exception {
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		for (int i = 0; i < serversip.size(); i++) {
			if(IPType.equals(serversip.get(i).getHostname())) {
				ipmiutil.PowerUp(serversip.get(i).getHostname(),serversip.get(i).getUsername(),serversip.get(i).getPassword());
			}
		}
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "power");
		return "/WEB-INF/power/power";
	}
	
	@RequestMapping("/powerdown")
	@MethodLog(remark = "关闭电源",openType = "2")
	public String powerdown(HttpServletRequest request,String IPType) throws Exception {
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		for (int i = 0; i < serversip.size(); i++) {
			if(IPType.equals(serversip.get(i).getHostname())) {
				ipmiutil.PowerDown(serversip.get(i).getHostname(),serversip.get(i).getUsername(),serversip.get(i).getPassword());
			}
		}
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "power");
		return "/WEB-INF/power/power";
	}
	
	@RequestMapping("/hardreset")
	@MethodLog(remark = "硬重启",openType = "2")
	public String hardreset(HttpServletRequest request,String IPType) throws Exception {
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		for (int i = 0; i < serversip.size(); i++) {
			if(IPType.equals(serversip.get(i).getHostname())) {
				ipmiutil.HardReset(serversip.get(i).getHostname(),serversip.get(i).getUsername(),serversip.get(i).getPassword());
			}
		}
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "power");
		return "/WEB-INF/power/power";
	}
}
