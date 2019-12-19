package com.igeek.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 
* @ClassName: MD5Utils  
* @Description: MD5加密辅助类
* @date 2017年12月12日 下午4:18:28    
* Company www.igeekhome.com
*
 */
public class MD5Utils {
	/**
	 * 
	* @Title: md5  
	* @Description: 实现md5加密  
	* @param plainText
	* @return
	 */
	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	public static void main(String[] args) {
		System.out.println(md5("123"));
	}

}
