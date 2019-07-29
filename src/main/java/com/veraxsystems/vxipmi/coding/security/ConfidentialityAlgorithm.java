/*
 * ConfidentialityAlgorithm.java 
 * Created on 2011-07-25
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

/**
 * Interface for Confidentiality Algorithms. All classes extending this one must
 * implement constructor(byte[]).
 * 接口的机密性算法。扩展这个类的所有类都必须实现构造函数(byte[])。
 */
public abstract class ConfidentialityAlgorithm {
	protected byte[] sik;

	/**
	 * Initializes Confidentiality Algorithm
	 * 
	 * @param sik
	 *            - Session Integrity Key calculated during the opening of the
	 *            session or user password if 'one-key' logins are enabled.
	 *            如果启用“一键”登录，则在会话或用户密码打开期间计算会话完整性密钥。
	 * @throws InvalidKeyException
	 *             - when initiation of the algorithm fails
	 * @throws NoSuchAlgorithmException
	 *             - when initiation of the algorithm fails
	 * @throws NoSuchPaddingException
	 *             - when initiation of the algorithm fails
	 */
	public void initialize(byte[] sik) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException {
		this.sik = sik;
	}

	/**
	 * Returns the algorithm's ID.
	 */
	public abstract byte getCode();

	/**
	 * Encrypts the data.
	 * 加密数据。
	 * @param data
	 *            - payload to be encrypted
	 *            要加密的负载
	 * @return encrypted data encapsulated in COnfidentiality Header and
	 *         Trailer.
	 *         封装在保密头和尾文件中的加密数据。
	 * @throws InvalidKeyException
	 *             - when initiation of the algorithm fails
	 */
	public abstract byte[] encrypt(byte[] data) throws InvalidKeyException;

	/**
	 * Decrypts the data.
	 * 解密数据。
	 * @param data
	 *            - encrypted data encapsulated in COnfidentiality Header and
	 *            Trailer.
	 *            封装在保密头和尾文件中的加密数据。
	 * @return decrypted data.
	 * @throws IllegalArgumentException 
	 *             - when initiation of the algorithm fails
	 */
	public abstract byte[] decrypt(byte[] data) throws IllegalArgumentException;

	/**
	 * Calculates size of the confidentiality header and trailer specific for
	 * the algorithm.
	 * 计算特定于该算法的机密标题和尾部的大小。
	 * 
	 * @param payloadSize
	 *            - size of the data that will be encrypted
	 */
	public abstract int getConfidentialityOverheadSize(int payloadSize);
}
