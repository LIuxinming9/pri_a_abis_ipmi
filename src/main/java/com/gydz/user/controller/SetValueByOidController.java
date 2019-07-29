package com.gydz.user.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.snmp;
import com.gydz.user.service.sensorImpl;
import com.gydz.util.snmpUtil;

@Controller
@RequestMapping("/setValueByOid")
public class SetValueByOidController {
	
	@Resource
	private sensorImpl sensorImpl;
	
	 // 服务器信息页面
	@RequestMapping("/list")
	@MethodLog(remark = "打开SNMP-SET页面",openType = "3")
	public String serviceipinfo(HttpServletRequest request) throws Exception {
		request.setAttribute("menu", "setValueByOid");
		return "/WEB-INF/snmp/setValueByOid";
	}
	
	@RequestMapping(value = "/setValueByOid",method = RequestMethod.POST)
    @MethodLog(remark = "添加OID",openType = "0")
    public String saveByBrand(@ModelAttribute("snmp") snmp snmp, HttpServletRequest request, Model model){
		Map<String,String> sysnamemap = sensorImpl.getSysname();
		snmpUtil snmpUtil = new snmpUtil();
		String hostname = snmp.getHostname();
		try {
			if(hostname.startsWith("1")){
				snmpUtil.initComm(hostname);
			}else {
				snmpUtil.initComm(sysnamemap.get(hostname));
			}
			
			snmpUtil.setPDU(snmp.getOid(), snmp.getType(), snmp.getValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("menu", "setValueByOid");
        return "redirect:/setValueByOid/list";
    }
}
