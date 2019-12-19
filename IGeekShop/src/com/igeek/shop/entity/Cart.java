package com.igeek.shop.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 
* @ClassName: Cart  
* @Description: 购物车
* @date 2017年12月18日 下午2:51:31    
* Company www.igeekhome.com
*
 */
public class Cart {
	//购物车中的购物项，为了方便封装map集合
	private Map<String,CartItem> list = new HashMap<String,CartItem>();
	//总计
	private double total;
	/**
	 * @return the list
	 */
	public Map<String, CartItem> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(Map<String, CartItem> list) {
		this.list = list;
	}
	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
