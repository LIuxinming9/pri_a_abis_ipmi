/*
 * ProtocolDecoder.java 
 * Created on 2011-07-26
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.protocol.decoder;

import java.security.InvalidKeyException;

import com.veraxsystems.vxipmi.coding.payload.IpmiPayload;
import com.veraxsystems.vxipmi.coding.payload.lan.IpmiLanResponse;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;
import com.veraxsystems.vxipmi.coding.protocol.IpmiMessage;
import com.veraxsystems.vxipmi.coding.rmcp.RmcpClassOfMessage;
import com.veraxsystems.vxipmi.coding.rmcp.RmcpMessage;
import com.veraxsystems.vxipmi.coding.security.ConfidentialityAlgorithm;
import com.veraxsystems.vxipmi.common.TypeConverter;

/**
 * Decodes IPMI session header and retrieves encrypted payload. Payload must be
 * IPMI LAN format message.
 * 解码IPMI会话头并检索加密的有效负载。负载必须是IPMI LAN格式的消息。
 */
public abstract class ProtocolDecoder implements IpmiDecoder {

	public ProtocolDecoder() {

	}

	/**
	 * Decodes IPMI message version independent fields.
	 * 解码与IPMI消息版本无关的字段。
	 * @param rmcpMessage
	 *            - RMCP message to decode.
	 * @param message
	 *            - A reference to message being decoded.
	 *            对正在解码的消息的引用。
	 * @param sequenceNumberOffset
	 *            - Protocol version specific offset to Session Sequence Number
	 *            field in the header of the IPMI message.
	 *            协议版本特定于IPMI消息头中会话序列号字段的偏移量。
	 * @param payloadLengthOffset
	 *            - Protocol version specific offset to IPMI Payload Length
	 *            field in the header of the IPMI message.
	 *            协议版本特定于IPMI消息头中IPMI有效负载长度字段的偏移量。
	 * @param payloadLengthLength
	 *            - Length of the payload length field.
	 * @see IpmiMessage
	 * @return Offset to the session trailer.
	 * @throws IllegalArgumentException
	 *             when delivered RMCP message does not contain encapsulated
	 *             IPMI message.
	 */
	@Deprecated
	protected int decode(RmcpMessage rmcpMessage, IpmiMessage message,
			int sequenceNumberOffset, int payloadLengthOffset,
			int payloadLengthLength) throws IllegalArgumentException {

		if (rmcpMessage.getClassOfMessage() != RmcpClassOfMessage.Ipmi) {
			throw new IllegalArgumentException("This is not an IPMI message");
		}

		byte[] raw = rmcpMessage.getData();

		message.setAuthenticationType(decodeAuthenticationType(raw[0]));

		message.setSessionSequenceNumber(decodeSessionSequenceNumber(raw,
				sequenceNumberOffset));

		message.setPayloadLength(decodePayloadLength(raw, payloadLengthOffset));

		message.setPayload(decodePayload(raw, payloadLengthOffset
				+ payloadLengthLength, message.getPayloadLength(),
				message.getConfidentialityAlgorithm()));

		return payloadLengthOffset + payloadLengthLength
				+ message.getPayloadLength();
	}

	/**
	 * Decodes IPMI message.
	 * 
	 * @param rmcpMessage
	 *            - RMCP message to decode.
	 * @see IpmiMessage
	 * @return Decoded IPMI message
	 * @throws IllegalArgumentException
	 *             when delivered RMCP message does not contain encapsulated
	 *             IPMI message.
	 * @throws InvalidKeyException
	 *             - when initiation of the integrity algorithm fails
	 */
	@Override
	public abstract IpmiMessage decode(RmcpMessage rmcpMessage)
			throws IllegalArgumentException, InvalidKeyException;

	protected static AuthenticationType decodeAuthenticationType(
			byte authenticationType) throws IllegalArgumentException {
		authenticationType &= TypeConverter.intToByte(0x0f);

		return AuthenticationType.parseInt(TypeConverter
				.byteToInt(authenticationType));
	}

	/**
	 * Decodes {@link AuthenticationType} of the message so that the version of
	 * the IPMI protocol could be determined.
	 * 解码消息的{@link AuthenticationType}，以便确定IPMI协议的版本。
	 * @param message
	 *            - RMCP message to decode.
	 * @return {@link AuthenticationType} of the message.
	 */
	public static AuthenticationType decodeAuthenticationType(
			RmcpMessage message) {
		return decodeAuthenticationType(message.getData()[0]);
	}

	/**
	 * Decodes int in a little endian convention from raw message at given
	 * offset
	 * 从给定偏移量的原始消息中以一种小的尾端约定解码整型
	 * 
	 * @param rawMessage
	 *            - Raw message to be decoded
	 * @param offset
	 * @return Decoded integer
	 */
	protected static int decodeInt(byte[] rawMessage, int offset) {
		byte[] result = new byte[4];

		System.arraycopy(rawMessage, offset, result, 0, 4);

		return TypeConverter.littleEndianByteArrayToInt(result);
	}

	/**
	 * Decodes session sequence number.
	 * 解码会话序列号。
	 * 
	 * @param rawMessage
	 *            - Byte array holding whole message data.
	 *            保存整个消息数据的字节数组。
	 * @param offset
	 *            - Offset to session sequence number in header.
	 *            标题中会话序列号的偏移量。
	 * @return Session Sequence number.
	 */
	protected int decodeSessionSequenceNumber(byte[] rawMessage, int offset) {
		return decodeInt(rawMessage, offset);
	}

	/**
	 * Decodes session ID.
	 * 
	 * @param rawMessage
	 *            - Byte array holding whole message data.
	 * @param offset
	 *            - Offset to session ID in header.
	 * @return Session ID.
	 */
	protected static int decodeSessionID(byte[] rawMessage, int offset) {
		return decodeInt(rawMessage, offset);
	}
	
	/**
	 * Decodes payload length.
	 * 
	 * @param rawData
	 *            - Byte array holding whole message data.
	 * @param offset
	 *            - Offset to payload length in header.
	 * @return payload length.
	 */
	protected abstract int decodePayloadLength(byte[] rawData, int offset);

	/**
	 * Decodes payload.
	 * 
	 * @param rawData
	 *            - Byte array holding whole message data.
	 * @param offset
	 *            - Offset to payload.
	 * @param length
	 *            - Length of the payload.
	 * @param confidentialityAlgorithm
	 *            - {@link ConfidentialityAlgorithm} required to decrypt
	 *            payload.
	 * @return Payload decoded into {@link IpmiLanResponse}.
	 */
	protected IpmiPayload decodePayload(byte[] rawData, int offset, int length,
			ConfidentialityAlgorithm confidentialityAlgorithm) {
		byte[] payload = null;
		if (length > 0) {
			payload = new byte[length];

			System.arraycopy(rawData, offset, payload, 0, length);

			payload = confidentialityAlgorithm.decrypt(payload);
		}
		return new IpmiLanResponse(payload);
	}
}
