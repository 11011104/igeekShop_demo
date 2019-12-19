package com.igeek.common.utils;

import java.util.UUID;
/**
 * 
* @ClassName: CommonUtils  
* @Description:通用的工具类
* @date 2017年12月12日 上午10:42:05    
* Company www.igeekhome.com
*
 */
public class CommonUtils {
	public static String getUUID()
	{
		return UUID.randomUUID().toString();
	}
}
