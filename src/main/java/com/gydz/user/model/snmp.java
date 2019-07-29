package com.gydz.user.model;

import java.util.Date;

public class snmp {
	
	private int id;
	
	private int iid;
	
	private int ooid;
	
	private String hostname;

	private String community;
	
	private String oid;
	
	private String way;
	
	private String english_des;
	
	private String chinese_des;
	
	private String value;
	
	private String type;
	
	private Date start_time;
	
	private String name;
	
	private String brand;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
		this.iid = iid;
	}

	public int getOoid() {
		return ooid;
	}

	public void setOoid(int ooid) {
		this.ooid = ooid;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getEnglish_des() {
		return english_des;
	}

	public void setEnglish_des(String english_des) {
		this.english_des = english_des;
	}

	public String getChinese_des() {
		return chinese_des;
	}

	public void setChinese_des(String chinese_des) {
		this.chinese_des = chinese_des;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "snmp [id=" + id + ", iid=" + iid + ", ooid=" + ooid + ", hostname=" + hostname + ", community="
				+ community + ", oid=" + oid + ", way=" + way + ", english_des=" + english_des + ", chinese_des="
				+ chinese_des + ", value=" + value + ", type=" + type + ", start_time=" + start_time + ", name=" + name
				+ ", brand=" + brand + "]";
	}
	
}
