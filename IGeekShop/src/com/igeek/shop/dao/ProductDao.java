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
	* @Description:��ѯ������Ʒ  
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
	* @Description: ��ѯ������Ʒ��Ϣ 
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
	* @Description:����������Ʒ���
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
	* @Description: ��ȡ��ѡ�����������Ʒ�ĸ��� 
	* @param cid:���ID
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
	* @Description: �������ID,���ҵ�ҳ�������Ӧ����Ʒ�б�  
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
	* @Description: ������ƷID���Ҿ���ĳ����Ʒ
	* @param pid : ��ƷID
	* @return
	* @throws SQLException
	 */
	public Product findProductById(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = " select * from product where pid = ? ";
		return runner.query(sql, new BeanHandler<Product>(Product.class), pid);
		
	}

}
