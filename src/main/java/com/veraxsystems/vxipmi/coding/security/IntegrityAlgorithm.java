/*
 * IntegrityAlgorithm.java 
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

import com.veraxsystems.vxipmi.coding.commands.session.Rakp1;
import com.veraxsystems.vxipmi.common.TypeConverter;

/**
 * Interface for Integrity Algorithms. All classes extending this one must
 * implement constructor(byte[]).
 * 接口的完整性算法。扩展这个类的所有类都必须实现构造函数(byte[])。
 */
public abstract class IntegrityAlgorithm {

	protected byte[] sik;

	public IntegrityAlgorithm() {

	}

	/**
	 * Initializes Integrity Algorithm
	 * 
	 * @param sik
	 *            - Session Integrity Key calculated during the opening of the
	 *            session or user password if 'one-key' logins are enabled.
	 * @throws InvalidKeyException
	 *             - when initiation of the algorithm fails
	 */
	public void initialize(byte[] sik) throws InvalidKeyException {
		this.sik = sik;
	}

	/**
	 * Returns the algorithm's ID.
	 */
	public abstract byte getCode();

	/**
	 * Creates AuthCode field for message.
	 * 为消息创建AuthCode字段。
	 * 
	 * @param base
	 *            - data starting with the AuthType/Format field up to and
	 *            including the field that immediately precedes the AuthCode
	 *            field
	 * @return AuthCode field. Might be null if empty AuthCOde field is
	 *         generated.
	 * 
	 * @see Rakp1#calculateSik(com.veraxsystems.vxipmi.coding.commands.session.Rakp1ResponseData)
	 */
	public abstract byte[] generateAuthCode(byte[] base);

	/**
	 * Modifies the algorithm base since with null Auth Code during encoding
	 * Integrity Pad isn't calculated.
	 * 修改算法库，因为在编码过程中不计算零认证码的完整性垫。
	 * @param base
	 *            - integrity algorithm base without Integrity Pad.
	 *            不带完整性垫的完整性算法库。
	 * @param authCodeLength
	 *            - expected length of the Auth Code field.
	 * @return - integrity algorithm base with Integrity Pad and updated Pad
	 *         Length field.
	 *         完整的算法基础与完整垫和更新垫长字段。
	 */
	protected byte[] injectIntegrityPad(byte[] base, int authCodeLength) {
		int pad = 0;
		if ((base.length + authCodeLength) % 4 != 0) {
			pad = 4 - (base.length + authCodeLength) % 4;
		}

		if (pad != 0) {
			byte[] newBase = new byte[base.length + pad];

			System.arraycopy(base, 0, newBase, 0, base.length - 2);

			for (int i = base.length - 2; i < base.length - 2 + pad; ++i) {
				newBase[i] = TypeConverter.intToByte(0xff);
			}

			newBase[newBase.length - 2] = TypeConverter.intToByte(pad);

			newBase[newBase.length - 1] = base[base.length - 1];

			return newBase;
		} else {
			return base;
		}
	}
}
