/*
 * SelRecord.java 
 * Created on 2011-08-11
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.gydz.user.model;

import java.util.Date;

import com.veraxsystems.vxipmi.coding.commands.sdr.record.FullSensorRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.ReadingType;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.SensorType;
import com.veraxsystems.vxipmi.common.TypeConverter;

public class selrecord {
	
	private Date start_time;
	private String IP;
	private int recordId;
	private String recordType;
	private String timestamp;
	private String sensorType;
	private int sensorNumber;
	private String eventDirection;
	private String event;
	private int reading;
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
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSensorType() {
		return sensorType;
	}
	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}
	public int getSensorNumber() {
		return sensorNumber;
	}
	public void setSensorNumber(int sensorNumber) {
		this.sensorNumber = sensorNumber;
	}
	public String getEventDirection() {
		return eventDirection;
	}
	public void setEventDirection(String eventDirection) {
		this.eventDirection = eventDirection;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public int getReading() {
		return reading;
	}
	public void setReading(int reading) {
		this.reading = reading;
	}
	@Override
	public String toString() {
		return "selrecord [start_time=" + start_time + ", IP=" + IP + ", recordId=" + recordId + ", recordType="
				+ recordType + ", timestamp=" + timestamp + ", sensorType=" + sensorType + ", sensorNumber="
				+ sensorNumber + ", eventDirection=" + eventDirection + ", event=" + event + ", reading=" + reading
				+ "]";
	}

}
