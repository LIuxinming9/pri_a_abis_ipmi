package com.gydz.util;

import org.apache.shiro.crypto.hash.SimpleHash;

import com.gydz.user.model.User;

public class EncryptUtil {

	private static String algorithmName = "MD5"; // 加密方式
	 
	private static int hashIterations = 1024; // 加密次数
 
	public static User EncryptUser(User user) {
		
		String password = new SimpleHash(algorithmName, user.getPassword(), null,  hashIterations).toHex();
		user.setPassword(password);	//设置加密过后的密码
		return user;
	}
	
	public static String EncryptPwd(String pwd) {
		 
		return new SimpleHash(algorithmName, pwd, null,  hashIterations).toHex();
	}
	
	public static void main(String[] args) {
		String passWord = new SimpleHash(algorithmName, "aaa123456", null,  hashIterations).toHex();
		//1a8a8ba5577d96b81b21ee0c17f773b9
		//894b3913a4a13b25dc6186d11835c209
		System.out.println("pwd:"+passWord);
	}

}
