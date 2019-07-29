package com.gydz.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
 
public class SnmpData {
	
	  public static final int DEFAULT_VERSION = SnmpConstants.version2c;
	  public static final String DEFAULT_PROTOCOL = "udp";
	  public static final int DEFAULT_PORT = 161;
	  public static final long DEFAULT_TIMEOUT = 3 * 1000L;
	  public static final int DEFAULT_RETRY = 3;
	 
	  /**
	   * ��������communityTarget�����ڷ���target
	   *
	   * @param targetAddress
	   * @param community
	   * @param version
	   * @param timeOut
	   * @param retry
	   * @return CommunityTarget
	   */
	  public static CommunityTarget createDefault(String ip, String community) {
	    Address address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip
	        + "/" + DEFAULT_PORT);
	    CommunityTarget target = new CommunityTarget();
	    target.setCommunity(new OctetString(community));
	    target.setAddress(address);
	    target.setVersion(DEFAULT_VERSION);
	    target.setTimeout(DEFAULT_TIMEOUT); // milliseconds
	    target.setRetries(DEFAULT_RETRY);
	    return target;
	  }
	  
	  public static String snmpGet(String ip, String community, String oid) {
		  String value = "";
	    CommunityTarget target = createDefault(ip, community);
	    Snmp snmp = null;
	    try {
	      PDU pdu = new PDU();
	      // pdu.add(new VariableBinding(new OID(new int[]
	      // {1,3,6,1,2,1,1,2})));
	      pdu.add(new VariableBinding(new OID(oid)));
	 
	      DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
	      snmp = new Snmp(transport);
	      snmp.listen();
	      pdu.setType(PDU.GET);
	      ResponseEvent respEvent = snmp.send(pdu, target);
	      //System.out.println("PeerAddress:" + respEvent.getPeerAddress());
	      PDU response = respEvent.getResponse();
	 
	      if (response == null) {
	        //System.out.println("response is null, request time out");
	      } else {
	        //System.out.println("response pdu size is " + response.size());
	        for (int i = 0; i < response.size(); i++) {
	          VariableBinding vb = response.get(i);
	          //System.out.println(vb.getOid() + " = " + vb.getVariable());
	          value = vb.getVariable().toString();
	        }
	 
	      }
	      //System.out.println("SNMP GET one OID value finished !");
	    } catch (Exception e) {
	      e.printStackTrace();
	      //System.out.println("SNMP Get Exception:" + e);
	    } finally {
	      if (snmp != null) {
	        try {
	          snmp.close();
	        } catch (IOException ex1) {
	          snmp = null;
	        }
	      }
	 
	    }
		return value;
	  }
	  
	  /*����OID�б�һ�λ�ȡ����OID���ݣ�������List��ʽ����*/
	  public static void snmpGetList(String ip, String community, List<String> oidList)
	  {
	    CommunityTarget target = createDefault(ip, community);
	    Snmp snmp = null;
	    try {
	      PDU pdu = new PDU();
	 
	      for(String oid:oidList)
	      {
	        pdu.add(new VariableBinding(new OID(oid)));
	      }
	 
	      DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
	      snmp = new Snmp(transport);
	      snmp.listen();
	      System.out.println("-------> ����PDU <-------");
	      pdu.setType(PDU.GET);
	      ResponseEvent respEvent = snmp.send(pdu, target);
	      System.out.println("PeerAddress:" + respEvent.getPeerAddress());
	      PDU response = respEvent.getResponse();
	 
	      if (response == null) {
	        System.out.println("response is null, request time out");
	      } else {
	        System.out.println("response pdu size is " + response.size());
	        for (int i = 0; i < response.size(); i++) {
	          VariableBinding vb = response.get(i);
	          System.out.println(vb.getOid() + " = " + vb.getVariable());
	        }
	 
	      }
	      System.out.println("SNMP GET one OID value finished !");
	    } catch (Exception e) {
	      e.printStackTrace();
	      System.out.println("SNMP Get Exception:" + e);
	    } finally {
	      if (snmp != null) {
	        try {
	          snmp.close();
	        } catch (IOException ex1) {
	          snmp = null;
	        }
	      }
	 
	    }
	  }
	  
	  /*����OID�б������첽��ʽһ�λ�ȡ����OID���ݣ�������List��ʽ����*/
	  public static void snmpAsynGetList(String ip, String community,List<String> oidList)
	  {
	    CommunityTarget target = createDefault(ip, community);
	    Snmp snmp = null;
	    try {
	      PDU pdu = new PDU();
	 
	      for(String oid:oidList)
	      {
	        pdu.add(new VariableBinding(new OID(oid)));
	      }
	 
	      DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
	      snmp = new Snmp(transport);
	      snmp.listen();
	      System.out.println("-------> ����PDU <-------");
	      pdu.setType(PDU.GET);
	      ResponseEvent respEvent = snmp.send(pdu, target);
	      System.out.println("PeerAddress:" + respEvent.getPeerAddress());
	      PDU response = respEvent.getResponse();
	 
	      /*�첽��ȡ*/
	      final CountDownLatch latch = new CountDownLatch(1);
	      ResponseListener listener = new ResponseListener() {
	        public void onResponse(ResponseEvent event) {
	          ((Snmp) event.getSource()).cancel(event.getRequest(), this);
	          PDU response = event.getResponse();
	          PDU request = event.getRequest();
	          System.out.println("[request]:" + request);
	          if (response == null) {
	            System.out.println("[ERROR]: response is null");
	          } else if (response.getErrorStatus() != 0) {
	            System.out.println("[ERROR]: response status"
	                + response.getErrorStatus() + " Text:"
	                + response.getErrorStatusText());
	          } else {
	            System.out.println("Received response Success!");
	            for (int i = 0; i < response.size(); i++) {
	              VariableBinding vb = response.get(i);
	              System.out.println(vb.getOid() + " = "
	                  + vb.getVariable());
	            }
	            System.out.println("SNMP Asyn GetList OID finished. ");
	            latch.countDown();
	          }
	        }
	      };
	 
	      pdu.setType(PDU.GET);
	      snmp.send(pdu, target, null, listener);
	      System.out.println("asyn send pdu wait for response...");
	 
	      boolean wait = latch.await(30, TimeUnit.SECONDS);
	      System.out.println("latch.await =:" + wait);
	 
	      snmp.close();
	 
	      System.out.println("SNMP GET one OID value finished !");
	    } catch (Exception e) {
	      e.printStackTrace();
	      System.out.println("SNMP Get Exception:" + e);
	    } finally {
	      if (snmp != null) {
	        try {
	          snmp.close();
	        } catch (IOException ex1) {
	          snmp = null;
	        }
	      }
	 
	    }
	  }
	  
	  /*����targetOID����ȡ��������*/
	  public static List<String> snmpWalk(String ip, String community, String targetOid)
	  {
		  List<String> list = new ArrayList<String>();
	    CommunityTarget target = createDefault(ip, community);
	    TransportMapping transport = null;
	    Snmp snmp = null;
	    try {
	      transport = new DefaultUdpTransportMapping();
	      snmp = new Snmp(transport);
	      transport.listen();
	 
	      PDU pdu = new PDU();
	      OID targetOID = new OID(targetOid);
	      pdu.add(new VariableBinding(targetOID));
	      pdu.setType(PDU.GETNEXT);
	      
	      boolean finished = false;
	     // System.out.println("----> demo start <----");
	      while (!finished) {
	        VariableBinding vb = null;
	        ResponseEvent respEvent = snmp.getNext(pdu, target);
	 
	        PDU response = respEvent.getResponse();
	 
	        if (null == response) {
	          //System.out.println("responsePDU == null");
	          finished = true;
	          break;
	        } else {
	          vb = response.get(0);
	        }
	        // check finish
	        finished = checkWalkFinished(targetOID, pdu, vb);
	        if (!finished) {
	         // System.out.println("==== walk each vlaue :");
	         //System.out.println(" = " + vb.getVariable());
	        	list.add(vb.getVariable().toString()+"="+vb.getOid().toString());
	        	//list.add(vb.getOid().toString()+"="+vb.getVariable().toString());
	          // Set up the variable binding for the next entry.
	          pdu.setRequestID(new Integer32(0));
	          pdu.set(0, vb);
	        } else {
	         // System.out.println("SNMP walk OID has finished.");
	          snmp.close();
	        }
	      }
	      //System.out.println("----> demo end <----");
	    } catch (Exception e) {
	      e.printStackTrace();
	      //System.out.println("SNMP walk Exception: " + e);
	    } finally {
	      if (snmp != null) {
	        try {
	          snmp.close();
	        } catch (IOException ex1) {
	          snmp = null;
	        }
	      }
	    }
		return list;
	  }
	 
	  private static boolean checkWalkFinished(OID targetOID, PDU pdu,
	      VariableBinding vb) {
	    boolean finished = false;
	    if (pdu.getErrorStatus() != 0) {
	      System.out.println("[true] responsePDU.getErrorStatus() != 0 ");
	      System.out.println(pdu.getErrorStatusText());
	      finished = true;
	    } else if (vb.getOid() == null) {
	      System.out.println("[true] vb.getOid() == null");
	      finished = true;
	    } else if (vb.getOid().size() < targetOID.size()) {
	      System.out.println("[true] vb.getOid().size() < targetOID.size()");
	      finished = true;
	    } else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid()) != 0) {
	      System.out.println("[true] targetOID.leftMostCompare() != 0");
	      finished = true;
	    } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
	      System.out
	          .println("[true] Null.isExceptionSyntax(vb.getVariable().getSyntax())");
	      finished = true;
	    } else if (vb.getOid().compareTo(targetOID) <= 0) {
	      System.out.println("[true] Variable received is not "
	          + "lexicographic successor of requested " + "one:");
	      System.out.println(vb.toString() + " <= " + targetOID);
	      finished = true;
	    }
	    return finished;
	 
	  }
	  
	  /*����targetOID���첽��ȡ��������*/
	  public static void snmpAsynWalk(String ip, String community, String oid)
	  {
	    final CommunityTarget target = createDefault(ip, community);
	    Snmp snmp = null;
	    try {
	      System.out.println("----> demo start <----");
	 
	      DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
	      snmp = new Snmp(transport);
	      snmp.listen();
	 
	      final PDU pdu = new PDU();
	      final OID targetOID = new OID(oid);
	      final CountDownLatch latch = new CountDownLatch(1);
	      pdu.add(new VariableBinding(targetOID));
	      pdu.setType(PDU.TRAP);
	 
	      ResponseListener listener = new ResponseListener() {
	        public void onResponse(ResponseEvent event) {
	          ((Snmp) event.getSource()).cancel(event.getRequest(), this);
	 
	          try {
	            PDU response = event.getResponse();
	            // PDU request = event.getRequest();
	            // System.out.println("[request]:" + request);
	            if (response == null) {
	              System.out.println("[ERROR]: response is null");
	            } else if (response.getErrorStatus() != 0) {
	              System.out.println("[ERROR]: response status"
	                  + response.getErrorStatus() + " Text:"
	                  + response.getErrorStatusText());
	            } else {
	              System.out
	                  .println("Received Walk response value :");
	              VariableBinding vb = response.get(0);
	 
	              boolean finished = checkWalkFinished(targetOID,
	                  pdu, vb);
	              if (!finished) {
	                System.out.println(vb.getOid() + " = "
	                    + vb.getVariable());
	                pdu.setRequestID(new Integer32(0));
	                pdu.set(0, vb);
	                ((Snmp) event.getSource()).getNext(pdu, target,
	                    null, this);
	              } else {
	                System.out
	                    .println("SNMP Asyn walk OID value success !");
	                latch.countDown();
	              }
	            }
	          } catch (Exception e) {
	            e.printStackTrace();
	            latch.countDown();
	          }
	 
	        }
	      };
	 
	      snmp.getNext(pdu, target, null, listener);
	      System.out.println("pdu �ѷ���,�ȵ��첽������...");
	 
	      boolean wait = latch.await(30, TimeUnit.SECONDS);
	      System.out.println("latch.await =:" + wait);
	      snmp.close();
	 
	      System.out.println("----> demo end <----");
	    } catch (Exception e) {
	      e.printStackTrace();
	      System.out.println("SNMP Asyn Walk Exception:" + e);
	    }
	  }
	  
	  /*����OID��ָ��string�������豸������*/
	  public static void setPDU(String ip,String community,String oid,String val) throws IOException
	  {
	    CommunityTarget target = createDefault(ip, community);
	    Snmp snmp = null;
	    PDU pdu = new PDU();
	    pdu.add(new VariableBinding(new OID(oid),new OctetString(val)));
	    pdu.setType(PDU.SET);
	 
	    DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
	    snmp = new Snmp(transport);
	    snmp.listen();
	    System.out.println("-------> ����PDU <-------");
	    snmp.send(pdu, target);
	    snmp.close();
	  }
}

