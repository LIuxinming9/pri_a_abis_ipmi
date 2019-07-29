package com.gydz.user.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;

@XmlRootElement
public class fruInfo2 {
	
	private Date start_time;
	private String IP;
	private String name;
	private String product_name;
	private String mfg_date;
	private String manufacturer;
	private String serial_number;
	private String part_number;
	private String custom_info;
	private String model_number;
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
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	public String getMfg_date() {
		return mfg_date;
	}
	public void setMfg_date(String mfg_date) {
		this.mfg_date = mfg_date;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getPart_number() {
		return part_number;
	}
	public void setPart_number(String part_number) {
		this.part_number = part_number;
	}
	public String getCustom_info() {
		return custom_info;
	}
	public void setCustom_info(String custom_info) {
		this.custom_info = custom_info;
	}
	public String getModel_number() {
		return model_number;
	}
	public void setModel_number(String model_number) {
		this.model_number = model_number;
	}
	@Override
	public String toString() {
		return "fruInfo2 [start_time=" + start_time + ", IP=" + IP + ", name=" + name + ", product_name=" + product_name
				+ ", mfg_date=" + mfg_date + ", manufacturer=" + manufacturer + ", serial_number=" + serial_number
				+ ", part_number=" + part_number + ", custom_info=" + custom_info + ", model_number=" + model_number
				+ "]";
	}
	
}
