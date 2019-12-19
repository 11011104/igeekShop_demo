package com.igeek.shop.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.igeek.common.utils.BeanFactory;
import com.igeek.shop.dao.AdminDao;
import com.igeek.shop.entity.Category;
import com.igeek.shop.entity.Order;
import com.igeek.shop.entity.Product;
import com.igeek.shop.service.AdminService;

public class AdminServiceImpl implements AdminService{
	//AdminDao dao = new AdminDao();
	AdminDao dao = (AdminDao)BeanFactory.getBean("adminDao");
	/**
	 * 
	* @Title: findAllCategory  
	* @Description: 查找所有类别
	* @return
	 */
	public List<Category> findAllCategory() {
		
		List<Category> categoryList = null;
		try {
			categoryList = dao.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return categoryList;
	}
	/**
	 * 
	* @Title: addProduct  
	* @Description: 添加商品
	* @param product
	* @return
	 */
	public boolean addProduct(Product product) {
		int row = 0;
		try {
			row = dao.addProduct(product);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row>0?true:false;
	}
	public List<Order> findAllOrders() {
		List<Order> orderList = null;
		try {
			orderList = dao.findAllOrders();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderList;
	}
	public List<Map<String, Object>> findOrderInfoByOid(String oid) {
		List<Map<String, Object>> mapList = null;
		try {
			mapList = dao.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapList;
	}

}
