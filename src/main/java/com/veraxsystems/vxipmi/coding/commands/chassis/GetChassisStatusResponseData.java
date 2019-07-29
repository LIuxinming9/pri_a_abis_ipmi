/*
 * GetChassisStatusResponseData.java 
 * Created on 2011-08-28
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.commands.chassis;

import com.veraxsystems.vxipmi.coding.commands.ResponseData;
import com.veraxsystems.vxipmi.common.TypeConverter;

/**
 * Wrapper for Get Chassis Status response.
 */
public class GetChassisStatusResponseData implements ResponseData {
	

	@Override
	public String toString() {
		return "GetChassisStatusResponseData [currentPowerState=" + currentPowerState + ", lastPowerEvent="
				+ lastPowerEvent + ", miscChassisState=" + miscChassisState + ", isFrontPanelButtonCapabilitiesSet="
				+ isFrontPanelButtonCapabilitiesSet + ", frontPanelButtonCapabilities=" + frontPanelButtonCapabilities
				+ "]";
	}

	private byte currentPowerState;//��ǰȨ��״̬

	private byte lastPowerEvent;//���Ļ�����һ����Ȩ���¼�

	private byte miscChassisState;//���ӵĵ���״̬

	private boolean isFrontPanelButtonCapabilitiesSet;//��ǰ��尴ť���ܼ���

	private byte frontPanelButtonCapabilities;//ǰ��尴ť

	public GetChassisStatusResponseData() {
		setFrontPanelButtonCapabilitiesSet(false);
	}

	public void setCurrentPowerState(byte currentPowerState) {
		this.currentPowerState = currentPowerState;
	}

	public byte getCurrentPowerState() {
		return currentPowerState;
	}

	public PowerRestorePolicy getPowerRestorePolicy() {
		switch ((currentPowerState & TypeConverter.intToByte(0x60)) >> 5) {
		case 0:
			return PowerRestorePolicy.PoweredOff;
		case 1:
			return PowerRestorePolicy.PowerRestored;
		case 2:
			return PowerRestorePolicy.PoweredUp;
		default:
			throw new IllegalArgumentException("Invalid Power Restore Policy");
		}
	}

	/**
	 * @return True when controller attempted to turn system power on or off,
	 *         but system did not enter desired state
	 *        当控制器试图打开或关闭系统电源，但系统未进入所需状态时为

	 */
	public boolean isPowerControlFault() {
		return ((currentPowerState & TypeConverter.intToByte(0x10)) != 0);
	}

	/**
	 * @return True when fault was detected in main power subsystem.
	 * 当主电源分系统检测到故障时为真

	 */
	public boolean isPowerFault() {
		return ((currentPowerState & TypeConverter.intToByte(0x8)) != 0);
	}

	/**
	 * @return True when interlock was detected (chassis is presently shut down
	 *         because a chassis panel interlock switch is active)
	 *         当检测到联锁时为真(底盘当前关闭，因为底盘面板联锁开关处于活动状态)

	 */
	public boolean isInterlock() {
		return ((currentPowerState & TypeConverter.intToByte(0x4)) != 0);
	}

	/**
	 * @return True when system was shut down because of power overload
	 *         condition.
	 *        当系统因功率过载而关闭时为。

	 */
	public boolean isPowerOverload() {
		return ((currentPowerState & TypeConverter.intToByte(0x2)) != 0);
	}

	/**
	 * @return True when system power is on.
	 */
	public boolean isPowerOn() {
		return ((currentPowerState & TypeConverter.intToByte(0x1)) != 0);
	}

	public void setLastPowerEvent(byte lastPowerEvent) {
		this.lastPowerEvent = lastPowerEvent;
	}

	public byte getLastPowerEvent() {
		return lastPowerEvent;
	}

	/**
	 * @return True when last 'Power is on' state was entered via IPMI command.
	 * 当通过IPMI命令输入最后一个“Power is on”状态时为真。

	 */
	public boolean wasIpmiPowerOn() {
		return ((lastPowerEvent & TypeConverter.intToByte(0x10)) != 0);
	}

	/**
	 * @return True if last power down caused by power fault.
	 * 如果上次断电是由于电源故障造成的，则为真。

	 */
	public boolean wasPowerFault() {
		return ((lastPowerEvent & TypeConverter.intToByte(0x8)) != 0);
	}

