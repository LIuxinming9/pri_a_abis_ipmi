package com.gydz.user.controller;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gydz.user.mapper.MethodLog;
import com.gydz.user.mapper.OperationLogMapper;
import com.gydz.user.model.OperationLog;
import com.gydz.user.model.User;

@Component
@Aspect
public class SystemOperLogs {
	
	@Resource
    private OperationLogMapper operLogMapper;
	
	//声明AOP切入点
    @Pointcut("@annotation(com.gydz.user.mapper.MethodLog)")
    public void log() {}
 
    @After("log()")
    public void after(JoinPoint point) throws Throwable {
    	MethodLog methodLog = getMethodRemark(point);
        if (methodLog != null) {
            HttpServletRequest request = this.getRequest(point);
            this.insertLog(request, methodLog);
        }
    }
 
 
    /**
     * 获取方法的中文备注____用于记录用户的操作日志描述
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private MethodLog getMethodRemark(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
 
        Class targetClass = Class.forName(targetName);
        Method[] method = targetClass.getMethods();
        for (Method m : method) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    MethodLog methodCache = m.getAnnotation(MethodLog.class);
                    if (methodCache != null && !("").equals(methodCache.remark())) {
                        return methodCache;
                    }
                    break;
                }
            }
        }
        return null;
    }
 
    /**
     * 获取参数request
     *
     * @param point
     * @return
     */
    private HttpServletRequest getRequest(JoinPoint point) {
        Object[] args = point.getArgs();
        for (Object obj : args) {
            if (obj instanceof HttpServletRequest)
                return (HttpServletRequest) obj;
        }
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }
    
    /**
     * 获取IP
     * @param request
     * @return
     */
    private String getRequestIP(HttpServletRequest request) {
    	String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        if(StringUtils.equalsIgnoreCase(remoteAddr, "0:0:0:0:0:0:0:1")){
        	remoteAddr = "127.0.0.1";
        }
        return remoteAddr;
    }
    
    private void insertLog(HttpServletRequest request, MethodLog methodLog) {
        OperationLog sysLog = new OperationLog();
        User user = ((User)request.getSession().getAttribute("user"));
        sysLog.setTime(new Date());
        if(user != null){
        	sysLog.setUid(user.getId());
        	sysLog.setAccount(user.getAccount());
        }
        sysLog.setIp(this.getRequestIP(request));
		sysLog.setLogtype(methodLog.openType()); 
		sysLog.setLogdesc(methodLog.remark()); 
		operLogMapper.addOperLog(sysLog); 
	 }


}
