package com.igeek.shop.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.igeek.common.utils.DataSourceUtils;
import com.igeek.shop.entity.Category;
import com.igeek.shop.entity.Product;

public class ProductDao {
	/**
	 * 
	* @Title: findHotProducts  
	* @Description:查询热门商品  
	* @return
	* @throws SQLException
	 */
	public List<Product> findHotProducts() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = " select * from product where is_hot = ? limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), 1,0,9);
		
	}
	/**
	 * 
	* @Title: findNewProducts  
	* @Description: 查询最新商品信息 
	* @return
	* @throws SQLException
	 */
	public List<Product> findNewProducts() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = " select * from product order by pdate desc limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), 0,9);
		
	}

	/**
	 * 
	* @Title: findAllCategory  
	* @Description:查找所有商品类别
	* @return
	 * @throws SQLException 
	 */
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = " select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
		
	}
	/**
	 * 
	* @Title: getCount  
	* @Description: 获取所选类别下所有商品的个数 
	* @param cid:类别ID
	* @return
	* @throws SQLException
	 */
	public int getCount(String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = " select count(*) from product where cid=?";
		Long row = (Long)runner.query(sql, new ScalarHandler(), cid);
		return row.intValue();
	}
	/**
	 * 
	* @Title: findProductListByPage  
	* @Description: 根据类别ID,查找的页码检索相应的商品列表  
	* @param cid
	* @param start
	* @param count
	* @return
	* @throws SQLException
	 */
	public List<Product> findProductListByPage(String cid,int start,int count) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = " select * from product where cid = ? limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class),cid,start,count);
		
	}
	/**
	 * 
	* @Title: findProductById  
	* @Description: 根据商品ID查找具体某个商品
	* @param pid : 商品ID
	* @return
	* @throws SQLException
	 */
	public Product findProductById(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = " select * from product where pid = ? ";
		return runner.query(sql, new BeanHandler<Product>(Product.class), pid);
		
	}

}