	/**
	 * @return True if last power down caused by a power interlock being
	 *         activated.
	 *         如果最后一次因电源联锁被激活而断电，则为。

	 */
	public boolean wasInterlock() {
		return ((lastPowerEvent & TypeConverter.intToByte(0x4)) != 0);
	}

	/**
	 * @return True if last power down caused by a Power overload.
	 * 如果上次断电是由电源过载引起的，则为真。

	 */
	public boolean wasPowerOverload() {
		return ((lastPowerEvent & TypeConverter.intToByte(0x2)) != 0);
	}

	/**
	 * @return True if AC failed.交流电
	 */
	public boolean acFailed() {
		return ((lastPowerEvent & TypeConverter.intToByte(0x1)) != 0);

	}

	public void setMiscChassisState(byte miscChassisState) {
		this.miscChassisState = miscChassisState;
	}

	public byte getMiscChassisState() {
		return miscChassisState;
	}

	/**
	 * @return True if Chassis Identify command and state info supported.
	 * 如果支持底盘标识命令和状态信息，则为True。

	 */
	public boolean isChassisIdentifyCommandSupported() {
		return ((miscChassisState & TypeConverter.intToByte(0x40)) != 0);
	}

	public ChassisIdentifyState getChassisIdentifyState() {
		if (!isChassisIdentifyCommandSupported()) {
			throw new IllegalAccessError(
					"Chassis Idetify command and state not supported");
		}

		return ChassisIdentifyState.parseInt((miscChassisState & TypeConverter
				.intToByte(0x30)) >> 4);
	}

	/**
	 * @return True if cooling or fan fault was detected.
	 * 如果检测到冷却或风扇故障，则为

	 */
	public boolean coolingFaultDetected() {
		return ((miscChassisState & TypeConverter.intToByte(0x8)) != 0);
	}

	/**
	 * @return True if drive fault was detected.
	 * 如果检测到驱动器故障，则为。

	 */
	public boolean driveFaultDetected() {
		return ((miscChassisState & TypeConverter.intToByte(0x4)) != 0);
	}

	/**
	 * @return True if Front Panel Lockout active (power off and reset via
	 *         chassis push-buttons disabled.).
	 *        如果前面板锁定活动(电源关闭，并通过底盘按钮重置禁用。)

	 */
	public boolean isFrontPanelLockoutActive() {
		return ((miscChassisState & TypeConverter.intToByte(0x2)) != 0);
	}

	/**
	 * @return True if Chassis intrusion active is active.
	 * 如果机箱入侵活动为活动，则为。

	 */
	public boolean isChassisIntrusionActive() {
		return ((miscChassisState & TypeConverter.intToByte(0x1)) != 0);
	}

	public void setFrontPanelButtonCapabilities(
			byte frontPanelButtonCapabilities) {
		this.frontPanelButtonCapabilities = frontPanelButtonCapabilities;
		setFrontPanelButtonCapabilitiesSet(true);
	}

	public byte getFrontPanelButtonCapabilities() {
		return frontPanelButtonCapabilities;
	}

	/**
	 * @return Standby (sleep) button disable is allowed.
	 * 允许待机(休眠)按钮禁用。

	 * @throws IllegalAccessException
	 *             when Front Panel Button Capabilities wasn't set.
	 */
	public boolean isStandbyButtonDisableAllowed()
			throws IllegalAccessException {
		if (!isFrontPanelButtonCapabilitiesSet()) {
			throw new IllegalAccessException(
					"Front Panel Button Capabilities not set");
		}
		return ((frontPanelButtonCapabilities & TypeConverter.intToByte(0x80)) != 0);
	}

	/**
	 * @return Diagnostic Interrupt button disable is allowed.
	 * 允许诊断中断按钮禁用。

	 * @throws IllegalAccessException
	 *             when Front Panel Button Capabilities wasn't set.
	 */
	public boolean isDiagnosticInterruptButtonDisableAllowed()
			throws IllegalAccessException {
		if (!isFrontPanelButtonCapabilitiesSet()) {
			throw new IllegalAccessException(
					"Front Panel Button Capabilities not set");
		}
		return ((frontPanelButtonCapabilities & TypeConverter.intToByte(0x40)) != 0);
	}

