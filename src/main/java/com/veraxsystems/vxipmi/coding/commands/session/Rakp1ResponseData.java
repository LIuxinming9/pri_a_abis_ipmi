/*
 * Rakp1ResponseData.java 
 * Created on 2011-07-26
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.commands.session;

import java.util.Arrays;

import com.veraxsystems.vxipmi.coding.commands.ResponseData;

/**
 * A wrapper for RAKP 2 message.
 * @see Rakp1
 */
public class Rakp1ResponseData implements ResponseData {
	
	@Override
	public String toString() {
		return "Rakp1ResponseData [messageTag=" + messageTag + ", statusCode=" + statusCode
				+ ", remoteConsoleSessionId=" + remoteConsoleSessionId + ", managedSystemRandomNumber="
				+ Arrays.toString(managedSystemRandomNumber) + ", managedSystemGuid="
				+ Arrays.toString(managedSystemGuid) + "]";
	}

	private byte messageTag;//信息标签
	
	private byte statusCode;//状态码
	
	private int remoteConsoleSessionId;//远程控制台会话Id
	
	private byte[] managedSystemRandomNumber;//管理系统随机数
	
	private byte[] managedSystemGuid;//管理系统全局唯一标识符 

	public void setMessageTag(byte messageTag) {
		this.messageTag = messageTag;
	}

	public byte getMessageTag() {
		return messageTag;
	}

	public void setStatusCode(byte statusCode) {
		this.statusCode = statusCode;
	}

	public byte getStatusCode() {
		return statusCode;
	}

	public void setRemoteConsoleSessionId(int remoteConsoleSessionId) {
		this.remoteConsoleSessionId = remoteConsoleSessionId;
	}

	public int getRemoteConsoleSessionId() {
		return remoteConsoleSessionId;
	}

	public void setManagedSystemGuid(byte[] managedSystemGuid) {
		this.managedSystemGuid = managedSystemGuid;
	}

	public byte[] getManagedSystemGuid() {
		return managedSystemGuid;
	}

	public void setManagedSystemRandomNumber(byte[] randomNumber) {
		this.managedSystemRandomNumber = randomNumber;
	}

	public byte[] getManagedSystemRandomNumber() {
		return managedSystemRandomNumber;
	}
}
