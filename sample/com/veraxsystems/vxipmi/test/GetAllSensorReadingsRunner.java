package com.veraxsystems.vxipmi.test;

import java.net.InetAddress;
import java.util.List;

import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.IpmiVersion;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSdr;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSdrResponseData;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSensorReading;
import com.veraxsystems.vxipmi.coding.commands.sdr.GetSensorReadingResponseData;
import com.veraxsystems.vxipmi.coding.commands.sdr.ReserveSdrRepository;
import com.veraxsystems.vxipmi.coding.commands.sdr.ReserveSdrRepositoryResponseData;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.CompactSensorRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.FullSensorRecord;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.RateUnit;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.ReadingType;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.SensorRecord;
import com.veraxsystems.vxipmi.coding.payload.CompletionCode;
import com.veraxsystems.vxipmi.coding.payload.lan.IPMIException;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;
import com.veraxsystems.vxipmi.common.PropertiesManager;
import com.veraxsystems.vxipmi.common.TypeConverter;

public class GetAllSensorReadingsRunner {

	/**
	 * GetAllSensorReadingsRunner 获取所有传感器读数
	*这是最后一个记录ID (FFFFh)的值。为了检索全部SDR记录，客户端必须重复
	*读取SDR记录，直到MAX_REPO_RECORD_ID作为下一个记录ID返回为止
	* IPMI规范ver的33.12。2.0
	*/

    private static final int MAX_REPO_RECORD_ID = 65535;

    private static final String hostname = "192.168.1.1";

    private static final String username = "user";

    private static final String password = "pass";

    /**
     * 获取记录头和大小的初始GetSdr消息的大小
     */
    private static final int INITIAL_CHUNK_SIZE = 8;

    /**
    *块大小取决于IPMI服务器的缓冲区大小。更大的值将提高性能。如果服务器是
	在GetSdr命令期间，CHUNK_SIZE应该是错误的减少。
     */
    private static final int CHUNK_SIZE = 16;

    /**
     * SDR记录头的大小
     */
    private static final int HEADER_SIZE = 5;

    private int nextRecId;

