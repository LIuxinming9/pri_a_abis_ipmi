package com.gydz.user.model;

public class serversip {

	private int id;
	private String name;
	private String hostname;
	private String username;
	private String password;
	private String bmcKey;
	private String ip;
	private String community;
	private boolean power;
	private int is_online;
	private int snmp_online;
	private String brand;
	private String location;
	private String note;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBmcKey() {
		return bmcKey;
	}
	public void setBmcKey(String bmcKey) {
		this.bmcKey = bmcKey;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public boolean isPower() {
		return power;
	}
	public void setPower(boolean power) {
		this.power = power;
	}
	public int getIs_online() {
		return is_online;
	}
	public void setIs_online(int is_online) {
		this.is_online = is_online;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString() {
		return "serversip [id=" + id + ", name=" + name + ", hostname=" + hostname + ", username=" + username
				+ ", password=" + password + ", bmcKey=" + bmcKey + ", ip=" + ip + ", community=" + community
				+ ", power=" + power + ", is_online=" + is_online + ", brand=" + brand + ", location=" + location
				+ ", note=" + note + "]";
	}
	public int getSnmp_online() {
		return snmp_online;
	}
	public void setSnmp_online(int snmp_online) {
		this.snmp_online = snmp_online;
	}
	
}
