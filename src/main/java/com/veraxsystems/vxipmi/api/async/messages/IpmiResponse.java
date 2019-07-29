/*
 * IpmiResponse.java 
 * Created on 2011-09-07
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.api.async.messages;

import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.async.IpmiListener;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;

/**
 * Interface for response messages delivered to {@link IpmiListener}s
 */
public abstract class IpmiResponse {
	private int tag;
	/*@Override
	public String toString() {
		return "IpmiResponse [tag=" + tag + ", handle=" + handle + "]";
	}*/

	private ConnectionHandle handle;

	/**
	 * {@link ConnectionHandle} to the message that was an origin of the
	 * response Handle contains only the id of the connection, not the
	 * {@link CipherSuite} and {@link PrivilegeLevel} used in that connection.
	 * 到响应句柄起源的消息的{@link ConnectionHandle}只包含连接的id，
	 * 而不包含在该连接中使用的{@link CipherSuite}和{@link特权}。
	 */
	public ConnectionHandle getHandle() {
		return handle;
	}

	/**
	 * Tag of the message that is associated with the {@link IpmiResponse}
	 * 与{@link IpmiResponse}关联的消息的标记
	 */
	public int getTag() {
		return tag;
	}

	public IpmiResponse(int tag, ConnectionHandle handle) {
		this.tag = tag;
		this.handle = handle;
	}
}
