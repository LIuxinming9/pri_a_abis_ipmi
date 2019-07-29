/*
 * ReserveSdrRepositoryResponseData.java 
 * Created on 2011-08-03
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.commands.sdr;

import com.veraxsystems.vxipmi.coding.commands.ResponseData;

/**
 * Wrapper for Reserve SDR Repository command response.
 * 用于Reserve SDR存储库命令响应的包装器。
 */
public class ReserveSdrRepositoryResponseData implements ResponseData {

	@Override
	public String toString() {
		return "ReserveSdrRepositoryResponseData [reservationId=" + reservationId + "]";
	}

	/**
	 * This value is required in other requests, such as the 'Delete SDR'
	 * command. These commands will not execute unless the correct Reservation
	 * ID value is provided.
	 * 这个值在其他请求中是必需的，例如'Delete SDR'命令。
	 * 除非提供正确的reservation ationid值，否则这些命令不会执行。
	 */
	private int reservationId;

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getReservationId() {
		return reservationId;
	}
}
