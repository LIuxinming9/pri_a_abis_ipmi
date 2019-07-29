/*
 * PowerRestorePolicy.java 
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

/**
 * Chassis power restore policy.
 */
public enum PowerRestorePolicy {
	/**
	 * Chassis stays powered off after AC/mains returns
	 * 底盘保持电源关闭后，交流/电源返回
	 */
	PoweredOff,
	/**
	 * After AC returns, power is restored to the state that was in effect when
	 * AC/mains was lost
	 * 交流电返回后，电源恢复到交流电/市电丢失时的有效状态
	 */
	PowerRestored,
	/**
	 * Chassis always powers up after AC/mains returns
	 * 底盘总是在交流/电源返回后启动
	 */
	PoweredUp,
}
