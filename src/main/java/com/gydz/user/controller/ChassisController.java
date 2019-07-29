package com.gydz.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.chassisList;
import com.gydz.user.model.chassisStatus;
import com.gydz.user.model.fruInfo2;
import com.gydz.user.model.serversip;
import com.gydz.user.model.state;
import com.gydz.user.service.chassisImpl;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.util.ExcelUtil;
import com.gydz.util.ipmiutil;

@Controller
@RequestMapping("/chassis")
public class ChassisController {

	@Resource
	private sensorImpl sensorImpl;
	
	@Resource
	private chassisImpl chassisImpl;
	
	@Resource
	private englishImpl englishImpl;
	// 底盘状态页面
	@RequestMapping("/chassisinfo")
	@MethodLog(remark = "进入机箱查询页面",openType = "3")
	public String chassisinfo(HttpServletRequest request) {
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "chassisinfo");
		return "/WEB-INF/chassis/chassisinfo";
	}
	
	
	//处理底盘状态数据
	public static List<chassisList> chassisdata(List<chassisStatus> chassisStatus){
		List<chassisList> info = new ArrayList<chassisList>();
		for (int i = 0; i < chassisStatus.size(); i++) {
			chassisList chassisList = new chassisList();
			chassisList.setIP(chassisStatus.get(i).getIP());
			chassisList.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList.setName("is_power_control_fault");
			chassisList.setState(chassisStatus.get(i).isIs_power_control_fault()+"");
			chassisList chassisList1 = new chassisList();
			chassisList1.setIP(chassisStatus.get(i).getIP());
			chassisList1.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList1.setName("is_power_fault");
			chassisList1.setState(chassisStatus.get(i).isIs_power_fault()+"");
			chassisList chassisList2 = new chassisList();
			chassisList2.setIP(chassisStatus.get(i).getIP());
			chassisList2.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList2.setName("is_interlock");
			chassisList2.setState(chassisStatus.get(i).isIs_interlock()+"");
			chassisList chassisList3 = new chassisList();
			chassisList3.setIP(chassisStatus.get(i).getIP());
			chassisList3.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList3.setName("is_power_overload");
			chassisList3.setState(chassisStatus.get(i).isIs_power_overload()+"");
			chassisList chassisList4 = new chassisList();
			chassisList4.setIP(chassisStatus.get(i).getIP());
			chassisList4.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList4.setName("is_power_on");
			chassisList4.setState(chassisStatus.get(i).isIs_power_on()+"");
			chassisList chassisList5 = new chassisList();
			chassisList5.setIP(chassisStatus.get(i).getIP());
			chassisList5.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList5.setName("was_ipmi_power_on");
			chassisList5.setState(chassisStatus.get(i).isWas_ipmi_power_on()+"");
			chassisList chassisList6 = new chassisList();
			chassisList6.setIP(chassisStatus.get(i).getIP());
			chassisList6.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList6.setName("was_power_fault");
			chassisList6.setState(chassisStatus.get(i).isWas_power_fault()+"");
			chassisList chassisList7 = new chassisList();
			chassisList7.setIP(chassisStatus.get(i).getIP());
			chassisList7.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList7.setName("was_interlock");
			chassisList7.setState(chassisStatus.get(i).isWas_interlock()+"");
			chassisList chassisList8 = new chassisList();
			chassisList8.setIP(chassisStatus.get(i).getIP());
			chassisList8.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList8.setName("ac_failed");
			chassisList8.setState(chassisStatus.get(i).isAc_failed()+"");
			chassisList chassisList9 = new chassisList();
			chassisList9.setIP(chassisStatus.get(i).getIP());
			chassisList9.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList9.setName("is_chassis_identify_command_supported");
			chassisList9.setState(chassisStatus.get(i).isIs_chassis_identify_command_supported()+"");
			chassisList chassisList10 = new chassisList();
			chassisList10.setIP(chassisStatus.get(i).getIP());
			chassisList10.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList10.setName("cooling_fault_detected");
			chassisList10.setState(chassisStatus.get(i).isCooling_fault_detected()+"");
			chassisList chassisList11 = new chassisList();
			chassisList11.setIP(chassisStatus.get(i).getIP());
			chassisList11.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList11.setName("drive_fault_detected");
			chassisList11.setState(chassisStatus.get(i).isDrive_fault_detected()+"");
			chassisList chassisList12 = new chassisList();
			chassisList12.setIP(chassisStatus.get(i).getIP());
			chassisList12.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList12.setName("is_front_panel_lockout_active");
			chassisList12.setState(chassisStatus.get(i).isIs_front_panel_lockout_active()+"");
			chassisList chassisList13 = new chassisList();
			chassisList13.setIP(chassisStatus.get(i).getIP());
			chassisList13.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList13.setName("is_chassis_intrusion_active");
			chassisList13.setState(chassisStatus.get(i).isIs_chassis_intrusion_active()+"");
			chassisList chassisList14 = new chassisList();
			chassisList14.setIP(chassisStatus.get(i).getIP());
			chassisList14.setStart_time(chassisStatus.get(i).getStart_time());
			chassisList14.setName("is_front_panel_button_capabilities_set");
			chassisList14.setState(chassisStatus.get(i).isIs_front_panel_button_capabilities_set()+"");
			//info.add(chassisList);
			info.add(chassisList1);
			info.add(chassisList2);
			info.add(chassisList3);
			info.add(chassisList4);
			//info.add(chassisList5);
			info.add(chassisList6);
			info.add(chassisList7);
			info.add(chassisList8);
			//info.add(chassisList9);
			info.add(chassisList10);
			info.add(chassisList11);
			//info.add(chassisList12);
			info.add(chassisList13);
			//info.add(chassisList14);
			
			if(chassisStatus.get(i).isIs_front_panel_button_capabilities_set()) {
				chassisList chassisList15 = new chassisList();
				chassisList15.setIP(chassisStatus.get(i).getIP());
				chassisList15.setStart_time(chassisStatus.get(i).getStart_time());
				chassisList15.setName("is_standby_button_disable_allowed");
				chassisList15.setState(chassisStatus.get(i).isIs_standby_button_disable_allowed()+"");
				chassisList chassisList16 = new chassisList();
				chassisList16.setIP(chassisStatus.get(i).getIP());
				chassisList16.setStart_time(chassisStatus.get(i).getStart_time());
				chassisList16.setName("is_diagnostic_interrupt_button_disable_allowed");
				chassisList16.setState(chassisStatus.get(i).isIs_diagnostic_interrupt_button_disable_allowed()+"");
				chassisList chassisList17 = new chassisList();
				chassisList17.setIP(chassisStatus.get(i).getIP());
				chassisList17.setStart_time(chassisStatus.get(i).getStart_time());
				chassisList17.setName("is_reset_button_disable_allowed");
				chassisList17.setState(chassisStatus.get(i).isIs_reset_button_disable_allowed()+"");
				chassisList chassisList18 = new chassisList();
				chassisList18.setIP(chassisStatus.get(i).getIP());
				chassisList18.setStart_time(chassisStatus.get(i).getStart_time());
				chassisList18.setName("is_power_off_button_disable_allowed");
				chassisList18.setState(chassisStatus.get(i).isIs_power_off_button_disable_allowed()+"");
				chassisList chassisList19 = new chassisList();
				chassisList19.setIP(chassisStatus.get(i).getIP());
				chassisList19.setStart_time(chassisStatus.get(i).getStart_time());
				chassisList19.setName("is_standby_button_disabled");
				chassisList19.setState(chassisStatus.get(i).isIs_standby_button_disabled()+"");
				chassisList chassisList20 = new chassisList();
				chassisList20.setIP(chassisStatus.get(i).getIP());
				chassisList20.setStart_time(chassisStatus.get(i).getStart_time());
				chassisList20.setName("is_diagnostic_interrupt_button_disabled");
				chassisList20.setState(chassisStatus.get(i).isIs_diagnostic_interrupt_button_disabled()+"");
				chassisList chassisList21 = new chassisList();
				chassisList21.setIP(chassisStatus.get(i).getIP());
				chassisList21.setStart_time(chassisStatus.get(i).getStart_time());
				chassisList21.setName("is_reset_button_disabled");
				chassisList21.setState(chassisStatus.get(i).isIs_reset_button_disabled()+"");
				chassisList chassisList22 = new chassisList();
				chassisList22.setIP(chassisStatus.get(i).getIP());
				chassisList22.setStart_time(chassisStatus.get(i).getStart_time());
				chassisList22.setName("is_power_off_button_disabled");
				chassisList22.setState(chassisStatus.get(i).isIs_power_off_button_disabled()+"");
				/*info.add(chassisList15);
				info.add(chassisList16);
				info.add(chassisList17);
				info.add(chassisList18);
				info.add(chassisList19);
				info.add(chassisList20);
				info.add(chassisList21);
				info.add(chassisList22);*/
			}
		}
		return info;
	}
	
	public static List<chassisList> chassisquerydata(List<chassisList> infolist,String nameType){
		List<chassisList> info = new ArrayList<chassisList>();
		for (int i = 0; i < infolist.size(); i++) {
			int j = 0;
			if(nameType.equals("全部")) {
				info.add(i, infolist.get(i));
			}else if(nameType.equals(infolist.get(i).getName())) {
				info.add(j, infolist.get(i));
				j++;
			}
		}
		return info;
	}
	
	public List<chassisStatus> getValue(String IPType,String datemin,String datemax,String nameType){
		List<chassisStatus> chassisStatus = new ArrayList<chassisStatus>();
    	List<chassisStatus> alist = null;
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
				param.setNameType(nameType);
				alist = chassisImpl.getChassisInfobyParam(param);
				for (int j = 0; j < alist.size(); j++) {
					chassisStatus.add(alist.get(j));
				}
			}
			
		}else {
			param.setDatemin(datemin);
			param.setDatemax(datemax);
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = chassisImpl.getChassisInfobyParam(param);
			for (int i = 0; i < alist.size(); i++) {
				chassisStatus.add(alist.get(i));
			}
		}
		return chassisStatus;
	}
	
	public List<chassisStatus> getCurrentValue(String IPType,String nameType){
		List<chassisStatus> chassisStatus = new ArrayList<chassisStatus>();
    	List<chassisStatus> alist = null;
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
				alist = chassisImpl.getChassiscurrentInfobyParam(param);
				for (int j = 0; j < alist.size(); j++) {
					chassisStatus.add(alist.get(j));
				}
			}
			
		}else {
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = chassisImpl.getChassiscurrentInfobyParam(param);
			for (int i = 0; i < alist.size(); i++) {
				chassisStatus.add(alist.get(i));
			}
		}
		return chassisStatus;
	}
	
	//选择后的底盘状态页面
	@RequestMapping("/chassisquery")
	@MethodLog(remark = "查询机箱历史状态",openType = "3")
    public String chassisquery(HttpServletRequest request,
    		String datemin, String datemax, String keyword,String IPType,String nameType){
		HashMap<String, String> map = englishImpl.getmap();	
		List<chassisStatus> chassisStatus = getValue(IPType,datemin,datemax,nameType);
		List<chassisList> infolist = new ArrayList<chassisList>();
		infolist = chassisdata(chassisStatus);
		List<chassisList> info = new ArrayList<chassisList>();
		info = chassisquerydata(infolist,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("info", info);
		request.setAttribute("menu", "chassisinfo");
        return "/WEB-INF/chassis/chassisDiv";
    }
	
	
	//选择后的底盘实时状态页面
	@RequestMapping("/chassiscurrentquery")
	@MethodLog(remark = "查询机箱实时状态",openType = "3")
    public String chassiscurrentquery(HttpServletRequest request,
    		 String keyword,String IPType,String nameType){
		HashMap<String, String> map = englishImpl.getmap();	
		List<chassisStatus> chassisStatus = getCurrentValue(IPType,nameType);
		
		List<chassisList> infolist = new ArrayList<chassisList>();
		infolist = chassisdata(chassisStatus);
		List<chassisList> info = new ArrayList<chassisList>();
		info = chassisquerydata(infolist,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("info", info);
		request.setAttribute("menu", "chassisinfo");
        return "/WEB-INF/chassis/chassisDiv";
    }
	
	/**
     * 导出底盘状态
     */
    @RequestMapping("/exprotChassisList")
    @MethodLog(remark = "导出机箱历史状态",openType = "3")
	public void exprotChassisList(HttpServletRequest request,
    		String datemin, String datemax, String queryType,String IPType,String nameType,
    		HttpServletResponse response){
    	HashMap<String, String> stamap = englishImpl.getmap();
    	//int width = 256*14;
		String[] excelHeader = { "时间", "服务器用途", "服务器ip地址", "机箱名字", "机箱状态"};
		String[] ds_titles = { "start_time", "sysname", "IP", "name", "state"};
		int[] widths = {256*25, 256*25,256*25, 256*50, 256*25};
		int[] ds_format = { 1,2,2,2,2,2,2};
		try{
			
			List<chassisStatus> chassisStatus = getValue(IPType,datemin,datemax,nameType);
			
			List<chassisList> infolist = new ArrayList<chassisList>();
			infolist = chassisdata(chassisStatus);
			List<chassisList> info = new ArrayList<chassisList>();
			info = chassisquerydata(infolist,nameType);
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,String> sysnamemap = sensorImpl.getSysname();
			if(info != null && info.size() > 0){
				for(chassisList rdata : info){
					Map<String,Object> map = new HashMap<String,Object>();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("start_time", format.format(rdata.getStart_time()));
					map.put("sysname", sysnamemap.get(rdata.getIP()));
					map.put("IP", rdata.getIP());
					map.put("name", stamap.get(rdata.getName()));
					map.put("state", rdata.getState());
					data.add(map);
				}
			}
			ExcelUtil.export("底盘状态历史数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
    
    /**
     * 导出实时底盘状态
     */
    @RequestMapping("/exprotChassiscurrentList")
    @MethodLog(remark = "导出实时机箱状态",openType = "3")
	public void exprotChassiscurrentList(HttpServletRequest request,
    		 String queryType,String IPType,String nameType,
    		HttpServletResponse response){
    	HashMap<String, String> stamap = englishImpl.getmap();
    	//int width = 256*14;
		String[] excelHeader = { "时间", "服务器用途", "服务器ip地址", "机箱名字", "机箱状态"};
		String[] ds_titles = { "start_time", "sysname", "IP", "name", "state"};
		int[] widths = {256*25, 256*25,256*25, 256*50, 256*25};
		int[] ds_format = { 1,2,2,2,2,2,2};
		try{
			List<chassisStatus> chassisStatus = getCurrentValue(IPType,nameType);
			List<chassisList> infolist = new ArrayList<chassisList>();
			infolist = chassisdata(chassisStatus);
			List<chassisList> info = new ArrayList<chassisList>();
			info = chassisquerydata(infolist,nameType);
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,String> sysnamemap = sensorImpl.getSysname();
			if(info != null && info.size() > 0){
				for(chassisList rdata : info){
					Map<String,Object> map = new HashMap<String,Object>();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("start_time", format.format(rdata.getStart_time()));
					map.put("sysname", sysnamemap.get(rdata.getIP()));
					map.put("IP", rdata.getIP());
					map.put("name", stamap.get(rdata.getName()));
					map.put("state", rdata.getState());
					data.add(map);
				}
			}
			ExcelUtil.export("机箱状态实时数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
