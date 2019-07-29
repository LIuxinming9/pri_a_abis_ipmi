package com.gydz.user.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * RRemote ʵ����
 * 
 * @date 2018-11-11 15:21:33
 * @version 1.0
 */
@XmlRootElement
public class ipmiwarnmodel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date start_time;
	private String IP;
	private String name;
	private String entity_id;
	private String sensor_type;
	private String record_type;
	private double current_num;
	private String state;
	private String sensor_base_unit;
	private String entity_physical;
	private String sensor_state_valid;
	private String statesAsserted;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	public String getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(String sensor_type) {
		this.sensor_type = sensor_type;
	}
	public String getRecord_type() {
		return record_type;
	}
	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}
	public double getCurrent_num() {
		return current_num;
	}
	public void setCurrent_num(double current_num) {
		this.current_num = current_num;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSensor_base_unit() {
		return sensor_base_unit;
	}
	public void setSensor_base_unit(String sensor_base_unit) {
		this.sensor_base_unit = sensor_base_unit;
	}
	public String getEntity_physical() {
		return entity_physical;
	}
	public void setEntity_physical(String entity_physical) {
		this.entity_physical = entity_physical;
	}
	public String getSensor_state_valid() {
		return sensor_state_valid;
	}
	public void setSensor_state_valid(String sensor_state_valid) {
		this.sensor_state_valid = sensor_state_valid;
	}
	public String getStatesAsserted() {
		return statesAsserted;
	}
	public void setStatesAsserted(String statesAsserted) {
		this.statesAsserted = statesAsserted;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ipmiwarnmodel [start_time=" + start_time + ", IP=" + IP + ", name=" + name + ", entity_id=" + entity_id
				+ ", sensor_type=" + sensor_type + ", record_type=" + record_type + ", current_num=" + current_num
				+ ", state=" + state + ", sensor_base_unit=" + sensor_base_unit + ", entity_physical=" + entity_physical
				+ ", sensor_state_valid=" + sensor_state_valid + ", statesAsserted=" + statesAsserted + "]";
	}
	

}