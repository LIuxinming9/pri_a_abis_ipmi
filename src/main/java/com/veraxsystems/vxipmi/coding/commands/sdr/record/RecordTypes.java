/*
 * RecordTypes.java 
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
 * Class holding codes for SDR types. Byte constants are encoded as pseudo
 * unsigned bytes. IpmiLanConstants doesn't use {@link TypeConverter} because
 * fields need to be runtime constants.
 * 类持有SDR类型的代码。字节常量被编码为伪无符号字节。
 * IpmiLanConstants不使用{@link TypeConverter}，因为字段需要是运行时常量。
 * @see TypeConverter#byteToInt(byte)
 * @see TypeConverter#intToByte(int)
 * 全传感器记录= 0x01;
紧凑传感器记录= 0x02;
事件仅记录= 0x03;
实体关联记录= 0x08;
设备相对实体关联记录= 0x09;
通用设备定位器记录= 0x10;
FRU设备定位器记录= 0x11;
管理控制器设备定位器记录= 0x12;
管理控制器确认记录= 0x13;
BMC消息通道信息记录= 0x14;
OEM记录=(字节)(0xc0 - 256);

 */
public final class RecordTypes {

	public static final byte FULL_SENSOR_RECORD = 0x01;

	public static final byte COMPACT_SENSOR_RECORD = 0x02;

	public static final byte EVENT_ONLY_RECORD = 0x03;

	public static final byte ENTITY_ASSOCIATION_RECORD = 0x08;

	public static final byte DEVICE_RELATIVE_ENTITY_ASSOCIATION_RECORD = 0x09;

	public static final byte GENERIC_DEVICE_LOCATOR_RECORD = 0x10;

	public static final byte FRU_DEVICE_LOCATOR_RECORD = 0x11;

	public static final byte MANAGEMENT_CONTROLLER_DEVICE_LOCATOR_RECORD = 0x12;

	public static final byte MANAGEMENT_CONTROLLER_CONFIRMATION_RECORD = 0x13;

	public static final byte BMC_MESSAGE_CHANNEL_INFO_RECORD = 0x14;

	public static final byte OEM_RECORD = (byte) (0xc0 - 256);

	private RecordTypes() {
	}
}
