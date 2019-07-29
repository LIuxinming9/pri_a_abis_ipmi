/*
 * AuthenticationAlgorithm.java 
 * Created on 2011-07-21
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

import com.veraxsystems.vxipmi.coding.commands.session.Rakp1;

/**
 * Interface for authentication algorithms. All classes extending this one must
 * have a parameterless constructor.
 * 用于身份验证算法的接口。扩展这个类的所有类都必须有一个无参数构造函数。
 */
public abstract class AuthenticationAlgorithm {

	/**
	 * @return algorithm-specific code算法具体代码
	 */
	public abstract byte getCode();
	
	/**
	 * @return length of the key for the RAKP2 message
	 */
	public abstract int getKeyLength();
	
	/**
	 * @return length of the integrity check base for RAKP4 message
	 * RAKP4消息的完整性检查基数的长度
	 */
	public abstract int getIntegrityCheckBaseLength();

	/**
	 * Checks value of the Key Exchange Authentication Code in RAKP messages
	 * 检查RAKP消息中密钥交换身份验证代码的值
	 * @param data
	 *            - The base for authentication algorithm. Depends on RAKP
	 *            Message.验证算法的基础。取决于RAKP消息。
	 * @param key
	 *            - the Key Exchange Authentication Code to check.
	 *            密钥交换验证码要检查。
	 * @param password
	 *            - password of the user establishing a session
	 *            建立会话的用户的密码
	 * @return True if authentication check was successful, false otherwise.
	 * @throws NoSuchAlgorithmException
	 *             when initiation of the algorithm fails
	 * @throws InvalidKeyException
	 *             when creating of the algorithm key failsS
	 */
	public abstract boolean checkKeyExchangeAuthenticationCode(byte[] data,
			byte[] key, String password) throws NoSuchAlgorithmException,
			InvalidKeyException;

	/**
	 * Calculates value of the Key Exchange Authentication Code in RAKP messages
	 * 计算RAKP消息中密钥交换身份验证代码的值
	 * @param data
	 *            - The base for authentication algorithm. Depends on RAKP
	 *            Message.
	 * @param password
	 *            - password of the user establishing a session
	 * @throws NoSuchAlgorithmException
	 *             when initiation of the algorithm fails
	 * @throws InvalidKeyException
	 *             when creating of the algorithm key fails
	 */
	public abstract byte[] getKeyExchangeAuthenticationCode(byte[] data,
			String password) throws NoSuchAlgorithmException,
			InvalidKeyException;

	/**
	 * Validates Integrity Check Value in RAKP Message 4.
	 * 验证RAKP消息4中的完整性检查值
	 * @param data
	 *            - The base for authentication algorithm.
	 * @param reference
	 *            - The Integrity Check Value to validate.
	 * @param sik
	 *            - The Session Integrity Key generated on base of RAKP Messages
	 *            1 and 2.
	 * @see Rakp1#calculateSik(com.veraxsystems.vxipmi.coding.commands.session.Rakp1ResponseData)
	 * @return True if integrity check was successful, false otherwise.
	 * @throws NoSuchAlgorithmException
	 *             when initiation of the algorithm fails
	 * @throws InvalidKeyException
	 *             when creating of the algorithm key fails
	 */
	public abstract boolean doIntegrityCheck(byte[] data, byte[] reference,
			byte[] sik) throws InvalidKeyException, NoSuchAlgorithmException;
}
