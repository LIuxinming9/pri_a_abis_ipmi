/*
 * ReadFruDataResponseData.java 
 * Created on 2011-08-11
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.commands.fru;

import java.util.Arrays;

import com.veraxsystems.vxipmi.coding.commands.ResponseData;

/**
 * Wrapper for Read FRU Data response. Contains only raw data, because size of
 * the FRU Inventory Area might exceed size of the communication packet so it
 * might come in many {@link ReadFruDataResponseData} packets and it must be
 * decoded by {@link ReadFruData#decodeFruData(java.util.List)}.
 * 读取FRU数据响应的包装器。只包含原始数据，因为FRU库存区域的大小可能超过通信数据包的大小，
 * 所以它可能包含许多{@link ReadFruData#decodeFruData(java.util.List)}数据包，
 * 并且必须通过{@link ReadFruData#decodeFruData(java.util.List)}进行解码。
 */
public class ReadFruDataResponseData implements ResponseData {
	@Override
	public String toString() {
		return "ReadFruDataResponseData [fruData=" + Arrays.toString(fruData) + "]";
	}

	private byte[] fruData;

	public void setFruData(byte[] fruData) {
		this.fruData = fruData;
	}

	public byte[] getFruData() {
		return fruData;
	}
}
