package com.igeek.shop.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.igeek.common.utils.DataSourceUtils;
import com.igeek.shop.entity.Category;
import com.igeek.shop.entity.Order;
import com.igeek.shop.entity.OrderItem;
import com.igeek.shop.entity.Product;
/**
 * 
* @ClassName: ProductDao  
* @Description:对Product持久层的操作
* @date 2017年12月13日 上午10:11:20    
* Company www.igeekhome.com
*
 */
public class ProductDao {
	/**
	 * 
	* @Title: findHotProducts  
	* @Description: 查找所有热门商品 
	* @return
	 * @throws SQLException 
	 */
	public List<Product> findHotProducts() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = ? limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), 1,0,9);
		
	}
	/**
	 * 
	* @Title: findNewProducts  
	* @Description: 查找所有最新商品
	* @return
	 * @throws SQLException 
	 */
	public List<Product> findNewProducts() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), 0,9);
		
	}
	public List<Category> findAllCategorys() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}
	/**
	 * 
	* @Title: getCount  
	* @Description: 查找某类别下商品的数量  
	* @param cid
	* @return
	* @throws SQLException
	 */
	public int getCount(String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid = ?";
		Long row = (Long)runner.query(sql, new ScalarHandler(), cid);
		return row.intValue();
	}
	/**
	 * 
	* @Title: findProductByCid  
	* @Description: 根据类别查找某页下的商品数据  
	* @param cid
	* @param start
	* @param currentCount
	* @return
	* @throws SQLException
	 */
	public List<Product> findProductByCid(String cid, int start, int currentCount) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid=? limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), cid,start,currentCount);
	}
	public Product findProductById(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid=?";
		return runner.query(sql, new BeanHandler<Product>(Product.class), pid);

	}
	/**
	 * 
	* @Title: addOrders  
	* @Description: 插入一个订单  
	* @param order
	 * @throws SQLException 
	 */
	public void addOrders(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		runner.update(conn, sql, order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
	}
	/**
	 * 
	* @Title: addOrderItems  
	* @Description:插入多条订单项 
	* @param order
	 * @throws SQLException 
	 */
	public void addOrderItems(Order order) throws SQLException {
		
		QueryRunner runner = new QueryRunner();
		Connection conn = DataSourceUtils.getConnection();
		for(OrderItem item : order.getOrderItems())
		{
			String sql = "insert into orderitem values(?,?,?,?,?)";
			runner.update(conn,sql,item.getItemid(),item.getCount(),item.getSubtotal(),
					item.getProduct().getPid(),order.getOid());
			
			
		}
		
		
	}
	/**
	 * 
	* @Title: updateOrderInfo  
	* @Description:更新订单收货信息
	* @param order
	 * @throws SQLException 
	 */
	public void updateOrderInfo(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set address=?,name=?,telephone=? where oid=?";
		runner.update(sql, order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
		
	}
	public void updateOrderState(String r6_Order) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set state=? where oid=?";
		runner.update(sql, 1, r6_Order);
		
	}
	public List<Order> findAllOrders(String uid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where uid=?";
		return runner.query(sql, new BeanListHandler<Order>(Order.class), uid);
	}
	/**
	 * 
	* @Title: findAllOrderItems  
	* @Description:根据oid查找orderitem集合
	* @param oid
	* @return
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> findAllOrderItems(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select i.count,i.subtotal,p.pimage,p.pname,p.shop_price from "
				+ "orderitem i,product p where i.pid=p.pid and i.oid=?";
		List<Map<String, Object>> listMap = runner.query(sql, new MapListHandler(), oid);
		return listMap;
	}

}