	/**
	 * @return Reset button disable is allowed.
	 * 允许重置按钮禁用。

	 * @throws IllegalAccessException
	 *             when Front Panel Button Capabilities wasn't set.
	 */
	public boolean isResetButtonDisableAllowed() throws IllegalAccessException {
		if (!isFrontPanelButtonCapabilitiesSet()) {
			throw new IllegalAccessException(
					"Front Panel Button Capabilities not set");
		}
		return ((frontPanelButtonCapabilities & TypeConverter.intToByte(0x20)) != 0);
	}

	/**
	 * @return Power off button disable allowed (in the case there is a single
	 *         combined power/standby (sleep) button, disabling power off also
	 *         disables sleep requests via that button.)
	 *         关闭电源按钮禁用允许(在有一个电源/备用(睡眠)按钮的情况下，禁用电源关闭也禁用通过该按钮的睡眠请求。)

	 * @throws IllegalAccessException
	 *             when Front Panel Button Capabilities wasn't set.
	 */
	public boolean isPowerOffButtonDisableAllowed()
			throws IllegalAccessException {
		if (!isFrontPanelButtonCapabilitiesSet()) {
			throw new IllegalAccessException(
					"Front Panel Button Capabilities not set");
		}
		return ((frontPanelButtonCapabilities & TypeConverter.intToByte(0x10)) != 0);
	}

	/**
	 * @return Standby (sleep) button disabled.
	 *关闭待机(休眠)按钮。

	 * @throws IllegalAccessException
	 *             when Front Panel Button Capabilities wasn't set.
	 */
	public boolean isStandbyButtonDisabled() throws IllegalAccessException {
		if (!isFrontPanelButtonCapabilitiesSet()) {
			throw new IllegalAccessException(
					"Front Panel Button Capabilities not set");
		}
		return ((frontPanelButtonCapabilities & TypeConverter.intToByte(0x8)) != 0);
	}

	/**
	 * @return Diagnostic Interrupt button disabled.
	 * 诊断中断按钮禁用。

	 * @throws IllegalAccessException
	 *             when Front Panel Button Capabilities wasn't set.
	 */
	public boolean isDiagnosticInterruptButtonDisabled()
			throws IllegalAccessException {
		if (!isFrontPanelButtonCapabilitiesSet()) {
			throw new IllegalAccessException(
					"Front Panel Button Capabilities not set");
		}
		return ((frontPanelButtonCapabilities & TypeConverter.intToByte(0x4)) != 0);
	}

	/**
	 * @return Reset button disabled.
	 * 重置按钮禁用。

	 * @throws IllegalAccessException
	 *             when Front Panel Button Capabilities wasn't set.
	 */
	public boolean isResetButtonDisabled() throws IllegalAccessException {
		if (!isFrontPanelButtonCapabilitiesSet()) {
			throw new IllegalAccessException(
					"Front Panel Button Capabilities not set");
		}
		return ((frontPanelButtonCapabilities & TypeConverter.intToByte(0x2)) != 0);
	}

	/**
	 * @return Power off button disabled (in the case there is a single combined
	 *         power/standby (sleep) button, disabling power off also disables
	 *         sleep requests via that button are also disabled.)
	 *        关闭电源按钮禁用(在有一个电源/备用(睡眠)按钮的情况下，禁用电源关闭也禁用通过该按钮的睡眠请求。)

	 * @throws IllegalAccessException
	 *             when Front Panel Button Capabilities wasn't set.
	 */
	public boolean isPowerOffButtonDisabled() throws IllegalAccessException {
		if (!isFrontPanelButtonCapabilitiesSet()) {
			throw new IllegalAccessException(
					"Front Panel Button Capabilities not set");
		}
		return ((frontPanelButtonCapabilities & TypeConverter.intToByte(0x1)) != 0);
	}

	private void setFrontPanelButtonCapabilitiesSet(
			boolean isFrontPanelButtonCapabilitiesSet) {
		this.isFrontPanelButtonCapabilitiesSet = isFrontPanelButtonCapabilitiesSet;
	}

	public boolean isFrontPanelButtonCapabilitiesSet() {
		return isFrontPanelButtonCapabilitiesSet;
	}

}
