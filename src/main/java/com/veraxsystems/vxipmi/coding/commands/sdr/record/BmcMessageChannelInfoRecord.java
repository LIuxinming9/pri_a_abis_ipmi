/*
 * BmcMessageChannelInfoRecord.java 
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

/**
 * This record describes the allocation and type for the BMC message channels.
 * This record type has been deprecated
 * 此记录描述BMC消息通道的分配和类型。此记录类型已被弃用
 */
@Deprecated
public class BmcMessageChannelInfoRecord extends SensorRecord {

	@Override
	protected void populateTypeSpecficValues(byte[] recordData,
			SensorRecord record) {
	}

}
