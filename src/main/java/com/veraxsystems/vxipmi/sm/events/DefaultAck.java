/*
 * DefaultAck.java 
 * Created on 2011-08-22
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.sm.events;

import com.veraxsystems.vxipmi.sm.StateMachine;
import com.veraxsystems.vxipmi.sm.states.State;

/**
 * Default message for acknowledging received IPMI responses. Performs a few {@link State} transitions.
 * 确认收到IPMI响应的默认消息。执行几个{@link状态}转换。
 * @see StateMachine
 */
public class DefaultAck extends StateMachineEvent {
	
}
