package com.gydz.util;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.security.UsmUserEntry;
import org.snmp4j.security.UsmUserTable;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

/** 
 * 本类用于监听代理进程的Trap信息 
 *  
 * @author gfw2306
 * 
 */  
public class SnmpTrapReceive implements CommandResponder {  

    private String username1 = "snmp";
    private String username2 = "user2";
    private String username3 = "user3";
    private String username4 = "user4";
    private String authPassword = "snmp123456";
    private String privPassword = "password2";

    private MultiThreadedMessageDispatcher dispatcher;  
    private Snmp snmp = null;  
    private Address listenAddress;  
    private ThreadPool threadPool;  

    public SnmpTrapReceive() {  
        // BasicConfigurator.configure();  
    }  

    private void init() throws UnknownHostException, IOException {
        /*threadPool = ThreadPool.create("Trap", 2);  
        dispatcher = new MultiThreadedMessageDispatcher(threadPool,
                new MessageDispatcherImpl());  
        listenAddress = GenericAddress.parse(System.getProperty(  
                "snmp4j.listenAddress", "udp:10.195.88.96/162")); // 本地IP与监听端口  
        TransportMapping transport;  
        // 对TCP与UDP协议进行处理  
        if (listenAddress instanceof UdpAddress) {  
            transport = new DefaultUdpTransportMapping(  
                    (UdpAddress) listenAddress);  
        } else {  
            transport = new DefaultTcpTransportMapping(  
                    (TcpAddress) listenAddress);  
        }  
        snmp = new Snmp(dispatcher, transport);  
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());  
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());  
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());  
        UsmUser usmUser = new UsmUser(new OctetString(username), AuthMD5.ID,
                new OctetString(authPassword), Priv3DES.ID,
                new OctetString(privPassword));

        USM usm = new USM(SecurityProtocols.getInstance().addDefaultProtocols(), new OctetString(MPv3  
                .createLocalEngineID()), 0); 
        usm.addUser(usmUser);
        SecurityModels.getInstance().addSecurityModel(usm);  
        snmp.listen();  



        //add the enginID in the trap 
        //OctetString engineID = new OctetString(MPv3.createLocalEngineID()); 
        byte[] enginId = "TEO_ID".getBytes();
        OctetString engineID = new OctetString(enginId);
        //create and add the userSecurityModel 
        USM usm = new USM(SecurityProtocols.getInstance(),engineID, 0);   
        SecurityModels.getInstance().addSecurityModel(usm);   

        //add the securityProtocols,you can skip it if your users are noAuthNoPriv 
        SecurityProtocols.getInstance().addDefaultProtocols();         


        //create and add the user 
        UsmUser usmUser = new UsmUser(new OctetString(username), AuthMD5.ID,
                new OctetString(authPassword), Priv3DES.ID,
                new OctetString(privPassword));  
        usm.addUser(usmUser);


        //snmp.getUSM().addUser(usmUser); 
        snmp.listen(); 
        */





        /*******************************************使用处*************************************************/

        //创建接收SnmpTrap的线程池，参数： 线程名称及线程数
        threadPool = ThreadPool.create("Trap", 2);  
        dispatcher = new MultiThreadedMessageDispatcher(threadPool,  
                new MessageDispatcherImpl());
        //监听端的 ip地址 和 监听端口号
        
        String listenIpPort = PropertyUtil.getProperty("listenIpPort");
        listenAddress = GenericAddress.parse(System.getProperty(  
                "snmp4j.listenAddress", listenIpPort));

        TransportMapping<?> transport;  
        if (listenAddress instanceof UdpAddress) {
        	System.out.println(listenAddress);
            transport = new DefaultUdpTransportMapping((UdpAddress)listenAddress);  
        }else{
            transport = new DefaultTcpTransportMapping((TcpAddress)listenAddress);  
        }  
        snmp = new Snmp(dispatcher, transport);
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
        //MPv3.setEnterpriseID(35904);
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());


        USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3
                .createLocalEngineID()),0);


        SecurityModels.getInstance().addSecurityModel(usm);
        // 添加安全协议,如果没有发过来的消息没有身份认证,可以跳过此段代码
        SecurityProtocols.getInstance().addDefaultProtocols();
        // 创建和添加用户
        OctetString userName1 = new OctetString(username1);
        OctetString userName2 = new OctetString(username2);
        //OctetString userName3 = new OctetString(username3);
        //OctetString userName4 = new OctetString(username4);
        OctetString authPass = new OctetString(authPassword);
        OctetString privPass = new OctetString("privPassword");
        UsmUser usmUser1 = new UsmUser(userName1, AuthMD5.ID, authPass, PrivDES.ID, privPass);
        UsmUser usmUser2 = new UsmUser(userName2, AuthMD5.ID, authPass, PrivDES.ID, privPass);
        //UsmUser usmUser3 = new UsmUser(userName3, AuthMD5.ID, authPass, PrivDES.ID, privPass);
        //UsmUser usmUser4 = new UsmUser(userName4, AuthMD5.ID, authPass, PrivDES.ID, privPass);
        //因为接受的Trap可能来自不同的主机，主机的Snmp v3加密认证密码都不一样，所以根据加密的名称，来添加认证信息UsmUser。
        //添加了加密认证信息的便可以接收来自发送端的信息。
        UsmUserEntry userEnty1 = new UsmUserEntry(userName1,usmUser1);
        UsmUserEntry userEnty2 = new UsmUserEntry(userName2,usmUser2);
        //UsmUserEntry userEnty3 = new UsmUserEntry(userName3,usmUser3);
        //UsmUserEntry userEnty4 = new UsmUserEntry(userName4,usmUser4);
        UsmUserTable userTable = snmp.getUSM().getUserTable();
        // 添加其他用户
        userTable.addUser(userEnty1);
        userTable.addUser(userEnty2);


        //开启Snmp监听，可以接收来自Trap端的信息。
        snmp.listen();

       /*UsmUserTable userTable = snmp.getUSM().getUserTable();
        userTable.addUser(userEnty1);
        userTable.addUser(userEnty2);*/

        /********************************************************************************************/




    }  


    public void run() {
        try {  
            init();  
            snmp.addCommandResponder(this);  
            System.out.println("开始监听Trap信息!");  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
    }  

    /** 
     * 实现CommandResponder的processPdu方法, 用于处理传入的请求、PDU等信息 
     * 当接收到trap时，会自动进入这个方法 
     *  
     * @param respEvnt 
     */  
    public void processPdu(CommandResponderEvent respEvnt) {
        // 解析Response  
        if (respEvnt != null && respEvnt.getPDU() != null) {
        	
            @SuppressWarnings("unchecked")
            Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getPDU().getVariableBindings(); 
            insertTask insertTask = new insertTask();
        	List<Connection>  connectionlist = insertTask.connectionlist;
        	Connection conn = connectionlist.get(0);
        	Date date = new Date();
    		try {
    			date = newDate.newDate();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
    		String tablename = dateFormat.format(date);
            for (int i = 0; i < recVBs.size(); i++) {
                VariableBinding recVB = recVBs.elementAt(i); 
                System.out.println(recVB.getOid() + " : " + recVB.getVariable());
                String sql = "insert into snmp_trap_" + tablename  
    					+ "(start_time,ip,oid,value) "
    					+ "values(?,?,?,?)";
    			PreparedStatement ps;
				try {
					ps = conn.prepareStatement(sql);
					ps.setTimestamp(1, new Timestamp(date.getTime()));
					ps.setString(2, respEvnt.getPeerAddress().toString());
	    			ps.setString(3, recVB.getOid().toString());
	    			ps.setString(4, recVB.getVariable().toString());
	    			ps.executeUpdate();//
	    			ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//
            }
        }
    }

    //开启监控的main方法。
    public static void main(String[] args) {
        SnmpTrapReceive multithreadedtrapreceiver = new SnmpTrapReceive();  
        multithreadedtrapreceiver.run();  
    }
}