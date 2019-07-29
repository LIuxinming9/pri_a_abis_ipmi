/*
 * Encoder.java 
 * Created on 2011-07-21
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.veraxsystems.vxipmi.coding.commands.IpmiCommandCoder;
import com.veraxsystems.vxipmi.coding.protocol.decoder.Protocolv15Decoder;
import com.veraxsystems.vxipmi.coding.protocol.decoder.Protocolv20Decoder;
import com.veraxsystems.vxipmi.coding.protocol.encoder.IpmiEncoder;
import com.veraxsystems.vxipmi.coding.rmcp.RmcpEncoder;
import com.veraxsystems.vxipmi.coding.rmcp.RmcpIpmiMessage;

/**
 * Creates RMCP packet containing encrypted IPMI command from IPMICommand
 * wrapper class or raw byte data.
 * 从IPMICommand包装器类或原始字节数据创建包含加密IPMI命令的RMCP包。
 */
public final class Encoder {

	/**
	 * Encodes IPMI command specified by commandCoder into byte array raw data.
	 * 将commandCoder指定的IPMI命令编码为字节数组原始数据。
	 * @param protcolEncoder
	 *            - instance of {@link IpmiEncoder} class for encoding of the
	 *            IPMI session header. {@link Protocolv15Decoder} or
	 *            {@link Protocolv20Decoder} should be used (depending on IPMI
	 *            protocol version used).
	 * @param commandCoder
	 *            - instance of {@link IpmiCommandCoder} class used for building
	 *            IPMI message payload.
	 * @param sequenceNumber
	 *            - A generated sequence number used for matching request and
	 *            response. If IPMI message is sent in a session, it is used as
	 *            a Session Sequence Number. For all IPMI messages,
	 *            sequenceNumber % 256 is used as a IPMI LAN Message sequence
	 *            number and as an IPMI payload message tag.
	 *            一个生成的序列号，用于匹配请求和响应。如果IPMI消息在会话中发送，则将其用作会话序列号。
	 *            对于所有IPMI消息，sequenceNumber % 256用作IPMI LAN消息序列号和IPMI有效负载消息标记。
	 * @param sessionId
	 *            - ID of the managed system's session message is being sent in.
	 *            正在发送托管系统的会话消息的ID。
	 *            For sessionless commands should be set to 0.
	 * @return encoded IPMI command编码IPMI命令
	 * @throws NoSuchAlgorithmException
	 *             - when authentication, confidentiality or integrity algorithm
	 *             fails.
	 * @throws InvalidKeyException
	 *             - when creating of the algorithm key fails
	 */
	public static byte[] encode(IpmiEncoder protcolEncoder,
			IpmiCommandCoder commandCoder, int sequenceNumber, int sessionId)
			throws NoSuchAlgorithmException, InvalidKeyException {
		return RmcpEncoder
				.encode(new RmcpIpmiMessage(protcolEncoder.encode(commandCoder
						.encodeCommand(sequenceNumber, sessionId))));
	}

	private Encoder() {
	}
}
