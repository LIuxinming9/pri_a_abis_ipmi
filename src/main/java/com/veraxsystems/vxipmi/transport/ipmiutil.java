package com.veraxsystems.vxipmi.transport;

import java.net.InetAddress;
import java.util.List;

import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.IpmiVersion;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatus;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatusResponseData;
import com.veraxsystems.vxipmi.coding.commands.fru.BaseUnit;
import com.veraxsystems.vxipmi.coding.commands.fru.GetFruInventoryAreaInfo;
import com.veraxsystems.vxipmi.coding.commands.fru.GetFruInventoryAreaInfoResponseData;
import com.veraxsystems.vxipmi.coding.commands.fru.ReadFruData;
import com.veraxsystems.vxipmi.coding.commands.fru.ReadFruDataResponseData;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSdr;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSdrRepositoryInfo;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSdrRepositoryInfoResponseData;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSdrResponseData;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSensorReading;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSensorReadingResponseData;
import com.veraxsystems.vxipmi.coding.commands.sdr.ReserveSdrRepository;
import com.veraxsystems.vxipmi.coding.commands.sdr.ReserveSdrRepositoryResponseData;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.CompactSensorRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.DeviceRelativeEntityAssiciationRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.EntityAssociationRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.EventOnlyRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.FruDeviceLocatorRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.FullSensorRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.GenericDeviceLocatorRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.ManagementControllerConfirmationRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.ManagementControllerDeviceLocatorRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.OemRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.RateUnit;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.ReadingType;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.SensorRecord;
import com.veraxsystems.vxipmi.coding.commands.sel.GetSelEntry;
import com.veraxsystems.vxipmi.coding.commands.sel.GetSelEntryResponseData;
import com.veraxsystems.vxipmi.coding.commands.sel.GetSelInfo;
import com.veraxsystems.vxipmi.coding.commands.sel.GetSelInfoResponseData;
import com.veraxsystems.vxipmi.coding.commands.sel.ReserveSel;
import com.veraxsystems.vxipmi.coding.commands.sel.ReserveSelResponseData;
import com.veraxsystems.vxipmi.coding.commands.session.GetChannelAuthenticationCapabilities;
import com.veraxsystems.vxipmi.coding.commands.session.GetChannelAuthenticationCapabilitiesResponseData;
import com.veraxsystems.vxipmi.coding.commands.session.OpenSession;
import com.veraxsystems.vxipmi.coding.commands.session.OpenSessionResponseData;
import com.veraxsystems.vxipmi.coding.commands.session.Rakp1;
import com.veraxsystems.vxipmi.coding.commands.session.Rakp1ResponseData;
import com.veraxsystems.vxipmi.coding.commands.session.Rakp3;
import com.veraxsystems.vxipmi.coding.commands.session.Rakp3ResponseData;
import com.veraxsystems.vxipmi.coding.payload.CompletionCode;
import com.veraxsystems.vxipmi.coding.payload.lan.IPMIException;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;
import com.veraxsystems.vxipmi.common.TypeConverter;

/**
 * Created by Administrator on 2018/5/22 0022.
 */
public class ipmiutil {

    /**
     * This is the value of Last Record ID (FFFFh). In order to retrieve the full set of SDR records, client must repeat
     * reading SDR records until MAX_REPO_RECORD_ID is returned as next record ID. For further information see section
     * 33.12 of the IPMI specification ver. 2.0
     */
    private static final int MAX_REPO_RECORD_ID = 65535;

    private static final String hostname = "192.168.5.9";

    private static final String username = "USERID";

    private static final String password = "PASSW0RD";

    /**
     * Size of the initial GetSdr message to get record header and size
     */
    private static final int INITIAL_CHUNK_SIZE = 8;

    /**
     * Chunk size depending on buffer size of the IPMI server. Bigger values will improve performance. If server is
     * returning "Cannot return number of requested data bytes." error during GetSdr command, CHUNK_SIZE should be
     * decreased.
     */
    private static final int CHUNK_SIZE = 16;

    /**
     * Size of SDR record header
     */
    private static final int HEADER_SIZE = 5;

    private int nextRecId;

