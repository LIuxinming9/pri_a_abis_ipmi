/*
 * CommandCodes.java 
 * Created on 2011-07-21
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.commands;

import com.veraxsystems.vxipmi.common.TypeConverter;

/**
 * Contains command codes for IPMI commands Byte constants are encoded as pseudo
 * unsigned bytes. IpmiLanConstants doesn't use {@link TypeConverter} because
 * fields need to be runtime constants.
 * 包含用于IPMI命令的命令代码字节常量被编码为伪无符号字节。
 * IpmiLanConstants不使用{@link TypeConverter}，因为字段需要是运行时常量。

 * @see TypeConverter#byteToInt(byte)
 * @see TypeConverter#intToByte(int)
 * 
 */
public final class CommandCodes {

	/**
	 * An IPMI code for Get Chassis底盘 Status command
	 */
	public static final byte GET_CHASSIS_STATUS = 0x01;

	/**
	 * An IPMI code for Chassis Control command
	 */
	public static final byte CHASSIS_CONTROL = 0x02;

	/**
	 * An IPMI code for Get FRU Inventory库存  Area Info command
	 */
	public static final byte GET_FRU_INVENTORY_AREA_INFO = 0x10;

	/**
	 * An IPMI code for Read FRU Data command
	 */
	public static final byte READ_FRU_DATA = 0x11;

	/**
	 * An IPMI code for Get Device SDR Info command
	 */
	public static final byte GET_SDR_REPOSITORY_INFO = 0x20;

	/**
	 * An IPMI code for Get Device SDR Info command
	 */
	public static final byte GET_DEVICE_SDR_INFO = 0x21;

	/**
	 * An IPMI code for Reserve SDR Repository command
	 */
	public static final byte RESERVE_SDR_REPOSITORY = 0x22;

	/**
	 * An IPMI code for Get SDR command
	 */
	public static final byte GET_SDR = 0x23;

	/**
	 * An IPMI code for Get Channel通道 Authentication Capabilities command
	 */
	public static final byte GET_CHANNEL_AUTHENTICATION_CAPABILITIES = 0x38;

	/**
     * An IPMI code for Set Session Privilege Level command
     */
	public static final byte SET_SESSION_PRIVILEGE_LEVEL = 0x3B;
	
	/**
	 * An IPMI code for Get SEL Info command
	 */
	public static final byte GET_SEL_INFO = 0x40;

	/**
	 * An IPMI code for Reserve储备 SEL command
	 */
	public static final byte RESERVE_SEL = 0x42;

	/**
	 * An IPMI code for Get SEL Entry command
	 */
	public static final byte GET_SEL_ENTRY = 0x43;

	/**
	 * An IPMI code for Get Channel Cipher Suites command
	 */
	public static final byte GET_CHANNEL_CIPHER_SUITES = 0x54;

	private CommandCodes() {
	}
}
