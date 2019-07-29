/*
 * FruDeviceLocatorRecord.java 
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
 此记录用于定位位于IPMB上的FRU信息，位于后台或管理控制器的私有总线上的FRU信息
 ，或通过FRU命令访问管理控制器的FRU信息。

 */
public class FruDeviceLocatorRecord extends SensorRecord {

	@Override
	public String toString() {
		return "FruDeviceLocatorRecord [deviceAccessAddress=" + deviceAccessAddress + ", deviceId=" + deviceId
				+ ", logical=" + logical + ", accessLun=" + accessLun + ", managementChannelNumber="
				+ managementChannelNumber + ", deviceType=" + deviceType + ", deviceTypeModifier=" + deviceTypeModifier
				+ ", fruEntityId=" + fruEntityId + ", fruEntityInstance=" + fruEntityInstance + ", name=" + name + "]";
	}

	private int deviceAccessAddress;//设备访问地址

	private int deviceId;

	/**
	 false -设备不是逻辑FRU设备
	true -设备是逻辑FRU设备(通过FRU命令访问mgmt)。控制器)

	 */
	private boolean logical;//逻辑

	private int accessLun;//访问Lun

	private int managementChannelNumber;//管理通道

	private DeviceType deviceType;

	private int deviceTypeModifier;//设备类别改变

	private int fruEntityId;

	private int fruEntityInstance;

	private String name;

	@Override
	protected void populateTypeSpecficValues(byte[] recordData,
			SensorRecord record) {

		setDeviceAccessAddress((TypeConverter.byteToInt(recordData[5]) & 0xfe) >> 1);

		setLogical((TypeConverter.byteToInt(recordData[7]) & 0x80) != 0);

		int deviceId = TypeConverter.byteToInt(recordData[6]);

		if (!isLogical()) {
			deviceId &= 0xfe;
			deviceId >>= 1;
		}

		setDeviceId(deviceId);
		int id = TypeConverter.byteToInt(recordData[6]);

		if (!isLogical()) {
			id >>= 1;
		}

		setId(id);

		setAccessLun((TypeConverter.byteToInt(recordData[7]) & 0xc) >> 2);

		setManagementChannelNumber((TypeConverter.byteToInt(recordData[8]) & 0xf0) >> 4);

		setDeviceType(DeviceType.parseInt(TypeConverter
				.byteToInt(recordData[10])));

		setDeviceTypeModifier(TypeConverter.byteToInt(recordData[11]));

		setFruEntityId(TypeConverter.byteToInt(recordData[12]));

		setFruEntityInstance(TypeConverter.byteToInt(recordData[13]));

		byte[] name = new byte[recordData.length - 16];

		System.arraycopy(recordData, 16, name, 0, name.length);

		setName(decodeName(recordData[15], name));
	}

	public int getDeviceAccessAddress() {
		return deviceAccessAddress;
	}

	public void setDeviceAccessAddress(int deviceAccessAddress) {
		this.deviceAccessAddress = deviceAccessAddress;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public boolean isLogical() {
		return logical;
	}

	public void setLogical(boolean logical) {
		this.logical = logical;
	}

	public int getAccessLun() {
		return accessLun;
	}

	public void setAccessLun(int accessLun) {
		this.accessLun = accessLun;
	}

	public int getManagementChannelNumber() {
		return managementChannelNumber;
	}

	public void setManagementChannelNumber(int managementChannelNumber) {
		this.managementChannelNumber = managementChannelNumber;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public int getDeviceTypeModifier() {
		return deviceTypeModifier;
	}

	public void setDeviceTypeModifier(int deviceTypeModifier) {
		this.deviceTypeModifier = deviceTypeModifier;
	}

	public int getFruEntityId() {
		return fruEntityId;
	}

	public void setFruEntityId(int fruEntityId) {
		this.fruEntityId = fruEntityId;
	}

	public int getFruEntityInstance() {
		return fruEntityInstance;
	}

	public void setFruEntityInstance(int fruEntityInstance) {
		this.fruEntityInstance = fruEntityInstance;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