    /**
     * @param args
     */
    public static void main(String[] args) {
        GetAllSensorReadingsRunner runner = new GetAllSensorReadingsRunner();
        try {
            // 更改默认超时值
            PropertiesManager.getInstance().setProperty("timeout", "2500");
            runner.doRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doRun() throws Exception {
    	// Id 0表示SDR中的第一条记录。接下来可以从其中检索id
    	//记录——它们被组织在一个列表中，没有BMC命令
    	//把它们都弄好。
        nextRecId = 0;

       //有些监控中心允许毫无保留地获取传感器记录，所以我们尝试这样做
       //先那样做
        int reservationId = 0;
        int lastReservationId = -1;

        // Create the connector
        IpmiConnector connector = new IpmiConnector(0);

       //启动到远程主机的会话。我们假设有两个键
       //身份验证未启用，因此BMC密钥为空(请参阅
       // #startSession获取详细信息)。

        ConnectionHandle handle = startSession(connector, InetAddress.getByName(hostname), username, password, "",
                PrivilegeLevel.User);

        // 更改此特定连接的超时(后续连接的默认值不变)
        connector.setTimeout(handle, 2750);
        
       //我们得到传感器数据，直到我们遇到ID = 65535，这意味着
       //这是最后一张唱片。

        while (nextRecId < MAX_REPO_RECORD_ID) {

            SensorRecord record = null;

            try {
            	//填充传感器记录并获取下一条记录的ID
            	//存储库(详细信息请参见#getSensorData)。
                record = getSensorData(connector, handle, reservationId);

                int recordReadingId = -1;

               //我们检查收到的记录是FullSensorRecord还是FullSensorRecord
              // CompactSensorRecord，因为这些类型有读数
              //与之相关(详细信息请参见IPMI规范)。

                if (record instanceof FullSensorRecord) {
                    FullSensorRecord fsr = (FullSensorRecord) record;
                    recordReadingId = TypeConverter.byteToInt(fsr.getSensorNumber());
                    System.out.println(fsr.getName());

                } else if (record instanceof CompactSensorRecord) {
                    CompactSensorRecord csr = (CompactSensorRecord) record;
                    recordReadingId = TypeConverter.byteToInt(csr.getSensorNumber());
                    System.out.println(csr.getName());
                }

              //如果我们的记录有关联的读数，我们就会收到为它的请求

                GetSensorReadingResponseData data2 = null;
                try {
                    if (recordReadingId >= 0) {
                        data2 = (GetSensorReadingResponseData) connector
                                .sendMessage(handle, new GetSensorReading(IpmiVersion.V20, handle.getCipherSuite(),
                                        AuthenticationType.RMCPPlus, recordReadingId));
                        if (record instanceof FullSensorRecord) {
                            FullSensorRecord rec = (FullSensorRecord) record;
                          //使用检索到的信息解析传感器读取
                          //来自传感器记录。看到
                          // FullSensorRecord#calcFormula获取详细信息。

                            System.out.println(data2.getSensorReading(rec) + " " + rec.getSensorBaseUnit().toString()
                                    + (rec.getRateUnit() != RateUnit.None ? " per " + rec.getRateUnit() : ""));
                        }
                        if (record instanceof CompactSensorRecord) {
                            CompactSensorRecord rec = (CompactSensorRecord) record;
                            // 获取传感器断言的状态
                            List<ReadingType> events = data2.getStatesAsserted(rec.getSensorType(),
                                    rec.getEventReadingType());
                            String s = "";
                            for (int i = 0; i < events.size(); ++i) {
                                s += events.get(i) + ", ";
                            }
                            System.out.println(s);

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

               //如果获取传感器数据失败，我们检查它是否已经失败
               //用这个预订号。

                if (lastReservationId == reservationId)
                    throw e;
                lastReservationId = reservationId;

              //如果失败的原因是取消了
              //预订，我们得到新的预订id并重试。这可以
              //在获取所有传感器的过程中会发生很多次，因为BMC不能
              //管理并行会话，旧会话无效，如果是新会话
              //出现。

                reservationId = ((ReserveSdrRepositoryResponseData) connector
                        .sendMessage(handle, new ReserveSdrRepository(IpmiVersion.V20, handle.getCipherSuite(),
                                AuthenticationType.RMCPPlus))).getReservationId();
            }
        }

        // Close the session
        connector.closeSession(handle);
        System.out.println("Session closed");
        // Close the connection
        connector.closeConnection(handle);
        connector.tearDown();
        System.out.println("Connection manager closed");
    }

    public ConnectionHandle startSession(IpmiConnector connector, InetAddress address, String username,
            String password, String bmcKey, PrivilegeLevel privilegeLevel) throws Exception {

        // 创建连接的句柄，它将是连接的标识符
        ConnectionHandle handle = connector.createConnection(address);

        CipherSuite cs;

        try {
            // 获取远程主机支持的密码套件
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
          //选择密码套件和请求的权限级别
          //会话

            connector.getChannelAuthenticationCapabilities(handle, cs, privilegeLevel);

            // 打开会话并进行身份验证
            connector.openSession(handle, username, password, bmcKey.getBytes());
        } catch (Exception e) {
            connector.closeConnection(handle);
            throw e;
        }

        return handle;
    }

    public SensorRecord getSensorData(IpmiConnector connector, ConnectionHandle handle, int reservationId)
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
            // System.out.println(e.getCompletionCode() + ": " + e.getMessage());
        	//下面的错误代码意味着记录太大了
        	//在一个块中发送。这意味着我们需要分割数据
        	//小部件。

            if (e.getCompletionCode() == CompletionCode.CannotRespond
                    || e.getCompletionCode() == CompletionCode.UnspecifiedError) {
                System.out.println("Getting chunks");
                // 首先，我们获取记录的标题来找出它的大小。
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
