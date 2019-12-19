package com.igeek.shop.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.igeek.common.utils.JedisPoolUtils;
import com.igeek.shop.entity.Category;
import com.igeek.shop.service.ProductService;

import redis.clients.jedis.Jedis;

/**
 * 
* @ClassName: CategoryListServlet  
* @Description: 查找所有类别
* @date 2017年12月13日 上午11:10:07    
* Company www.igeekhome.com
*
 */
public class CategoryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = new ProductService();
		
		/*Jedis jedis = JedisPoolUtils.getJedis();
		String categoryListJson = jedis.get("categoryListJson");*/
		/*if(categoryListJson==null)
		{
			System.out.println("缓存没有,从数据库中获取");
			//查找所有类别
			 List<Category> categoryList = service.findCategoryList();
			 
			 //转成JSON
			 Gson gson = new Gson();
			 categoryListJson = gson.toJson(categoryList);
			 
			 jedis.set("categoryListJson", categoryListJson);
		}*/
		 List<Category> categoryList = service.findCategoryList();
		 
		 //转成JSON
		 Gson gson = new Gson();
		 String categoryListJson = gson.toJson(categoryList);
		 response.setContentType("text/html;charset=utf-8");
		 response.getWriter().write(categoryListJson);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
