/*
 * GetFruInfoRunner.java Created on 2011-09-20
 * 
 * Copyright (c) Verax Systems 2012. All rights reserved.
 * 
 * This software is furnished under a license. Use, duplication, disclosure
 * and all other uses are restricted to the rights specified in the written
 * license agreement.
 */
package com.veraxsystems.vxipmi.transport;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthStyle;

import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.IpmiVersion;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;
import com.veraxsystems.vxipmi.coding.commands.fru.BaseUnit;
import com.veraxsystems.vxipmi.coding.commands.fru.GetFruInventoryAreaInfo;
import com.veraxsystems.vxipmi.coding.commands.fru.GetFruInventoryAreaInfoResponseData;
import com.veraxsystems.vxipmi.coding.commands.fru.ReadFruData;
import com.veraxsystems.vxipmi.coding.commands.fru.ReadFruDataResponseData;
import com.veraxsystems.vxipmi.coding.commands.fru.record.BoardInfo;
import com.veraxsystems.vxipmi.coding.commands.fru.record.FruRecord;
import com.veraxsystems.vxipmi.coding.commands.fru.record.MultiRecordInfo;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSdr;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSdrResponseData;
import com.veraxsystems.vxipmi.coding.commands.sdr.ReserveSdrRepository;
import com.veraxsystems.vxipmi.coding.commands.sdr.ReserveSdrRepositoryResponseData;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.FruDeviceLocatorRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.SensorRecord;
import com.veraxsystems.vxipmi.coding.payload.CompletionCode;
import com.veraxsystems.vxipmi.coding.payload.lan.IPMIException;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;
import com.veraxsystems.vxipmi.common.TypeConverter;

public class GetFruInfoRunner {

    /**
      *�������һ����¼ID (FFFFh)��ֵ��Ϊ�˼���ȫ��SDR��¼���ͻ��˱����ظ�
     *��ȡSDR��¼��ֱ��MAX_REPO_RECORD_ID��Ϊ��һ����¼ID����Ϊֹ
     * IPMI�淶ver��33.12��2.0
     */
    private static final int MAX_REPO_RECORD_ID = 65535;

    /**
     * ����Ĭ��FRU��Id
     */
    private static final int DEFAULT_FRU_ID = 0;

    /**
     *�ڵ���ReadFru�����д�������ݴ�С�������ֵ��������ܡ������������
	    ��ReadFru�����ڼ䷵�ء������е���Ч�����ֶΡ�����FRU_READ_PACKET_SIZEӦ����
     *���١�
     */
    private static final int FRU_READ_PACKET_SIZE = 16;

    /**
     * ��ȡ��¼ͷ�ʹ�С�ĳ�ʼGetSdr��Ϣ�Ĵ�С
     */
    private static final int INITIAL_CHUNK_SIZE = 8;

    /**
     *���Сȡ����IPMI�������Ļ�������С�������ֵ��������ܡ������������
	   ��GetSdr�����ڼ䣬CHUNK_SIZEӦ���Ǵ����
     *���١�
     */
    private static final int CHUNK_SIZE = 16;

    /**
     * SDR��¼ͷ�Ĵ�С
     */
    private static final int HEADER_SIZE = 5;

    private static int nextRecId = 0;


    private static final String hostname = "192.168.1.106";

    private static final String username = "lenovo";

    private static final String password = "len0vO";
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        IpmiConnector connector;

      //������������ָ������ͨ�ŵĶ˿�
      //ʹ��Զ��������UDP�㿪ʼ��������˿ڣ�����
      // 2��������������ͬһ�˿���ͬʱ������
      //�ڶ��������ǿ�ѡ�ġ������������׽��ְ󶨵�
      //�ض���IP�ӿڡ��������������ں˵�ѡ��
      //һ��IP��ַ��


        connector = new IpmiConnector(6000);
        System.out.println("Connector created");

        //�������Ӳ���ȡ�����ָ����IP��ַ
        //Զ����������������ConnectionManager��ע�ᣬ
        //��Ҫ������������������ʶ����
        //(Ŀ��IP��ַ�ǲ����ģ���Ϊ���ǿ��Դ�����
        //���ӵ�ͬһ����)
        ConnectionHandle handle = connector.createConnection(InetAddress.getByName(hostname));
        System.out.println("Connection created");

