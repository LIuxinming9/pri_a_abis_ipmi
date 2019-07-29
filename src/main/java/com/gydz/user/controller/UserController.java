package com.gydz.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.model.OperationLog;
import com.gydz.user.model.QueryParam;
import com.gydz.user.model.User;
import com.gydz.user.service.OperLogServiceImpl;
import com.gydz.user.service.UserServiceImpl;
import com.gydz.util.EncryptUtil;
import com.sun.jna.Platform;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private UserServiceImpl userServiceImpl;
    
    @Resource
    private OperLogServiceImpl operLogServiceImpl;
    
    @RequestMapping("/login")
    @MethodLog(remark = "登录系统",openType = "5")
    public String login(HttpServletRequest request){
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	
        Subject subject = SecurityUtils.getSubject();
        User user = userServiceImpl.getByUsername(username); 
        UsernamePasswordToken token = new UsernamePasswordToken(username ,password);
        
        try {
            subject.login(token);
        	HashMap<Long,List<String>> upMap = new HashMap<Long,List<String>>();
        	HashMap<Long,List<String>> downMap = new HashMap<Long,List<String>>();
        	QueryParam param = new QueryParam();
    		param.setRouteNo(1);
        	request.setAttribute("upMap",upMap);
        	request.setAttribute("downMap",downMap);
            request.getSession().setAttribute("user",user);
            request.setAttribute("menu","index");
            return "redirect:/warncurrent/warncurrentall";
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("user",user);
            request.setAttribute("error","用户名或密码错误，请重新输入...");
            return "login";
        }
    }
    
    @RequestMapping("/list")
    @MethodLog(remark = "查询用户列表",openType = "3")
    public String list(HttpServletRequest request, String keyword){
    	System.out.println("keyword:"+keyword+"|"+request.getParameter("keyword"));
    	List<User> list = null;
    	if(StringUtils.isBlank(keyword)){
    		list = userServiceImpl.getAllUsers(); 
    	}else{
    		list = userServiceImpl.getUserByKeyWord(keyword);
    	}
    	request.getSession().setAttribute("list",list);
    	request.setAttribute("menu","user");
    	request.setAttribute("keyword",keyword);
        System.out.println("list.size:"+list.size()); 
        return "/WEB-INF/user/list";
    }
    
    @RequestMapping("/add")
    public String add(HttpServletRequest request){
    	request.setAttribute("menu","user"); 
        return "/WEB-INF/user/add";
    }
    
    @ResponseBody
    @RequestMapping(value = "/isExist",method = RequestMethod.POST) 
    public String isExist(Model model,String account) {
    	
    	User user = userServiceImpl.getByUsername(account);
    	System.out.println("account["+account +"]isExist?"+(user != null));
    	
    	if(user == null){
    		return "success";
    	}else{
    		return "error";
    	}
    }
    
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @MethodLog(remark = "添加用户",openType = "0")
    public String save(@ModelAttribute("user") User user, HttpServletRequest request, Model model){
        try {
        	userServiceImpl.addUser(user);
        	int rid = userServiceImpl.getroleid(user);
        	User userid = userServiceImpl.getByUsername(user.getUsername());
        	userServiceImpl.addRole(rid,userid.getId());
        }catch (Exception ex){
            model.addAttribute("error","保存用户失败！");
        }
        return "redirect:/user/list";
    }
    
    @RequestMapping("/uPwd")
    public String updatePwd(HttpServletRequest request){
    	request.setAttribute("menu","index"); 
        return "/WEB-INF/user/uPwd";
    }
    
    @ResponseBody
    @RequestMapping(value = "/isOpwd",method = RequestMethod.POST) 
    public String isOpwd(Model model,HttpServletRequest request,String oPwd) {
    	System.out.println("oPwd:"+oPwd);
    	User user = (User)request.getSession().getAttribute("user");
    	if(user != null){
	    	String enOpwd = EncryptUtil.EncryptPwd(oPwd);System.out.println(enOpwd);
			if(StringUtils.equals(enOpwd, user.getPassword())){
	    		return "success";
	    	}else{
	    		return "error";
	    	}
    	}
    	return "error";
    }
    
    @RequestMapping(value = "/savePwd",method = RequestMethod.POST)
    @MethodLog(remark = "修改密码",openType = "2")
    public String savePwd(HttpServletRequest request, Model model){
    	System.out.println("password:"+request.getParameter("password"));
        try {
        	User user = (User)request.getSession().getAttribute("user");
        	String pwd = request.getParameter("password");
        	if(user != null && StringUtils.isNotBlank(pwd)){
        		userServiceImpl.updateUserPwd(user.getId(), pwd);
        	}else{
        		return "redirect:/uPwd";
        	}
        }catch (Exception ex){
            model.addAttribute("error","修改密码失败！");
        }
        return "redirect:/user/logout";
    }

    @RequiresPermissions(value={"delete"})
    @RequestMapping(value = "/del/{id}")
    @MethodLog(remark = "删除用户",openType = "1")
    public String del(@PathVariable String id, Model model){
        try {
        	userServiceImpl.deleteUser(id);
        	userServiceImpl.deleteRole(id);
        }catch (Exception ex){
            model.addAttribute("error","删除用户失败！");
        }
        return "redirect:/user/list";
    }

    @RequestMapping("/logout")
    @MethodLog(remark = "退出系统",openType = "6")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "login";
    }
    
    @RequestMapping(value="/log")
    @MethodLog(remark = "查询系统日志",openType = "3")
    public String log(HttpServletRequest request,
    		String datemin, String datemax, String keyword){
    	System.out.println("keyword:"+keyword+"|datemin:"+datemin+"|datemax:"+datemax);
    	if(StringUtils.isBlank(datemax) && StringUtils.isBlank(datemin)){
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	Date nowDate = new Date();
        	if(!Platform.isWindows()){
            	TimeZone timeZone = TimeZone.getTimeZone("CST");
        		format.setTimeZone(timeZone);
        	}
        	datemin = format.format(nowDate)+" 00:00:00";
        	datemax = format.format(nowDate)+" 23:59:59";
    	}
    	List<OperationLog> list = null;
    	QueryParam param = new QueryParam();
		param.setKeyword(keyword);
		param.setDatemin(datemin);
		param.setDatemax(datemax);
		list = operLogServiceImpl.getOperLogByKeyWord(param);
    	request.getSession().setAttribute("list",list);
    	request.setAttribute("menu","log");
    	request.setAttribute("keyword",keyword);
    	request.setAttribute("datemin",datemin);
    	request.setAttribute("datemax",datemax);
        System.out.println("list.size:"+list.size()); 
        return "/WEB-INF/log/list";
    }

}
