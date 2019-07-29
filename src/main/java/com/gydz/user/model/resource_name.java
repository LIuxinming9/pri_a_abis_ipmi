package com.gydz.user.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class resource_name {

	private Date start_time;
	private String IP;
	private String name;
	private String des;
	private int cardnum;
	private int hardnum;
	private int emptynum;
	private String entity_id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public int getCardnum() {
		return cardnum;
	}
	public void setCardnum(int cardnum) {
		this.cardnum = cardnum;
	}
	public int getHardnum() {
		return hardnum;
	}
	public void setHardnum(int hardnum) {
		this.hardnum = hardnum;
	}
	public int getEmptynum() {
		return emptynum;
	}
	public void setEmptynum(int emptynum) {
		this.emptynum = emptynum;
	}
	@Override
	public String toString() {
		return "resource_name [name=" + name + ", des=" + des + ", cardnum=" + cardnum + ", hardnum=" + hardnum
				+ ", emptynum=" + emptynum + "]";
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	
	
}
