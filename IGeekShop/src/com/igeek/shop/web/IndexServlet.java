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
* @Description: ��ҳ��ʾ��Ʒ
* @date 2017��12��13�� ����9:20:54    
* Company www.igeekhome.com
*
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService service = new ProductService();
		
		//��ȡ������Ʒ
		List<Product> hotProductList = service.findHotProductList();
		
		//��ȡ������Ʒ
		List<Product> newProductList = service.findNewProductList();
		
		/*//�����������
		List<Category> categoryList = service.findCategoryList();
		
		request.setAttribute("categoryList", categoryList);*/
		//����request����
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