    /**
     * @param args
     */
    public static void main(String[] args) {
        ipmiutil runner = new ipmiutil();
        try {
            runner.doRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doRun() throws Exception {
        // ID 0指示SDR中的第一个记录�?�下�?个IDS可以从记录中�?索出来�?��?�它们被组织在一个列表中，没有BMC命令来获取所有这些ID�?
        nextRecId = 0;

        // �?些BMC允许无保留地获取传感器记录，�?以我们尝试这样做�?
        int reservationId = 0;
        int lastReservationId = -1;

        // 创建连接�?
        IpmiConnector connector = new IpmiConnector(0);

        // 启动会话到远程主�?
        ConnectionHandle handle = startSession(connector, InetAddress.getByName(hostname), username, password, "",
                PrivilegeLevel.User);

        // 更改连接超时时间
        connector.setTimeout(handle, 2750);

        // 我们得到传感器数据，直到我们遇到ID�?65535，这意味�?这个记录是最后一个�??
        while (nextRecId < MAX_REPO_RECORD_ID) {
            SensorRecord record = null;
            
            //getSensorData1(connector, handle, reservationId); 
            int recordReadingId = -1;
            
            
            //测试使用
            if (record instanceof FullSensorRecord) {
                FullSensorRecord fsr = (FullSensorRecord) record;
                System.out.println("FullSensorRecord传感器名称：" + fsr.getName());
                System.out.println("实体ID："+fsr.getEntityId());
                System.out.println("传感器种类："+fsr.getSensorType());
                System.out.println("正常最大值："+fsr.getNormalMaximum());
                System.out.println("正常最小值："+fsr.getNormalMinimum());

            }
            if (record instanceof CompactSensorRecord) {
            	CompactSensorRecord fsr = (CompactSensorRecord) record;
                System.out.println("CompactSensorRecord传感器名称：" + fsr.getName());
                System.out.println("实体ID："+fsr.getEntityId());
                System.out.println("传感器种类："+fsr.getSensorType());
            }
            
            
            //原来的代码
          /*   try {
                // 填充传感器记录并获取存储库中的下�?条记录的ID
                record = getSensorData(connector, handle, reservationId);
                int recordReadingId = -1;

                // 判断接收到的数据是全部传感器记录还是压缩的记�?(详见IPMI规范)
                if (record instanceof FullSensorRecord) {
                    FullSensorRecord fsr = (FullSensorRecord) record;
                    recordReadingId = TypeConverter.byteToInt(fsr.getSensorNumber());
                    System.out.println("传感器名称：" + fsr.getName());

                } else if (record instanceof CompactSensorRecord) {
                    CompactSensorRecord csr = (CompactSensorRecord) record;
                    
                    recordReadingId = TypeConverter.byteToInt(csr.getSensorNumber());
                    System.out.println("传感器名称：" + csr.getName());
                }

                // 如果有记录，我们会得到响应数�?
                GetSensorReadingResponseData data2 = null;
                try {
                	//
                	System.out.println("recordReadingId"+recordReadingId);
                    if (recordReadingId >= 0&&recordReadingId!=51&&recordReadingId!=15) {
                    	//System.out.println("recordReadingId"+recordReadingId);
                        data2 = (GetSensorReadingResponseData) connector
                                .sendMessage(handle, new GetSensorReading(IpmiVersion.V20, handle.getCipherSuite(),
                                        AuthenticationType.RMCPPlus, recordReadingId));
                        if (record instanceof FullSensorRecord) {
                            FullSensorRecord rec = (FullSensorRecord) record;
                            // 解析传感器读取的记录信息
                            System.out.println("解析传感器读取的记录信息:" + data2.getSensorReading(rec) + rec.getSensorBaseUnit().toString()
                                   + (rec.getRateUnit() != RateUnit.None ? " per " + rec.getRateUnit() : ""));
                        }
                        if (record instanceof CompactSensorRecord) {
                            CompactSensorRecord rec = (CompactSensorRecord) record;
                            // 获取传感器状�?
                            List<ReadingType> events = data2.getStatesAsserted(rec.getSensorType(),
                                    rec.getEventReadingType());
                            StringBuilder s = new StringBuilder();
                            for (int i = 0; i < events.size(); ++i) {
                                s.append(events.get(i)).append(", ");
                            }
                            System.out.println("传感器状态：" + s);
                        }
                    }
                } catch (IPMIException e) {
                    if (e.getCompletionCode() == CompletionCode.DataNotPresent) {
                        e.printStackTrace();
                    } else {
                        throw e;
                    }
                }
            } catch (IPMIException e) {
                System.out.println("Getting new reservation ID");
                System.out.println("156: " + e.getMessage());
                // 如果获得传感器数据失败，�?查预留id是否已经失败�?
                if (lastReservationId == reservationId || e.getCompletionCode() != CompletionCode.ReservationCanceled)
                    throw e;
                lastReservationId = reservationId;

                // 如果失败的原因是取消预留，我们得到新的预留ID并重�?
                // 在获得所有传感器时，这会发生很多次，因为BMC不能管理并行会话，如果出现新的会话，BMC就不能管理旧的会话�??
                reservationId = ((ReserveSdrRepositoryResponseData) connector.sendMessage(handle, new ReserveSdrRepository(IpmiVersion.V20, handle.getCipherSuite(),
                        AuthenticationType.RMCPPlus))).getReservationId();
            }*/
        }

        // 关闭会话
        connector.closeSession(handle);
        // 关闭连接
        connector.closeConnection(handle);
        connector.tearDown();
    }

    /**
     * 启动会话
     *
     * @param connector      连接�?
     * @param address        连接地址
     * @param username       用户�?
     * @param password       密码
     * @param bmcKey         二次校验密码
     * @param privilegeLevel 特权级别
     * @return
     * @throws Exception
     */
    public ConnectionHandle startSession(IpmiConnector connector, InetAddress address, String username, String password, String bmcKey, PrivilegeLevel privilegeLevel) throws Exception {
        ConnectionHandle handle = connector.createConnection(address);
        CipherSuite cs;
        try {
            // 获取远程主机支持的密码套�?
            List<CipherSuite> suites = connector.getAvailableCipherSuites(handle);

            if (suites.size() > 3) {
                cs = suites.get(3);
            } else if (suites.size() > 2) {
                cs = suites.get(2);
            } else if (suites.size() > 1) {
                cs = suites.get(1);
            } else {
                cs = suites.get(0);
            }
            // 选择密码套件并请求会话特权级�?
            connector.getChannelAuthenticationCapabilities(handle, cs, privilegeLevel);
            // 打开会话并验�?
            connector.openSession(handle, username, password, bmcKey.getBytes());
        } catch (Exception e) {
            connector.closeConnection(handle);
            throw e;
        }
        return handle;
    }

    /**
     * 获取传感器数�?
     *
     * @param connector     连接�?
     * @param handle        连接句柄
     * @param reservationId 预留id
     * @return
     * @throws Exception
     */
    public SensorRecord getSensorData(IpmiConnector connector, ConnectionHandle handle, int reservationId)
            throws Exception {
        try {
            // BMC功能是有限的，这意味�?有时记录大小超过消息的最大大小�?�因为我们不知道这个记录的大小，�?以我们先把整个记录放在第�?位�??
            GetSdrResponseData data = (GetSdrResponseData) connector.sendMessage(handle, new GetSdr(IpmiVersion.V20,
                    handle.getCipherSuite(), AuthenticationType.RMCPPlus, reservationId, nextRecId));
            // 如果获得完整的记录，我们从接收到的数据创建传感记�?
            SensorRecord sensorDataToPopulate = SensorRecord.populateSensorRecord(data.getSensorRecordData());
            // 更新下一个记录的ID
            nextRecId = data.getNextRecordId();
            return sensorDataToPopulate;
        } catch (IPMIException e) {
            // 下面的错误代码意味着记录太大，无法在�?个块中发送�?�这意味�?我们�?要把数据分割成更小的部分�?
            if (e.getCompletionCode() == CompletionCode.CannotRespond
                    || e.getCompletionCode() == CompletionCode.UnspecifiedError) {
                System.out.println("Getting chunks");
                // 首先，我们得到记录的头来找出它的大小�?
                GetSdrResponseData data = (GetSdrResponseData) connector.sendMessage(handle, new GetSdr(
                        IpmiVersion.V20, handle.getCipherSuite(), AuthenticationType.RMCPPlus, reservationId,
                        nextRecId, 0, INITIAL_CHUNK_SIZE));
                // 记录的大小是记录的第五字节�?�它没有考虑页眉的大小，�?以我们需要添加它�?
                int recSize = TypeConverter.byteToInt(data.getSensorRecordData()[4]) + HEADER_SIZE;
                int read = INITIAL_CHUNK_SIZE;

                byte[] result = new byte[recSize];

                System.arraycopy(data.getSensorRecordData(), 0, result, 0, data.getSensorRecordData().length);

                // 我们得到了剩余的记录块（注意超过记录大小），因为这将导致BMC的错误�??
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

                // �?后，用收集的数据填充传感器记录�??
                SensorRecord sensorDataToPopulate = SensorRecord.populateSensorRecord(result);
                // 更新下一个记录的ID
                nextRecId = data.getNextRecordId();
                return sensorDataToPopulate;
            } else {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
    }
    public void getSensorData1(IpmiConnector connector, ConnectionHandle handle, int reservationId) throws Exception
             {
            // BMC功能是有限的，这意味�?有时记录大小超过消息的最大大小�?�因为我们不知道这个记录的大小，�?以我们先把整个记录放在第�?位�??
    	GetChassisStatusResponseData data = (GetChassisStatusResponseData) connector.sendMessage(handle, new GetChassisStatus(IpmiVersion.V20,
                handle.getCipherSuite(), AuthenticationType.RMCPPlus));
        // 如果获得完整的记录，我们从接收到的数据创建传感记�?
        //SensorRecord sensorDataToPopulate = SensorRecord.populateSensorRecord(data.getSensorRecordData());
          //  System.out.println(sensorDataToPopulate);
    }
}
