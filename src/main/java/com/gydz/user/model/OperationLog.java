package com.gydz.user.model;

import java.util.Date;

public class OperationLog {

	private Integer  id;
	
    private Date time;
	
	private Integer  uid;
    
    private String account;
    
    private String ip;
    
    private String logtype;
    
    private String logdesc;
    
    @Override
	public String toString() {
		return "Log [id=" + id + ", time=" + time + ", uid=" + uid + ", account="
				+ account + ", ip=" + ip + ", logtype=" + logtype+ ", logdesc=" + logdesc + "]";
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLogtype() {
		return logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}

	public String getLogdesc() {
		return logdesc;
	}

	public void setLogdesc(String logdesc) {
		this.logdesc = logdesc;
	}
    
}
