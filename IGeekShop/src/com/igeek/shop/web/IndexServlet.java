package com.igeek.shop.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.igeek.shop.entity.Category;
import com.igeek.shop.entity.Product;
import com.igeek.shop.service.ProductService;

/**
 * 
* @ClassName: IndexServlet  
* @Description: 首页显示商品
* @date 2017年12月13日 上午9:20:54    
* Company www.igeekhome.com
*
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService service = new ProductService();
		
		//获取热门商品
		List<Product> hotProductList = service.findHotProductList();
		
		//获取最新商品
		List<Product> newProductList = service.findNewProductList();
		
		/*//查找所有类别
		List<Category> categoryList = service.findCategoryList();
		
		request.setAttribute("categoryList", categoryList);*/
		//放入request域中
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
