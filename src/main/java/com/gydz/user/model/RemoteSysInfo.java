package com.gydz.user.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RemoteSysInfo {

	private String ip;
	
	private String hostName;
	
	private String osName;
	
	private int cpuSize;
	
	private Double CPUUsedRate;
	
	private int fileSize;
	
	private Double memTotal;
	
	private Double memUsed;
	
	private Double memFree;
	
	private Double memUsedRate;
	
	private Double fileTotal;
	
	private Double fileUsed;
	
	private Double fileFree;
	
	private Double fileUsedRate;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public int getCpuSize() {
		return cpuSize;
	}

	public void setCpuSize(int cpuSize) {
		this.cpuSize = cpuSize;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public Double getMemTotal() {
		return memTotal;
	}

	public void setMemTotal(Double memTotal) {
		this.memTotal = memTotal;
	}

	public Double getMemUsed() {
		return memUsed;
	}

	public void setMemUsed(Double memUsed) {
		this.memUsed = memUsed;
	}

	public Double getMemFree() {
		return memFree;
	}

	public void setMemFree(Double memFree) {
		this.memFree = memFree;
	}

	public Double getFileTotal() {
		return fileTotal;
	}

	public void setFileTotal(Double fileTotal) {
		this.fileTotal = fileTotal;
	}

	public Double getFileUsed() {
		return fileUsed;
	}

	public void setFileUsed(Double fileUsed) {
		this.fileUsed = fileUsed;
	}

	public Double getFileFree() {
		return fileFree;
	}

	public void setFileFree(Double fileFree) {
		this.fileFree = fileFree;
	}

	public Double getMemUsedRate() {
		return memUsedRate;
	}

	public void setMemUsedRate(Double memUsedRate) {
		this.memUsedRate = memUsedRate;
	}

	public Double getFileUsedRate() {
		return fileUsedRate;
	}

	public void setFileUsedRate(Double fileUsedRate) {
		this.fileUsedRate = fileUsedRate;
	}

	public Double getCPUUsedRate() {
		return CPUUsedRate;
	}

	public void setCPUUsedRate(Double cPUUsedRate) {
		CPUUsedRate = cPUUsedRate;
	}
	
}
