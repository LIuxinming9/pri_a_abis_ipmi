/*
 * GetFruInfoRunner.java Created on 2011-09-20
 * 
 * Copyright (c) Verax Systems 2012. All rights reserved.
 * 
 * This software is furnished under a license. Use, duplication, disclosure
 * and all other uses are restricted to the rights specified in the written
 * license agreement.
 */
package com.veraxsystems.vxipmi.test;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

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
      *这是最后一个记录ID (FFFFh)的值。为了检索全部SDR记录，客户端必须重复
     *读取SDR记录，直到MAX_REPO_RECORD_ID作为下一个记录ID返回为止
     * IPMI规范ver的33.12。2.0
     */
    private static final int MAX_REPO_RECORD_ID = 65535;

    /**
     * 内置默认FRU的Id
     */
    private static final int DEFAULT_FRU_ID = 0;

    /**
     *在单个ReadFru命令中传输的数据大小。更大的值将提高性能。如果服务器是
	    在ReadFru命令期间返回“请求中的无效数据字段”错误，FRU_READ_PACKET_SIZE应该是
     *减少。
     */
    private static final int FRU_READ_PACKET_SIZE = 16;

    /**
     * 获取记录头和大小的初始GetSdr消息的大小
     */
    private static final int INITIAL_CHUNK_SIZE = 8;

    /**
     *块大小取决于IPMI服务器的缓冲区大小。更大的值将提高性能。如果服务器是
	   在GetSdr命令期间，CHUNK_SIZE应该是错误的
     *减少。
     */
    private static final int CHUNK_SIZE = 16;

    /**
     * SDR记录头的大小
     */
    private static final int HEADER_SIZE = 5;

    private static int nextRecId = 0;


    private static final String hostname = "192.168.1.1";

    private static final String username = "user";

    private static final String password = "pass";
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        IpmiConnector connector;

      //创建连接器，指定用于通信的端口
      //使用远程主机。UDP层开始监听这个端口，所以
      // 2个连接器可以在同一端口上同时工作。
      //第二个参数是可选的——它将基础套接字绑定到
      //特定的IP接口。跳过它将导致内核的选择
      //一个IP地址。


        connector = new IpmiConnector(6000);
        System.out.println("Connector created");

        //创建连接并获取句柄，指定的IP地址
        //远程主机。连接正在ConnectionManager中注册，
        //需要这个句柄在其他连接中识别它
        //(目标IP地址是不够的，因为我们可以处理多个
        //连接到同一主机)
        ConnectionHandle handle = connector.createConnection(InetAddress.getByName(hostname));
        System.out.println("Connection created");

        //通过getAvailableCipherSuites和获取可用密码套件列表
        //从中选择一个将在会话中进一步使用。

        CipherSuite cs = connector.getAvailableCipherSuites(handle).get(3);
        System.out.println("Cipher suite picked");

        //为远程主机提供所选的密码套件和权限级别。
        //从现在开始，您的连接句柄将包含这些信息。

        connector.getChannelAuthenticationCapabilities(handle, cs, PrivilegeLevel.User);
        System.out.println("Channel authentication capabilities receivied");

       //启动会话，提供用户名和密码，以及可选的
       // BMC密钥(仅当远程主机启用双密钥身份验证时，
        //否则这个参数应该是null)

        connector.openSession(handle, username, password, null);
        System.out.println("Session open");
        ReserveSdrRepositoryResponseData reservation = (ReserveSdrRepositoryResponseData) connector.sendMessage(handle,
                new ReserveSdrRepository(IpmiVersion.V20, cs, AuthenticationType.RMCPPlus));

        processFru(connector, handle, DEFAULT_FRU_ID);

        while (nextRecId < MAX_REPO_RECORD_ID) {

            System.out.println(">>Sending request for record " + nextRecId);

            // 搜索SDR存储库中的FruDeviceLocatorRecords，其中包含附加fru的id(如果存在)

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
                        processFru(connector, handle, fruLocator.getDeviceId());
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

        // 关闭连接管理器并释放侦听器端口。
        connector.tearDown();
        System.out.println("Connection manager closed");
    }

    private static void processFru(IpmiConnector connector, ConnectionHandle handle, int fruId) throws Exception {
        List<ReadFruDataResponseData> fruData = new ArrayList<ReadFruDataResponseData>();

        // 获取FRU库存区域信息
        GetFruInventoryAreaInfoResponseData info = (GetFruInventoryAreaInfoResponseData) connector.sendMessage(handle,
                new GetFruInventoryAreaInfo(IpmiVersion.V20, handle.getCipherSuite(), AuthenticationType.RMCPPlus,
                        fruId));

        int size = info.getFruInventoryAreaSize();
        BaseUnit unit = info.getFruUnit();

        //由于单个FRU条目的大小可能超过
        //通过IPMI发送的消息，必须分段读取

        for (int i = 0; i < size; i += FRU_READ_PACKET_SIZE) {
            int cnt = FRU_READ_PACKET_SIZE;
            if (i + cnt > size) {
                cnt = size % FRU_READ_PACKET_SIZE;
            }
            try {
                // 获取单包od FRU数据

                ReadFruDataResponseData data = (ReadFruDataResponseData) connector.sendMessage(handle, new ReadFruData(
                        IpmiVersion.V20, handle.getCipherSuite(), AuthenticationType.RMCPPlus, fruId, unit, i, cnt));

                fruData.add(data);

            } catch (Exception e) {
                System.out.println("Error while sending ReadFruData command : " + e.getMessage());
            }
        }

        try {
            // 收集完所有数据后，我们可以对其进行组合和解析
            List<FruRecord> records = ReadFruData.decodeFruData(fruData);

            System.out.println("----------------------------------------------");
            System.out.println("Received FRU records");
            System.out.println("----------------------------------------------");
            for (FruRecord record : records) {
                // 例如，现在我们可以显示接收到的关于board的信息

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
        	// BMC的能力是有限的-这意味着有时
        	//记录大小超过消息的最大大小。因为我们不
        	//知道这张唱片的尺寸，我们尽量弄到
        	//先吃一整块

            GetSdrResponseData data = (GetSdrResponseData) connector.sendMessage(handle, new GetSdr(IpmiVersion.V20,
                    handle.getCipherSuite(), AuthenticationType.RMCPPlus, reservationId, nextRecId));
            //如果获取整个记录成功，我们将创建SensorRecord
            //接收的数据…

            SensorRecord sensorDataToPopulate = SensorRecord.populateSensorRecord(data.getSensorRecordData());
            // …并更新下一条记录的ID

            nextRecId = data.getNextRecordId();
            return sensorDataToPopulate;
        } catch (IPMIException e) {
        	//下面的错误代码意味着记录太大了
        	//在一个块中发送。这意味着我们需要分割数据
        	//小部件。

            if (e.getCompletionCode() == CompletionCode.CannotRespond
                    || e.getCompletionCode() == CompletionCode.UnspecifiedError) {
                System.out.println("Getting chunks");
                // 首先，我们获取记录的标题来找出它的大小
                GetSdrResponseData data = (GetSdrResponseData) connector.sendMessage(handle, new GetSdr(
                        IpmiVersion.V20, handle.getCipherSuite(), AuthenticationType.RMCPPlus, reservationId,
                        nextRecId, 0, INITIAL_CHUNK_SIZE));
              //记录大小为记录的5字节。它不需要
              //考虑到标题的大小，我们需要添加它。

                int recSize = TypeConverter.byteToInt(data.getSensorRecordData()[4]) + HEADER_SIZE;
                int read = INITIAL_CHUNK_SIZE;

                byte[] result = new byte[recSize];

                System.arraycopy(data.getSensorRecordData(), 0, result, 0, data.getSensorRecordData().length);

              //我们把其余的记录分成几块(小心
              //超过记录大小，因为这会导致BMC
              //错误。

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

                //最后，我们用收集到的数据填充传感器记录
                //数据……

                SensorRecord sensorDataToPopulate = SensorRecord.populateSensorRecord(result);
                // …并更新下一条记录的ID

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
