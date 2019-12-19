package com.igeek.shop.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.igeek.common.utils.DataSourceUtils;
import com.igeek.shop.dao.AdminDao;
import com.igeek.shop.entity.Category;
import com.igeek.shop.entity.Order;
import com.igeek.shop.entity.Product;

public class AdminDaoOracle implements AdminDao{
	/**
	 * 
	* @Title: findAllCategory  
	* @Description: 查找所有类别
	* @return
	 * @throws SQLException 
	 */
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	
	}
	/**
	 * 
	* @Title: addProduct  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param product
	* @return
	 * @throws SQLException 
	 */
	public int addProduct(Product product) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?)";
		return runner.update(sql, product.getPid(),product.getPname(),product.getMarket_price(),
				product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),
				product.getPdesc(),product.getPflag(),product.getCategory().getCid());

	}
	public List<Order> findAllOrders() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from orders";
		return runner.query(sql, new BeanListHandler<Order>(Order.class));
	}
	public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select p.pimage,p.pname,p.shop_price,i.count,i.subtotal from orderitem i,product p where i.pid=p.pid and i.oid=?";
		return runner.query(sql, new MapListHandler(), oid);
	}

}
