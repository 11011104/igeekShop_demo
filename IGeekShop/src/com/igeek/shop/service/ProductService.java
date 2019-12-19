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
	* @Description: ����������Ʒ  
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
	* @Description: ����������Ʒ
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
	* @Description: �������ID��ҳ��������Ӧ��Ʒ���ݣ���װ��PageBean����
	* @param cid : ���ID
	* @param currentPage : ��ǰҳ��
	* @param currentCount :ÿҳ����ʾ������,һ��̶����ҳ�洫
	* @return ����PageBean���Ͷ���
	 */
	public PageBean<Product> findProductListByCid(String cid,int currentPage,int currentCount) {
		PageBean<Product> pageBean = new PageBean<Product>();
		
	/*	//��ǰҳ��
		int currentPage = 1;*/
		pageBean.setCurrentPage(currentPage);
		
		//��ǰҳ��ʾ����
//		int currentCount = 12;
		pageBean.setCurrentCount(currentCount);
		
		//��ȡ������
		int totalCount = 0;
		try {
			totalCount = dao.getCount(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		
		//��ҳ��
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		
		int start = (currentPage-1)*currentCount;
		//��ҳ��Ʒ
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
	* @Description: ������ƷID���Ҿ�����Ʒ��ҵ���߼�  
	* @param pid : ��ƷID
	* @return ���ؾ�����Ʒ����
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
