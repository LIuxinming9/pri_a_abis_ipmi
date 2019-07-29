package com.gydz.user.model;

import java.util.Date;

public class fruList {

	private Date start_time;
	private String IP;
	private String category;
	private String name;
	private String state;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "fruList [start_time=" + start_time + ", IP=" + IP + ", category=" + category + ", name=" + name
				+ ", state=" + state + "]";
	}
	
}
