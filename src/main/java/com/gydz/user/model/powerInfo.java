package com.gydz.user.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class powerInfo {

	private Integer  id;
	
    private String serviceName;
    
    private String serviceIP;
    
    private String serviceManufacturer;
    
    private String serviceLocation;
    
    private String productInfoProductName;
    
    private String boardInfoMfgDate;
    
    private String productInfoSerialNumber;
    
    private String boardInfoSerialNumber;
    
    private String productInfoModelNumber;
    
    private String boardInfoPartNumber;

    private String warnCurrentCategory;
    
    private String warnCurrentLevel;
    
    private String warnCurrentSum;
    
    private String warnHistorySum;
    
    private String isWarn;
    
    private String isStart;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceIP() {
		return serviceIP;
	}

	public void setServiceIP(String serviceIP) {
		this.serviceIP = serviceIP;
	}

	public String getServiceManufacturer() {
		return serviceManufacturer;
	}

	public void setServiceManufacturer(String serviceManufacturer) {
		this.serviceManufacturer = serviceManufacturer;
	}

	public String getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public String getProductInfoProductName() {
		return productInfoProductName;
	}

	public void setProductInfoProductName(String productInfoProductName) {
		this.productInfoProductName = productInfoProductName;
	}

	public String getBoardInfoMfgDate() {
		return boardInfoMfgDate;
	}

	public void setBoardInfoMfgDate(String boardInfoMfgDate) {
		this.boardInfoMfgDate = boardInfoMfgDate;
	}

	public String getProductInfoSerialNumber() {
		return productInfoSerialNumber;
	}

	public void setProductInfoSerialNumber(String productInfoSerialNumber) {
		this.productInfoSerialNumber = productInfoSerialNumber;
	}

	public String getBoardInfoSerialNumber() {
		return boardInfoSerialNumber;
	}

	public void setBoardInfoSerialNumber(String boardInfoSerialNumber) {
		this.boardInfoSerialNumber = boardInfoSerialNumber;
	}

	public String getProductInfoModelNumber() {
		return productInfoModelNumber;
	}

	public void setProductInfoModelNumber(String productInfoModelNumber) {
		this.productInfoModelNumber = productInfoModelNumber;
	}

	public String getBoardInfoPartNumber() {
		return boardInfoPartNumber;
	}

	public void setBoardInfoPartNumber(String boardInfoPartNumber) {
		this.boardInfoPartNumber = boardInfoPartNumber;
	}

	public String getWarnCurrentCategory() {
		return warnCurrentCategory;
	}

	public void setWarnCurrentCategory(String warnCurrentCategory) {
		this.warnCurrentCategory = warnCurrentCategory;
	}

	public String getWarnCurrentLevel() {
		return warnCurrentLevel;
	}

	public void setWarnCurrentLevel(String warnCurrentLevel) {
		this.warnCurrentLevel = warnCurrentLevel;
	}

	public String getWarnCurrentSum() {
		return warnCurrentSum;
	}

	public void setWarnCurrentSum(String warnCurrentSum) {
		this.warnCurrentSum = warnCurrentSum;
	}

	public String getWarnHistorySum() {
		return warnHistorySum;
	}

	public void setWarnHistorySum(String warnHistorySum) {
		this.warnHistorySum = warnHistorySum;
	}

	public String getIsWarn() {
		return isWarn;
	}

	public void setIsWarn(String isWarn) {
		this.isWarn = isWarn;
	}

	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}

	@Override
	public String toString() {
		return "powerInfo [id=" + id + ", serviceName=" + serviceName + ", serviceIP=" + serviceIP
				+ ", serviceManufacturer=" + serviceManufacturer + ", serviceLocation=" + serviceLocation
				+ ", productInfoProductName=" + productInfoProductName + ", boardInfoMfgDate=" + boardInfoMfgDate
				+ ", productInfoSerialNumber=" + productInfoSerialNumber + ", boardInfoSerialNumber="
				+ boardInfoSerialNumber + ", productInfoModelNumber=" + productInfoModelNumber
				+ ", boardInfoPartNumber=" + boardInfoPartNumber + ", warnCurrentCategory=" + warnCurrentCategory
				+ ", warnCurrentLevel=" + warnCurrentLevel + ", warnCurrentSum=" + warnCurrentSum + ", warnHistorySum="
				+ warnHistorySum + ", isWarn=" + isWarn + ", isStart=" + isStart + "]";
	}
    
}
