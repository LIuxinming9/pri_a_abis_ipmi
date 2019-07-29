/*
 * IpmiPayload.java 
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

import java.security.InvalidKeyException;

import com.veraxsystems.vxipmi.coding.security.ConfidentialityAlgorithm;

/**
 * Payload for IPMI messages
 */
public abstract class IpmiPayload {

	private byte[] data;

	private byte[] encryptedPayload;

	public void setData(byte[] data) {
		this.data = data;
		/*for (int i = 0; i < data.length; i++) {
			System.out.print("___"+data[i]);
		}*/
	}

	public byte[] getData() {
		return data;
	}

	/**
	 * Returns encrypted payload encoded in byte array.
	 * 
	 * Migth be null if payload was not yet encrypted.
	 * 返回以字节数组编码的加密有效负载。
		如果有效负载尚未加密，则Migth为null。

	 * @see #encryptPayload(ConfidentialityAlgorithm)
	 */
	public byte[] getEncryptedPayload() {
		return encryptedPayload;
	}

	/**
	 * Returns unencrypted payload encoded in byte array (owner is responsible
	 * for encryption).
	 * 返回字节数组中编码的未加密负载(所有者负责加密)。
	 * @return payload
	 */
	public abstract byte[] getPayloadData();

	/**
	 * Returns encoded but UNENCRYPTED payload length.
	 * 返回编码但未加密的有效负载长度。
	 */
	public abstract int getPayloadLength();

	/**
	 * Returns IPMI command encapsulated in IPMI Payload.
	 * 返回封装在IPMI有效负载中的IPMI命令。
	 */
	public abstract byte[] getIpmiCommandData();

	/**
	 * Encrypts {@link #getPayloadData()}.
	 * 
	 * @param confidentialityAlgorithm
	 *            {@link ConfidentialityAlgorithm} to be used to encrypt payload
	 *            data.
	 * @throws InvalidKeyException
	 *             - when confidentiality algorithm fails.
	 * @see IpmiPayload#getEncryptedPayload()
	 */
	public void encryptPayload(ConfidentialityAlgorithm confidentialityAlgorithm)
			throws InvalidKeyException {
		encryptedPayload = confidentialityAlgorithm.encrypt(getPayloadData());
	}
}
