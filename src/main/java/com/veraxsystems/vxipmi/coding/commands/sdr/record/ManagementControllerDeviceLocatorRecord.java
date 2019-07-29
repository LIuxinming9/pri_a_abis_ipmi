/*
 * ManagementControllerDeviceLocatorRecord.java 
 * Created on 2011-08-04
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.commands.sdr.record;

import com.veraxsystems.vxipmi.common.TypeConverter;

/**
 * This information is used for identifying management controllers on the IPMB
 * and other internal channels, and for providing Entity and initialization
 * information for all management controllers, including the BMC.
 * 这些信息用于标识IPMB和其他内部通道上的管理控制器，
 * 以及为包括BMC在内的所有管理控制器提供实体和初始化信息。
 */
public class ManagementControllerDeviceLocatorRecord extends SensorRecord {

	@Override
	public String toString() {
		return "ManagementControllerDeviceLocatorRecord [deviceAddress=" + deviceAddress + ", channelNumber="
				+ channelNumber + ", supportsChassis=" + supportsChassis + ", supportsBridge=" + supportsBridge
				+ ", supportsIpmbEventGenerator=" + supportsIpmbEventGenerator + ", supportsIpmbEventReciever="
				+ supportsIpmbEventReciever + ", supportsFruInventoryDevice=" + supportsFruInventoryDevice
				+ ", supportsSelDevice=" + supportsSelDevice + ", supportsSdrRepositoryDevice="
				+ supportsSdrRepositoryDevice + ", supportsSensors=" + supportsSensors + ", entityId=" + entityId
				+ ", entityInstance=" + entityInstance + ", name=" + name + "]";
	}

	private int deviceAddress;

	private int channelNumber;

	/**
	 * Device functions as chassis device,设备功能为底盘设备，
	 */
	private boolean supportsChassis;

	/**
	 * Controller responds to Bridge NetFn commands
	 * 控制器响应网桥NetFn命令
	 */
	private boolean supportsBridge;

	/**
	 * Device generates event messages on IPMB
	 * 设备在IPMB上生成事件消息
	 */
	private boolean supportsIpmbEventGenerator;

	/**
	 * Device accepts event messages from IPMB
	 * 设备接受来自IPMB的事件消息
	 */
	private boolean supportsIpmbEventReciever;

	/**
	 * Device accepts FRU commands to FRU Device #0 at LUN 00b
	 * 设备在LUN 00b接受FRU设备0的FRU命令
	 */
	private boolean supportsFruInventoryDevice;

	/**
	 * Device provides interface to SEL
	 */
	private boolean supportsSelDevice;

	/**
	 * For BMC, indicates BMC provides interface to SDR Repository. For other
	 * controller, indicates controller accepts Device SDR commands
	 * 对于BMC，表示BMC提供到SDR存储库的接口。对于其他控制器，表示控制器接受设备SDR命令
	 */
	private boolean supportsSdrRepositoryDevice;

	/**
	 * Device accepts sensor commands
	 */
	private boolean supportsSensors;
	
	/**
	 * Entity ID for the FRU associated with this device.
	 * 与此设备关联的FRU的实体ID。
	 */
	private int entityId;
	
	/**
	 * Instance number for entity.
	 */
	private int entityInstance;
	
	private String name;

	@Override
	protected void populateTypeSpecficValues(byte[] recordData,
			SensorRecord record) {
		
		setDeviceAddress(TypeConverter.byteToInt(recordData[5]) >> 1);
		
		setChannelNumber(TypeConverter.byteToInt(recordData[6]) & 0xf);
		
		setSupportsChassis((TypeConverter.byteToInt(recordData[8]) & 0x80) != 0);

		setSupportsBridge((TypeConverter.byteToInt(recordData[8]) & 0x40) != 0);

		setSupportsIpmbEventGenerator((TypeConverter.byteToInt(recordData[8]) & 0x20) != 0);

		setSupportsIpmbEventReciever((TypeConverter.byteToInt(recordData[8]) & 0x10) != 0);

		setSupportsFruInventoryDevice((TypeConverter.byteToInt(recordData[8]) & 0x8) != 0);

		setSupportsSelDevice((TypeConverter.byteToInt(recordData[8]) & 0x4) != 0);

		setSupportsSdrRepositoryDevice((TypeConverter.byteToInt(recordData[8]) & 0x2) != 0);

		setSupportsSensors((TypeConverter.byteToInt(recordData[8]) & 0x1) != 0);
		
		setEntityId(TypeConverter.byteToInt(recordData[12]));
		
		setEntityInstance(TypeConverter.byteToInt(recordData[13]));
		
		byte[] name = new byte[recordData.length - 16];
		
		System.arraycopy(recordData, 16, name, 0, name.length);
		
		setName(decodeName(recordData[15], name));

	}

	public int getDeviceAddress() {
		return deviceAddress;
	}

	public void setDeviceAddress(int deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	public int getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(int channelNumber) {
		this.channelNumber = channelNumber;
	}

	public boolean isSupportsChassis() {
		return supportsChassis;
	}

	public void setSupportsChassis(boolean supportsChassis) {
		this.supportsChassis = supportsChassis;
	}

	public boolean isSupportsBridge() {
		return supportsBridge;
	}

	public void setSupportsBridge(boolean supportsBridge) {
		this.supportsBridge = supportsBridge;
	}

	public boolean isSupportsIpmbEventGenerator() {
		return supportsIpmbEventGenerator;
	}

	public void setSupportsIpmbEventGenerator(boolean supportsIpmbEventGenerator) {
		this.supportsIpmbEventGenerator = supportsIpmbEventGenerator;
	}

	public boolean isSupportsIpmbEventReciever() {
		return supportsIpmbEventReciever;
	}

	public void setSupportsIpmbEventReciever(boolean supportsIpmbEventReciever) {
		this.supportsIpmbEventReciever = supportsIpmbEventReciever;
	}

	public boolean isSupportsFruInventoryDevice() {
		return supportsFruInventoryDevice;
	}

	public void setSupportsFruInventoryDevice(boolean supportsFruInventoryDevice) {
		this.supportsFruInventoryDevice = supportsFruInventoryDevice;
	}

	public boolean isSupportsSelDevice() {
		return supportsSelDevice;
	}

	public void setSupportsSelDevice(boolean supportsSelDevice) {
		this.supportsSelDevice = supportsSelDevice;
	}

	public boolean isSupportsSdrRepositoryDevice() {
		return supportsSdrRepositoryDevice;
	}

	public void setSupportsSdrRepositoryDevice(boolean supportsSdrRepositoryDevice) {
		this.supportsSdrRepositoryDevice = supportsSdrRepositoryDevice;
	}

	public boolean isSupportsSensors() {
		return supportsSensors;
	}

	public void setSupportsSensors(boolean supportsSensors) {
		this.supportsSensors = supportsSensors;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public int getEntityInstance() {
		return entityInstance;
	}

	public void setEntityInstance(int entityInstance) {
		this.entityInstance = entityInstance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
