package com.igeek.shop.service;

import java.sql.SQLException;
import java.util.List;

import com.igeek.shop.dao.ProductDao;
import com.igeek.shop.entity.Category;
import com.igeek.shop.entity.PageBean;
import com.igeek.shop.entity.Product;

public class ProductService {
	private ProductDao dao = new ProductDao();
	/** 
	* @Title: findHotProductList  
	* @Description: 查找热门商品  
	* @return
	 */
	public List<Product> findHotProductList() {
		List<Product> hotProductList = null;
		try {
			hotProductList = dao.findHotProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hotProductList;
	}

	public List<Product> findNewProductList() {
		List<Product> newProductList = null;
		try {
			newProductList = dao.findNewProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newProductList;
	}

	/**
	 * 
	* @Title: findCategoryList  
	* @Description: 查找所有商品
	* @return
	 */
	public List<Category> findCategoryList() {
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
	* @Title: findProductListByCid  
	* @Description: 根据类别ID、页数查找相应商品数据，组装成PageBean对象
	* @param cid : 类别ID
	* @param currentPage : 当前页码
	* @param currentCount :每页中显示的条数,一般固定或从页面传
	* @return 返回PageBean类型对象
	 */
	public PageBean<Product> findProductListByCid(String cid,int currentPage,int currentCount) {
		PageBean<Product> pageBean = new PageBean<Product>();
		
	/*	//当前页数
		int currentPage = 1;*/
		pageBean.setCurrentPage(currentPage);
		
		//当前页显示条数
//		int currentCount = 12;
		pageBean.setCurrentCount(currentCount);
		
		//获取总条数
		int totalCount = 0;
		try {
			totalCount = dao.getCount(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		
		//总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		
		int start = (currentPage-1)*currentCount;
		//当页商品
		List<Product> list = null;
		try {
			list = dao.findProductListByPage(cid,start,currentCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pageBean.setList(list);
		
		return pageBean;
	}

	/**
	 * 
	* @Title: findProductById  
	* @Description: 根据商品ID查找具体商品的业务逻辑  
	* @param pid : 商品ID
	* @return 返回具体商品对象
	 */
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
	
}
