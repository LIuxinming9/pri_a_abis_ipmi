/*
 * PlainMassage.java 
 * Created on 2011-08-02
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.payload;

/**
 * Represents IPMI payload fully specified by user - contains wrapped byte array
 * which is returned as a payload via {@link #getPayloadData()}. Used for
 * OpenSession and RAKP messages.
 * 表示用户完全指定的IPMI有效负载——包含封装的字节数组，
 * 该数组通过{@link #getPayloadData()}作为有效负载返回。用于OpenSession和RAKP消息。
 */
public class PlainMessage extends IpmiPayload {

	@Override
	public byte[] getPayloadData() {
		return getData();
	}

	@Override
	public int getPayloadLength() {
		return getData().length;
	}
	
	/**
	 * Creates IPMI payload.
	 * @param data
	 * - byte array containing payload for IPMI message.
	 */
	public PlainMessage(byte[] data) {
		setData(data);
	}

	@Override
	public byte[] getIpmiCommandData() {
		return getData();
	}

}
