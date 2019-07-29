package com.gydz.user.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QueryParam {
	
	private String keyword;
	
	private String datemin;
	
	private String datemax;
	
	private String queryType;
	
	private String IPType;
	
	private String desType;
	
	private String nameType;
	
	private String entity_id;
	
	private int routeNo;
	
	private int warn_state;
	
	private String end_time;
	
	private String brand;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDatemin() {
		return datemin;
	}

	public void setDatemin(String datemin) {
		this.datemin = datemin;
	}

	public String getDatemax() {
		return datemax;
	}

	public void setDatemax(String datemax) {
		this.datemax = datemax;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getIPType() {
		return IPType;
	}

	public void setIPType(String iPType) {
		IPType = iPType;
	}

	public String getDesType() {
		return desType;
	}

	public void setDesType(String desType) {
		this.desType = desType;
	}

	public String getNameType() {
		return nameType;
	}

	public void setNameType(String nameType) {
		this.nameType = nameType;
	}

	public String getEntity_id() {
		return entity_id;
	}

	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	public int getRouteNo() {
		return routeNo;
	}

	public void setRouteNo(int routeNo) {
		this.routeNo = routeNo;
	}

	public int getWarn_state() {
		return warn_state;
	}

	public void setWarn_state(int warn_state) {
		this.warn_state = warn_state;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "QueryParam [keyword=" + keyword + ", datemin=" + datemin + ", datemax=" + datemax + ", queryType="
				+ queryType + ", IPType=" + IPType + ", desType=" + desType + ", nameType=" + nameType + ", entity_id="
				+ entity_id + ", routeNo=" + routeNo + ", warn_state=" + warn_state + ", end_time=" + end_time
				+ ", brand=" + brand + "]";
	}
}
