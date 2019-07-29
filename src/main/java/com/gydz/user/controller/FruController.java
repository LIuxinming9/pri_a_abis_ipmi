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
import com.gydz.user.model.fruInfo2;
import com.gydz.user.model.fruList;
import com.gydz.user.model.sensorrecord;
import com.gydz.user.model.serversip;
import com.gydz.user.service.englishImpl;
import com.gydz.user.service.fruImpl;
import com.gydz.user.service.sensorImpl;
import com.gydz.util.ExcelUtil;

@Controller
@RequestMapping("/fru")
public class FruController {

	@Resource
	private sensorImpl sensorImpl;
	
	@Resource
	private fruImpl fruImpl;

	@Resource
	private englishImpl englishImpl;
	
	 // FRUchassis页面
	@RequestMapping("/fruchassisinfo")
	@MethodLog(remark = "进入机箱信息页面",openType = "3")
	public String fruinfo(HttpServletRequest request) {
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "fruchassisinfo");
		return "/WEB-INF/fru/fruchassisinfo";
	}
	
	
	//处理FRUchassis数据
	public List<fruList> fruchassisdata(List<fruInfo2> fruInfo2){
		List<fruList> info = new ArrayList<fruList>();
		for (int i = 0; i < fruInfo2.size(); i++) {
			if(fruInfo2.get(i).getName().equals("ChassisInfo")) {
				fruList fruList1 = new fruList();
				fruList1.setStart_time(fruInfo2.get(i).getStart_time());
				fruList1.setIP(fruInfo2.get(i).getIP());
				fruList1.setCategory(fruInfo2.get(i).getName());
				fruList1.setName("serial_number");
				fruList1.setState(fruInfo2.get(i).getSerial_number());
				fruList fruList2 = new fruList();
				fruList2.setStart_time(fruInfo2.get(i).getStart_time());
				fruList2.setIP(fruInfo2.get(i).getIP());
				fruList2.setCategory(fruInfo2.get(i).getName());
				fruList2.setName("part_number");
				fruList2.setState(fruInfo2.get(i).getPart_number());
				fruList fruList3 = new fruList();
				fruList3.setStart_time(fruInfo2.get(i).getStart_time());
				fruList3.setIP(fruInfo2.get(i).getIP());
				fruList3.setCategory(fruInfo2.get(i).getName());
				fruList3.setName("custom_info");
				fruList3.setState(fruInfo2.get(i).getCustom_info());
				info.add(fruList1);
				info.add(fruList2);
				info.add(fruList3);
			}
		}
		return info;
	}
	
	//处理FRU数据
	public List<fruList> fruudata(List<fruInfo2> fruInfo2){
		List<fruList> info = new ArrayList<fruList>();
		for (int i = 0; i < fruInfo2.size(); i++) {
			if(fruInfo2.get(i).getName().equals("ChassisInfo")) {
				fruList fruList1 = new fruList();
				fruList1.setStart_time(fruInfo2.get(i).getStart_time());
				fruList1.setIP(fruInfo2.get(i).getIP());
				fruList1.setCategory(fruInfo2.get(i).getName());
				fruList1.setName("serial_number");
				fruList1.setState(fruInfo2.get(i).getSerial_number());
				fruList fruList2 = new fruList();
				fruList2.setStart_time(fruInfo2.get(i).getStart_time());
				fruList2.setIP(fruInfo2.get(i).getIP());
				fruList2.setCategory(fruInfo2.get(i).getName());
				fruList2.setName("part_number");
				fruList2.setState(fruInfo2.get(i).getPart_number());
				fruList fruList3 = new fruList();
				fruList3.setStart_time(fruInfo2.get(i).getStart_time());
				fruList3.setIP(fruInfo2.get(i).getIP());
				fruList3.setCategory(fruInfo2.get(i).getName());
				fruList3.setName("custom_info");
				fruList3.setState(fruInfo2.get(i).getCustom_info());
				info.add(fruList1);
				info.add(fruList2);
				info.add(fruList3);
			}else if(fruInfo2.get(i).getName().equals("BoardInfo")) {
				fruList fruList4 = new fruList();
				fruList4.setStart_time(fruInfo2.get(i).getStart_time());
				fruList4.setIP(fruInfo2.get(i).getIP());
				fruList4.setCategory(fruInfo2.get(i).getName());
				fruList4.setName("product_name");
				fruList4.setState(fruInfo2.get(i).getProduct_name());
				fruList fruList5 = new fruList();
				fruList5.setStart_time(fruInfo2.get(i).getStart_time());
				fruList5.setIP(fruInfo2.get(i).getIP());
				fruList5.setCategory(fruInfo2.get(i).getName());
				fruList5.setName("mfg_date");
				fruList5.setState(fruInfo2.get(i).getMfg_date().toString());
				fruList fruList6 = new fruList();
				fruList6.setStart_time(fruInfo2.get(i).getStart_time());
				fruList6.setIP(fruInfo2.get(i).getIP());
				fruList6.setCategory(fruInfo2.get(i).getName());
				fruList6.setName("manufacturer");
				fruList6.setState(fruInfo2.get(i).getManufacturer());
				fruList fruList7 = new fruList();
				fruList7.setStart_time(fruInfo2.get(i).getStart_time());
				fruList7.setIP(fruInfo2.get(i).getIP());
				fruList7.setCategory(fruInfo2.get(i).getName());
				fruList7.setName("serial_number");
				fruList7.setState(fruInfo2.get(i).getSerial_number());
				fruList fruList8 = new fruList();
				fruList8.setStart_time(fruInfo2.get(i).getStart_time());
				fruList8.setIP(fruInfo2.get(i).getIP());
				fruList8.setCategory(fruInfo2.get(i).getName());
				fruList8.setName("part_number");
				fruList8.setState(fruInfo2.get(i).getPart_number());
				fruList fruList9 = new fruList();
				fruList9.setStart_time(fruInfo2.get(i).getStart_time());
				fruList9.setIP(fruInfo2.get(i).getIP());
				fruList9.setCategory(fruInfo2.get(i).getName());
				fruList9.setName("custom_info");
				fruList9.setState(fruInfo2.get(i).getCustom_info());
				info.add(fruList4);
				info.add(fruList5);
				info.add(fruList6);
				info.add(fruList7);
				info.add(fruList8);
				info.add(fruList9);
			}else {
				fruList fruList10 = new fruList();
				fruList10.setStart_time(fruInfo2.get(i).getStart_time());
				fruList10.setIP(fruInfo2.get(i).getIP());
				fruList10.setCategory(fruInfo2.get(i).getName());
				fruList10.setName("product_name");
				fruList10.setState(fruInfo2.get(i).getProduct_name());
				fruList fruList11 = new fruList();
				fruList11.setStart_time(fruInfo2.get(i).getStart_time());
				fruList11.setIP(fruInfo2.get(i).getIP());
				fruList11.setCategory(fruInfo2.get(i).getName());
				fruList11.setName("manufacturer");
				fruList11.setState(fruInfo2.get(i).getManufacturer());
				fruList fruList12 = new fruList();
				fruList12.setStart_time(fruInfo2.get(i).getStart_time());
				fruList12.setIP(fruInfo2.get(i).getIP());
				fruList12.setCategory(fruInfo2.get(i).getName());
				fruList12.setName("serial_number");
				fruList12.setState(fruInfo2.get(i).getSerial_number());
				fruList fruList13 = new fruList();
				fruList13.setStart_time(fruInfo2.get(i).getStart_time());
				fruList13.setIP(fruInfo2.get(i).getIP());
				fruList13.setCategory(fruInfo2.get(i).getName());
				fruList13.setName("custom_info");
				fruList13.setState(fruInfo2.get(i).getCustom_info());
				fruList fruList14 = new fruList();
				fruList14.setStart_time(fruInfo2.get(i).getStart_time());
				fruList14.setIP(fruInfo2.get(i).getIP());
				fruList14.setCategory(fruInfo2.get(i).getName());
				fruList14.setName("model_number");
				fruList14.setState(fruInfo2.get(i).getModel_number());
				info.add(fruList10);
				info.add(fruList11);
				info.add(fruList12);
				info.add(fruList13);
				info.add(fruList14);
			}
		}
		return info;
	}
	
	public List<fruList> getinfobyname(List<fruList> infolist,String nameType){
		List<fruList> info = new ArrayList<fruList>();
		for (int i = 0; i < infolist.size(); i++) {
			if(nameType.equals("全部")) {
				info.add(infolist.get(i));
			}else if(infolist.get(i).getName().equals(nameType)) {
				info.add(infolist.get(i));
			}
		}
		return info;
	}
	
	public List<fruInfo2> getFruInfo2List(String IPType,String datemin,String datemax,String nameType){
		List<fruInfo2> fruInfo2 = new ArrayList<fruInfo2>();
    	List<fruInfo2> alist = null;
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
				param.setIPType(ipList.get(i));
				param.setNameType(nameType);
				alist = fruImpl.getInfobyParam(param);
				for (int j = 0; j < alist.size(); j++) {
					fruInfo2.add(alist.get(j));
				}
			}
			
		}else {
			param = new QueryParam();
			param.setDatemin(datemin);
			param.setDatemax(datemax);
			param.setIPType(IPType);
			param.setNameType(nameType);
			alist = fruImpl.getInfobyParam(param);
			for (int i = 0; i < alist.size(); i++) {
				fruInfo2.add(alist.get(i));
			}
		}
		return fruInfo2;
	}
	
	//选择后的FRUchassis页面
	@RequestMapping("/fruchassisquery")
	@MethodLog(remark = "查询机箱信息",openType = "3")
	public String fruquery(HttpServletRequest request,
			String datemin, String datemax, String nameType, String keyword,String IPType){
		HashMap<String, String> map = englishImpl.getmap();
		List<fruInfo2> fruInfo2 = getFruInfo2List(IPType,datemin,datemax,nameType);
		List<fruList> infolist = new ArrayList<fruList>();
		infolist = fruchassisdata(fruInfo2);
		List<fruList> info = new ArrayList<fruList>();
		info = getinfobyname(infolist,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("info",info);
		request.setAttribute("menu", "fruchassisinfo");
	    return "/WEB-INF/fru/fruchassisDiv";
	}
	
	/**
	 * 导出FRUchassis数据
	 */
	@RequestMapping("/exprotFRUchassisList")
	@MethodLog(remark = "导出机箱信息",openType = "3")
	public void exprotFRUList(HttpServletRequest request,
			String datemin, String datemax, String nameType,String IPType,
			HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap();
		//int width = 256*14;
		String[] excelHeader = { "时间", "服务器用途", "服务器ip地址", "FRU类别", "FRU名字", "FRU状态"};
		String[] ds_titles = { "start_time", "sysname", "IP", "category", "name","state"};
		int[] widths = {256*25, 256*25, 256*25,256*25, 256*25, 256*25, 256*25};
		int[] ds_format = { 1,2,2,2,2,2,2};
		try{
			List<fruInfo2> fruInfo2 = getFruInfo2List(IPType,datemin,datemax,nameType);
			List<fruList> infolist = new ArrayList<fruList>();
			infolist = fruchassisdata(fruInfo2);
			List<fruList> info = new ArrayList<fruList>();
			info = getinfobyname(infolist,nameType);
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,String> sysnamemap = sensorImpl.getSysname();
			if(info != null && info.size() > 0){
				for(fruList rdata : info){
					Map<String,Object> map = new HashMap<String,Object>();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("start_time", format.format(rdata.getStart_time()));
					map.put("sysname", sysnamemap.get(rdata.getIP()));
					map.put("IP", rdata.getIP());
					map.put("category", stamap.get(rdata.getCategory()));
					map.put("name", stamap.get(rdata.getName()));
					map.put("state", rdata.getState());
					data.add(map);
				}
			}
	    	
			ExcelUtil.export("机箱信息", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	 
	
	//FRU板信息
	
	 // FRUboard页面
	@RequestMapping("/fruboardinfo")
	@MethodLog(remark = "进入板信息页面",openType = "3")
	public String fruboardinfo(HttpServletRequest request) {
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "fruboardinfo");
		return "/WEB-INF/fru/fruboardinfo";
	}
	
	//处理FRUboard数据
	public List<fruList> fruboarddata(List<fruInfo2> fruInfo2){
		List<fruList> info = new ArrayList<fruList>();
		for (int i = 0; i < fruInfo2.size(); i++) {
			if(fruInfo2.get(i).getName().equals("BoardInfo")) {
				fruList fruList4 = new fruList();
				fruList4.setStart_time(fruInfo2.get(i).getStart_time());
				fruList4.setIP(fruInfo2.get(i).getIP());
				fruList4.setCategory(fruInfo2.get(i).getName());
				fruList4.setName("product_name");
				fruList4.setState(fruInfo2.get(i).getProduct_name());
				fruList fruList5 = new fruList();
				fruList5.setStart_time(fruInfo2.get(i).getStart_time());
				fruList5.setIP(fruInfo2.get(i).getIP());
				fruList5.setCategory(fruInfo2.get(i).getName());
				fruList5.setName("mfg_date");
				fruList5.setState(fruInfo2.get(i).getMfg_date().toString());
				fruList fruList6 = new fruList();
				fruList6.setStart_time(fruInfo2.get(i).getStart_time());
				fruList6.setIP(fruInfo2.get(i).getIP());
				fruList6.setCategory(fruInfo2.get(i).getName());
				fruList6.setName("manufacturer");
				fruList6.setState(fruInfo2.get(i).getManufacturer());
				fruList fruList7 = new fruList();
				fruList7.setStart_time(fruInfo2.get(i).getStart_time());
				fruList7.setIP(fruInfo2.get(i).getIP());
				fruList7.setCategory(fruInfo2.get(i).getName());
				fruList7.setName("serial_number");
				fruList7.setState(fruInfo2.get(i).getSerial_number());
				fruList fruList8 = new fruList();
				fruList8.setStart_time(fruInfo2.get(i).getStart_time());
				fruList8.setIP(fruInfo2.get(i).getIP());
				fruList8.setCategory(fruInfo2.get(i).getName());
				fruList8.setName("part_number");
				fruList8.setState(fruInfo2.get(i).getPart_number());
				fruList fruList9 = new fruList();
				fruList9.setStart_time(fruInfo2.get(i).getStart_time());
				fruList9.setIP(fruInfo2.get(i).getIP());
				fruList9.setCategory(fruInfo2.get(i).getName());
				fruList9.setName("custom_info");
				fruList9.setState(fruInfo2.get(i).getCustom_info());
				info.add(fruList4);
				info.add(fruList5);
				info.add(fruList6);
				info.add(fruList7);
				info.add(fruList8);
				info.add(fruList9);
			}
		}
		return info;
	}
	
	//选择后的FRUboard页面
	@RequestMapping("/fruboardquery")
	@MethodLog(remark = "查询板信息",openType = "3")
	public String fruboardquery(HttpServletRequest request,
			String datemin, String datemax, String nameType, String keyword,String IPType){
		HashMap<String, String> map = englishImpl.getmap();
		List<fruInfo2> fruInfo2 = getFruInfo2List(IPType,datemin,datemax,nameType);
		List<fruList> infolist = new ArrayList<fruList>();
		infolist = fruboarddata(fruInfo2);
		List<fruList> info = new ArrayList<fruList>();
		info = getinfobyname(infolist,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("info",info);
		request.setAttribute("menu", "fruboardinfo");
	    return "/WEB-INF/fru/fruboardDiv";
	}
	
	/**
	 * 导出FRUboard数据
	 */
	@RequestMapping("/exprotFRUboardList")
	@MethodLog(remark = "导出板信息",openType = "3")
	public void exprotFRUboardList(HttpServletRequest request,
			String datemin, String datemax, String nameType,String IPType,
			HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap();
		//int width = 256*14;
		String[] excelHeader = { "时间", "服务器用途", "服务器ip地址", "FRU类别", "FRU名字", "FRU状态"};
		String[] ds_titles = { "start_time", "sysname", "IP", "category", "name","state"};
		int[] widths = {256*25, 256*25, 256*25, 256*25, 256*25, 256*25, 256*25};
		int[] ds_format = { 1,2,2,2,2,2,2};
		try{
			List<fruInfo2> fruInfo2 = getFruInfo2List(IPType,datemin,datemax,nameType);
			List<fruList> infolist = new ArrayList<fruList>();
			infolist = fruboarddata(fruInfo2);
			List<fruList> info = new ArrayList<fruList>();
			info = getinfobyname(infolist,nameType);
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,String> sysnamemap = sensorImpl.getSysname();
			if(info != null && info.size() > 0){
				for(fruList rdata : info){
					Map<String,Object> map = new HashMap<String,Object>();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("start_time", format.format(rdata.getStart_time()));
					map.put("sysname", sysnamemap.get(rdata.getIP()));
					map.put("IP", rdata.getIP());
					map.put("category", stamap.get(rdata.getCategory()));
					map.put("name", stamap.get(rdata.getName()));
					map.put("state", rdata.getState());
					data.add(map);
				}
			}
	    	
			ExcelUtil.export("FRU板数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//product
	
	 // FRUproduct页面
	@RequestMapping("/fruproductinfo")
	@MethodLog(remark = "进入产品信息页面",openType = "3")
	public String fruproductinfo(HttpServletRequest request) {
		List<serversip> serversip = null;
		serversip = sensorImpl.getServersip();
		request.setAttribute("serversip", serversip);
		request.setAttribute("menu", "fruproductinfo");
		return "/WEB-INF/fru/fruproductinfo";
	}
	
	//处理FRUproduct数据
	public List<fruList> fruproductdata(List<fruInfo2> fruInfo2){
		List<fruList> info = new ArrayList<fruList>();
		for (int i = 0; i < fruInfo2.size(); i++) {
			if(fruInfo2.get(i).getName().equals("ProductInfo")) {
				fruList fruList10 = new fruList();
				fruList10.setStart_time(fruInfo2.get(i).getStart_time());
				fruList10.setIP(fruInfo2.get(i).getIP());
				fruList10.setCategory(fruInfo2.get(i).getName());
				fruList10.setName("product_name");
				fruList10.setState(fruInfo2.get(i).getProduct_name());
				fruList fruList11 = new fruList();
				fruList11.setStart_time(fruInfo2.get(i).getStart_time());
				fruList11.setIP(fruInfo2.get(i).getIP());
				fruList11.setCategory(fruInfo2.get(i).getName());
				fruList11.setName("manufacturer");
				fruList11.setState(fruInfo2.get(i).getManufacturer());
				fruList fruList12 = new fruList();
				fruList12.setStart_time(fruInfo2.get(i).getStart_time());
				fruList12.setIP(fruInfo2.get(i).getIP());
				fruList12.setCategory(fruInfo2.get(i).getName());
				fruList12.setName("serial_number");
				fruList12.setState(fruInfo2.get(i).getSerial_number());
				fruList fruList13 = new fruList();
				fruList13.setStart_time(fruInfo2.get(i).getStart_time());
				fruList13.setIP(fruInfo2.get(i).getIP());
				fruList13.setCategory(fruInfo2.get(i).getName());
				fruList13.setName("custom_info");
				fruList13.setState(fruInfo2.get(i).getCustom_info());
				fruList fruList14 = new fruList();
				fruList14.setStart_time(fruInfo2.get(i).getStart_time());
				fruList14.setIP(fruInfo2.get(i).getIP());
				fruList14.setCategory(fruInfo2.get(i).getName());
				fruList14.setName("model_number");
				fruList14.setState(fruInfo2.get(i).getModel_number());
				info.add(fruList10);
				info.add(fruList11);
				info.add(fruList12);
				info.add(fruList13);
				info.add(fruList14);
			}
		}
		return info;
	}
	
	//选择后的FRUproduct页面
	@RequestMapping("/fruproductquery")
	@MethodLog(remark = "查询产品信息",openType = "3")
	public String fruproductquery(HttpServletRequest request,
			String datemin, String datemax, String nameType, String keyword,String IPType){
		HashMap<String, String> map = englishImpl.getmap();
		List<fruInfo2> fruInfo2 = getFruInfo2List(IPType,datemin,datemax,nameType);
		List<fruList> infolist = new ArrayList<fruList>();
		infolist = fruproductdata(fruInfo2);
		List<fruList> info = new ArrayList<fruList>();
		info = getinfobyname(infolist,nameType);
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		request.setAttribute("map", map);
		request.setAttribute("sysnamemap", sysnamemap);
		request.setAttribute("info",info);
		request.setAttribute("menu", "fruproductinfo");
	    return "/WEB-INF/fru/fruproductDiv";
	}
	
	/**
	 * 导出FRUproduct数据
	 */
	@RequestMapping("/exprotFRUproductList")
	@MethodLog(remark = "导出产品信息",openType = "3")
	public void exprotFRUproductList(HttpServletRequest request,
			String datemin, String datemax, String nameType,String IPType,
			HttpServletResponse response){
		HashMap<String, String> stamap = englishImpl.getmap();
		//int width = 256*14;
		String[] excelHeader = { "时间", "服务器用途", "服务器ip地址", "FRU类别", "FRU名字", "FRU状态"};
		String[] ds_titles = { "start_time", "sysname", "IP", "category", "name","state"};
		int[] widths = {256*25, 256*25, 256*25,256*25, 256*25, 256*25, 256*25};
		int[] ds_format = { 1,2,2,2,2,2,2};
		try{
			List<fruInfo2> fruInfo2 = getFruInfo2List(IPType,datemin,datemax,nameType);
			List<fruList> infolist = new ArrayList<fruList>();
			infolist = fruproductdata(fruInfo2);
			List<fruList> info = new ArrayList<fruList>();
			info = getinfobyname(infolist,nameType);
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String,String> sysnamemap = sensorImpl.getSysname();
			if(info != null && info.size() > 0){
				for(fruList rdata : info){
					Map<String,Object> map = new HashMap<String,Object>();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("start_time", format.format(rdata.getStart_time()));
					map.put("sysname", sysnamemap.get(rdata.getIP()));
					map.put("IP", rdata.getIP());
					map.put("category", stamap.get(rdata.getCategory()));
					map.put("name", stamap.get(rdata.getName()));
					map.put("state", rdata.getState());
					data.add(map);
				}
			}
	    	
			ExcelUtil.export("FRU产品数据", "数据列表", excelHeader, ds_titles, ds_format, widths, data, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
