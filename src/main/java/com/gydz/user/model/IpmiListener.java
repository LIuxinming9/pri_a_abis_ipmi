package com.gydz.user.model;

import java.util.Date;

public class IpmiListener {
	
	
	public Date end_time;
	public String sensor_time;
	public String program_time;
	public String thread;
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getSensor_time() {
		return sensor_time;
	}
	public void setSensor_time(String sensor_time) {
		this.sensor_time = sensor_time;
	}
	public String getProgram_time() {
		return program_time;
	}
	public void setProgram_time(String program_time) {
		this.program_time = program_time;
	}
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	@Override
	public String toString() {
		return "IpmiListener [end_time=" + end_time + ", sensor_time=" + sensor_time + ", program_time=" + program_time
				+ ", thread=" + thread + "]";
	}
	
}
