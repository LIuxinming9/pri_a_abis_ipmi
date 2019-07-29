package com.gydz.util;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class JnaUtil {

	public interface CLibrary extends Library {
		
		CLibrary INSTANCE = (CLibrary) Native.loadLibrary("dissect", CLibrary.class);
		
		void wireshark_dissect_open();
		
		String dissect_str_packet(String data, int type);// Gb 26 ; Gn,Gi 1

	    void wireshark_dissect_close();
		
	}
	
	public static void open(){
		System.out.println("before open");
		CLibrary.INSTANCE.wireshark_dissect_open();
		System.out.println("open ok");
	}
	
	public static String decode(String data, int type){
		System.out.println("data:"+data);
		String result = null;
		try{
			System.out.println("before dissect_str_packet");
			//System.out.println("##"+CLibrary.INSTANCE.dissect_str_packet(data,type).substring(0, 100));
			result = CLibrary.INSTANCE.dissect_str_packet(data,type);
			System.out.println("dissect_str_packet ok");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("result:"+result);
		
		return result;
	}
	
	public static void close(){
		System.out.println("before close");
		CLibrary.INSTANCE.wireshark_dissect_close();
		System.out.println("close ok");
	}
}
