package com.gydz.util;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class snmpUtil {
	private Snmp snmp = null;  
	  
	  
    private Address targetAddress = null;  


    public void initComm(String ip) throws IOException {  

             

           // 设置Agent方的IP和端口  

           targetAddress = GenericAddress.parse("udp:"+ip+"/161");  

           TransportMapping transport = new DefaultUdpTransportMapping();  

           snmp = new Snmp(transport);  
           
           transport.listen();  
    }  


    public ResponseEvent sendPDU(PDU pdu) throws IOException {  

           // 设置 target  

           CommunityTarget target = new CommunityTarget();  

           target.setCommunity(new OctetString("public"));  

           target.setAddress(targetAddress);  

           // 通信不成功时的重试次数  

           target.setRetries(2);  

           // 超时时间  

           target.setTimeout(1500);  

           target.setVersion(SnmpConstants.version2c);  

           // 向Agent发送PDU，并返回Response  
           ResponseEvent event = snmp.send(pdu, target);
           
           //snmp.close();
           
           return event;  

    }  

      

    public void setPDU(String oid,String type,String value) throws IOException {

           // set PDU  

           PDU pdu = new PDU();  

           if(type.equals("非数字")) {
        	   pdu.add(new VariableBinding(new OID(oid), new OctetString(value)));  
           }else if(type.equals("数字")){
        	   pdu.add(new VariableBinding(new OID(oid), new Integer32(Integer.parseInt(value))));
           }
           //pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.7.0"), new OctetString("cuixiaoji")));  
           
           //pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.6.0"), new Integer32(10)));
           
           //pdu.add(new VariableBinding(new OID(".1.3.6.1.4.1.2011.2.15.2.1.2.1.1.1.3"), new OctetString(new Date().toString()))); 
           
           pdu.setType(PDU.SET);

           ResponseEvent  ResponseEvent  = sendPDU(pdu);
    }  

      

    public void getPDU() throws IOException {

           // get PDU  

           PDU pdu = new PDU();  

           pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 5, 0 })));  

           pdu.setType(PDU.GET);  

           readResponse(sendPDU(pdu));  

    }  

      

    public void readResponse(ResponseEvent respEvnt) {

           // 解析Response  
           if (respEvnt != null && respEvnt.getResponse() != null) {

                  Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();  

                  for (int i = 0; i < recVBs.size(); i++) {  

                         VariableBinding recVB = recVBs.elementAt(i);  

                         System.out.println(recVB.getOid() + " : " + recVB.getVariable());  
                         
                  }  

           }  

    }  

      

    public static void main(String[] args) {
    	

           try {  
        	   snmpUtil util = new snmpUtil();  
        	   
        	   util.initComm("127.0.0.1");  
        	   
        	   //util.setPDU("1.3.6.1.2.1.1.5.0","非数字","cuixiaojin");
        	   
        	   util.getPDU();
           } catch (IOException e) {  

                  e.printStackTrace();  
           }
    }  
}
