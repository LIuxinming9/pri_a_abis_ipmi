package com.gydz.user.model;

import java.util.Date;

public class fruInfo {

	private String name;
	private String productName;
	private Date mfgDate;
	private String manufacturer;
	private String serialNumber;
	private String partNumber;
	private String customInfo;
	private String modelNumber;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getMfgDate() {
		return mfgDate;
	}
	public void setMfgDate(Date mfgDate) {
		this.mfgDate = mfgDate;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getCustomInfo() {
		return customInfo;
	}
	public void setCustomInfo(String customInfo) {
		this.customInfo = customInfo;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	@Override
	public String toString() {
		return "fruInfo [name=" + name + ", productName=" + productName + ", mfgDate=" + mfgDate + ", manufacturer="
				+ manufacturer + ", serialNumber=" + serialNumber + ", partNumber=" + partNumber + ", customInfo="
				+ customInfo + ", modelNumber=" + modelNumber + "]";
	}
}