        //ͨ��getAvailableCipherSuites�ͻ�ȡ���������׼��б�
        //����ѡ��һ�����ڻỰ�н�һ��ʹ�á�

        CipherSuite cs = connector.getAvailableCipherSuites(handle).get(3);
        System.out.println("Cipher suite picked");

        //ΪԶ�������ṩ��ѡ�������׼���Ȩ�޼���
        //�����ڿ�ʼ���������Ӿ����������Щ��Ϣ��

        connector.getChannelAuthenticationCapabilities(handle, cs, PrivilegeLevel.Administrator);
        System.out.println("Channel authentication capabilities receivied");

       //�����Ự���ṩ�û��������룬�Լ���ѡ��
       // BMC��Կ(����Զ����������˫��Կ�����֤ʱ��
        //�����������Ӧ����null)

        connector.openSession(handle, username, password, null);
        System.out.println("Session open");
        ReserveSdrRepositoryResponseData reservation = (ReserveSdrRepositoryResponseData) connector.sendMessage(handle,
                new ReserveSdrRepository(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus));

        processFru(connector, handle, DEFAULT_FRU_ID);

        while (nextRecId < MAX_REPO_RECORD_ID) {

            System.out.println(">>Sending request for record " + nextRecId);

            // ����SDR�洢���е�FruDeviceLocatorRecords�����а�������fru��id(�������)

            try {
                SensorRecord record = getSensorData(connector, handle, reservation.getReservationId());
                if (record instanceof FruDeviceLocatorRecord) {
                    FruDeviceLocatorRecord fruLocator = (FruDeviceLocatorRecord) record;

                    System.out.println(fruLocator.getName());
                    System.out.println(fruLocator.getDeviceType());
                    System.out.println("FRU entity ID: " + fruLocator.getFruEntityId());
                    System.out.println("FRU access address: " + fruLocator.getDeviceAccessAddress());
                    System.out.println("FRU device ID: " + fruLocator.getDeviceId());
                    System.out.println("FRU logical: " + fruLocator.isLogical());
                    System.out.println("FRU access lun: " + fruLocator.getAccessLun());
                    if (fruLocator.isLogical()) {
                       // processFru(connector, handle, fruLocator.getDeviceId());
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                reservation = (ReserveSdrRepositoryResponseData) connector.sendMessage(handle,
                        new ReserveSdrRepository(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus));
            }
        }

        // Close the session
        connector.closeSession(handle);
        System.out.println("Session closed");

        // �ر����ӹ��������ͷ��������˿ڡ�
        connector.tearDown();
        System.out.println("Connection manager closed");
    }

    private static void processFru(IpmiConnector connector, ConnectionHandle handle, int fruId) throws Exception {
        List<ReadFruDataResponseData> fruData = new ArrayList<ReadFruDataResponseData>();

        // ��ȡFRU���������Ϣ
        GetFruInventoryAreaInfoResponseData info = (GetFruInventoryAreaInfoResponseData) connector.sendMessage(handle,
                new GetFruInventoryAreaInfo(IpmiVersion.V20, handle.getCipherSuite(), AuthenticationType.RMCPPlus,
                        fruId));
        int size = info.getFruInventoryAreaSize();
        BaseUnit unit = info.getFruUnit();

        //���ڵ���FRU��Ŀ�Ĵ�С���ܳ���
        //ͨ��IPMI���͵���Ϣ������ֶζ�ȡ

        for (int i = 0; i < size; i += FRU_READ_PACKET_SIZE) {
            int cnt = FRU_READ_PACKET_SIZE;
            if (i + cnt > size) {
                cnt = size % FRU_READ_PACKET_SIZE;
            }
            try {
                // ��ȡ����od FRU����

                ReadFruDataResponseData data = (ReadFruDataResponseData) connector.sendMessage(handle, new ReadFruData(
                        IpmiVersion.V20, handle.getCipherSuite(), AuthenticationType.RMCPPlus, fruId, unit, i, cnt));
                fruData.add(data);

            } catch (Exception e) {
                System.out.println("Error while sending ReadFruData command : " + e.getMessage());
            }
        }

        try {
            // �ռ����������ݺ����ǿ��Զ��������Ϻͽ���
            List<FruRecord> records = ReadFruData.decodeFruData(fruData);

            System.out.println("----------------------------------------------");
            System.out.println("Received FRU records");
            System.out.println("----------------------------------------------");
            for (FruRecord record : records) {
                // ���磬�������ǿ�����ʾ���յ��Ĺ���board����Ϣ
            		System.out.println("record_________"+record);
            		if (record instanceof MultiRecordInfo) {
            			System.out.println("22222222222222"+record);
            		}
                if (record instanceof BoardInfo) {
                    BoardInfo bi = (BoardInfo) record;
                    System.out.println(bi.getBoardSerialNumber() + " " + bi.getBoardProductName() + " "
                            + bi.getBoardPartNumber() + " " + bi.getBoardManufacturer());
                } else {
                    System.out.println("Other format: " + record.getClass().getSimpleName());
                }
            }
        } catch (Exception e) {
            System.out.println("Error while parsing FRU record: " + e.getMessage());
        }
    }

    public static SensorRecord getSensorData(IpmiConnector connector, ConnectionHandle handle, int reservationId)
            throws Exception {
        try {
        	// BMC�����������޵�-����ζ����ʱ
        	//��¼��С������Ϣ������С����Ϊ���ǲ�
        	//֪�����ų�Ƭ�ĳߴ磬���Ǿ���Ū��
        	//�ȳ�һ����

            GetSdrResponseData data = (GetSdrResponseData) connector.sendMessage(handle, new GetSdr(IpmiVersion.V20,
                    handle.getCipherSuite(), AuthenticationType.RMCPPlus, reservationId, nextRecId));
            //�����ȡ������¼�ɹ������ǽ�����SensorRecord
            //���յ����ݡ�

            SensorRecord sensorDataToPopulate = SensorRecord.populateSensorRecord(data.getSensorRecordData());
            // ����������һ����¼��ID

            nextRecId = data.getNextRecordId();
            return sensorDataToPopulate;
        } catch (IPMIException e) {
        	//����Ĵ��������ζ�ż�¼̫����
        	//��һ�����з��͡�����ζ��������Ҫ�ָ�����
        	//С������

            if (e.getCompletionCode() == CompletionCode.CannotRespond
                    || e.getCompletionCode() == CompletionCode.UnspecifiedError) {
                System.out.println("Getting chunks");
                // ���ȣ����ǻ�ȡ��¼�ı������ҳ����Ĵ�С
                GetSdrResponseData data = (GetSdrResponseData) connector.sendMessage(handle, new GetSdr(
                        IpmiVersion.V20, handle.getCipherSuite(), AuthenticationType.RMCPPlus, reservationId,
                        nextRecId, 0, INITIAL_CHUNK_SIZE));
              //��¼��СΪ��¼��5�ֽڡ�������Ҫ
              //���ǵ�����Ĵ�С��������Ҫ�������

                int recSize = TypeConverter.byteToInt(data.getSensorRecordData()[4]) + HEADER_SIZE;
                int read = INITIAL_CHUNK_SIZE;

                byte[] result = new byte[recSize];

                System.arraycopy(data.getSensorRecordData(), 0, result, 0, data.getSensorRecordData().length);

              //���ǰ�����ļ�¼�ֳɼ���(С��
              //������¼��С����Ϊ��ᵼ��BMC
              //����

                while (read < recSize) {
                    int bytesToRead = CHUNK_SIZE;
                    if (recSize - read < bytesToRead) {
                        bytesToRead = recSize - read;
                    }
                    GetSdrResponseData part = (GetSdrResponseData) connector.sendMessage(handle, new GetSdr(
                            IpmiVersion.V20, handle.getCipherSuite(), AuthenticationType.RMCPPlus, reservationId,
                            nextRecId, read, bytesToRead));

                    System.arraycopy(part.getSensorRecordData(), 0, result, read, bytesToRead);

                    System.out.println("Received part");

                    read += bytesToRead;
                }

                //����������ռ�����������䴫������¼
                //���ݡ���

                SensorRecord sensorDataToPopulate = SensorRecord.populateSensorRecord(result);
                // ����������һ����¼��ID

                nextRecId = data.getNextRecordId();
                return sensorDataToPopulate;
            } else {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
