package com.igeek.shop.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.igeek.common.utils.DataSourceUtils;
import com.igeek.shop.dao.ProductDao;
import com.igeek.shop.entity.Category;
import com.igeek.shop.entity.Order;
import com.igeek.shop.entity.OrderItem;
import com.igeek.shop.entity.PageBean;
import com.igeek.shop.entity.Product;

public class ProductService {
	private ProductDao dao = new ProductDao();
	/**
	 * 
	* @Title: findHotProductList  
	* @Description: 查找热门商品  
	* @return
	 */
	public List<Product> findHotProductList() {
		List<Product> hotProducts = null;
		try {
			hotProducts = dao.findHotProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hotProducts;
	}
	/**
	 * 
	* @Title: findNewProductList  
	* @Description:查询最新商品
	* @return
	 */
	public List<Product> findNewProductList() {
		List<Product> newProducts = null;
		try {
			newProducts = dao.findNewProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newProducts;
	}
	/**
	 * 
	* @Title: findCategoryList  
	* @Description: 查找所有商品类别  
	* @return
	 */
	public List<Category> findCategoryList() {
		 List<Category> categoryList = null;
		try {
			categoryList = dao.findAllCategorys();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categoryList;
	}
	/**
	 * 
	* @Title: findProductListByCid  
	* @Description:根据类别查找分页PageBean对象  
	* @param cid
	* @return
	 */
	public PageBean<Product> findProductListByCid(String cid,int currentPage,int currentCount) {
		PageBean<Product> pageBean = new PageBean<>();
		
		//当前页面
		
		pageBean.setCurrentPage(currentPage);
		//当前显示的数据条数
		
		pageBean.setCurrentCount(currentCount);
		
		//总共的条数,查询数据
		int totalCount = 0;
		try {
			totalCount = dao.getCount(cid);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		
		//总页数  
		int totalPage = (int)Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		
		//当页显示的数据,默认查找第一页，start   currentCount
		int start = (currentPage-1)*currentCount;
		List<Product> list = null;
		try {
			list = dao.findProductByCid(cid,start,currentCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//设置集合
		pageBean.setList(list);
		
		return pageBean;
	}
	public Product findProductById(String pid) {
		
		Product product = null;
		try {
			product = dao.findProductById(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return product;
		
	}
	/**
	 * 
	* @Title: submitOrders  
	* @Description: 提交订单的业务
	* @param order
	 */
	public void submitOrders(Order order) {
		// TODO Auto-generated method stub
		
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();

			//2.调用dao层的操作order的方法
			dao.addOrders(order);
			
			//3.调用dao层的操作orderitem的方法
			dao.addOrderItems(order);
			
		} catch (SQLException e) {
			//事务回滚
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				//提交释放资源
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 
	* @Title: updateOrderInfo  
	* @Description:更新订单的收货人信息  
	* @param order
	 */
	public void updateOrderInfo(Order order) {
		
		try {
			//更新
			dao.updateOrderInfo(order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void updateOrderState(String r6_Order) {
		// TODO Auto-generated method stub
		try {
			dao.updateOrderState(r6_Order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	* @Title: findAllOrders  
	* @Description: 查询指定用户的所有订单（单表查询）  
	* @param uid
	* @return
	 */
	public List<Order> findAllOrders(String uid) {
		List<Order> orderList = null;
		try {
			orderList = dao.findAllOrders(uid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderList;
	}
	/**
	 * 
	* @Title: findAllOrderItems  
	* @Description: 根据订单编号查询订单项的集合
	* @param oid
	* @return
	 */
	public List<Map<String, Object>> findAllOrderItems(String oid) {
		List<Map<String, Object>> itemList = null;
		try {
			itemList = dao.findAllOrderItems(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemList;
	}

}
