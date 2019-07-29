/*
 * VxipmiRunner.java 
 * Created on 2011-09-20
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.test;

import java.net.InetAddress;
import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.IpmiVersion;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;
import com.veraxsystems.vxipmi.coding.commands.chassis.ChassisControl;
import com.veraxsystems.vxipmi.coding.commands.chassis.ChassisControlResponseData;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatus;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatusResponseData;
import com.veraxsystems.vxipmi.coding.commands.chassis.PowerCommand;
import com.veraxsystems.vxipmi.coding.commands.session.SetSessionPrivilegeLevel;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;

public class ChassisControlRunner {

    /**
     * ChassisControlRunner底盘控制运行
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        IpmiConnector connector;

	      //创建连接器，指定用于通信的端口
	      //使用远程主机。UDP层开始监听这个端口，所以
	      // 2个连接器可以在同一端口上同时工作。

        connector = new IpmiConnector(6000);
        System.out.println("Connector created");
        
	        //创建连接并获取句柄，指定的IP地址为
	        //远程主机。连接正在ConnectionManager中注册，
	        //需要这个句柄在其他连接中识别它
	        //(目标IP地址是不够的，因为我们可以处理多个
	        //连接到同一主机)

        ConnectionHandle handle = connector.createConnection(InetAddress.getByName("192.168.1.106"));
        System.out.println("Connection created");

	       //通过getAvailableCipherSuites和获取可用密码套件列表
	       //从中选择一个将在会话中进一步使用。

        CipherSuite cs = connector.getAvailableCipherSuites(handle).get(3);
        System.out.println("Cipher suite picked");

	      //为远程主机提供所选的密码套件和权限级别。
	      //从现在开始，您的连接句柄将包含这些信息。

        connector.getChannelAuthenticationCapabilities(handle, cs, PrivilegeLevel.Administrator);
        System.out.println("Channel authentication capabilities receivied");

        //启动会话，提供用户名和密码，以及可选的
        // BMC密钥(仅当远程主机启用双密钥身份验证时，
        //否则这个参数应该是null)

        connector.openSession(handle, "user", "pass", null);
        System.out.println("Session open");

        //发送一些消息并读取响应
        GetChassisStatusResponseData rd = (GetChassisStatusResponseData) connector.sendMessage(handle,
                new GetChassisStatus(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus));

        System.out.println("Received answer");
        System.out.println("System power state is " + (rd.isPowerOn() ? "up" : "down"));

        //将会话权限级别设置为administrator，因为ChassisControl命令需要这个级别
        connector.sendMessage(handle, new SetSessionPrivilegeLevel(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus,
                PrivilegeLevel.Administrator));

        ChassisControl chassisControl = null;
        
        //打开或关闭电源
        if (!rd.isPowerOn()) {
            chassisControl = new ChassisControl(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus, PowerCommand.PowerUp);
        } else {
            chassisControl = new ChassisControl(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus,
                    PowerCommand.PowerDown);
        }

        ChassisControlResponseData data = (ChassisControlResponseData) connector.sendMessage(handle, chassisControl);

        //关闭会话
        connector.closeSession(handle);
        System.out.println("Session closed");

        //关闭连接管理器并释放侦听器端口。
        connector.tearDown();
        System.out.println("Connection manager closed");
    }

}
