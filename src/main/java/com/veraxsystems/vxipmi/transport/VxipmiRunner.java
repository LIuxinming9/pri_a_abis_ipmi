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
package com.veraxsystems.vxipmi.transport;

import java.net.InetAddress;
import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.IpmiVersion;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatus;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatusResponseData;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;

public class VxipmiRunner {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		IpmiConnector connector;

		//������������ָ������ͨ�ŵĶ˿�
		//ʹ��Զ��������UDP�㿪ʼ��������˿ڣ�����
		// 2��������������ͬһ�˿���ͬʱ������

		connector = new IpmiConnector(6000);
		System.out.println("Connector created");

		//�������Ӳ���ȡ�����ָ����IP��ַΪ
		//Զ����������������ConnectionManager��ע�ᣬ
		//��Ҫ������������������ʶ����
		//(Ŀ��IP��ַ�ǲ����ģ���Ϊ���ǿ��Դ�����
		//���ӵ�ͬһ����)

		ConnectionHandle handle = connector.createConnection(InetAddress
				.getByName("192.168.0.120"));
		System.out.println("Connection created");

		//ͨ��getAvailableCipherSuites�ͻ�ȡ���������׼��б�
		//����ѡ��һ�����ڻỰ�н�һ��ʹ�á�

		
		CipherSuite cs = connector.getAvailableCipherSuites(handle).get(3);
		System.out.println("Cipher suite picked");

		//ΪԶ�������ṩ��ѡ�������׼���Ȩ�޼���
		//�����ڿ�ʼ���������Ӿ����������Щ��Ϣ��

		connector.getChannelAuthenticationCapabilities(handle, cs,
				PrivilegeLevel.Administrator);
		System.out.println("Channel authentication capabilities receivied");

		//�����Ự���ṩ�û��������룬�Լ���ѡ��
		// BMC��Կ(����Զ����������˫��Կ�����֤ʱ��
		//�����������Ӧ����null)

		connector.openSession(handle, "root", "calvin", null);
		System.out.println("Session open");

		// ����һЩ��Ϣ����ȡ��Ӧ
		GetChassisStatusResponseData rd = (GetChassisStatusResponseData) connector
				.sendMessage(handle, new GetChassisStatus(IpmiVersion.V20, cs,
						AuthenticationType.RMCPPlus));

		System.out.println("Received answer");
		System.out.println("System power state is "
				+ (rd.isPowerOn() ? "up" : "down"));

		// Close the session
		connector.closeSession(handle);
		System.out.println("Session closed");

		// �ر����ӹ��������ͷ��������˿ڡ�

		connector.tearDown();
		System.out.println("Connection manager closed");
	}

}
