package com.igeek.shop.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.igeek.shop.entity.PageBean;
import com.igeek.shop.entity.Product;
import com.igeek.shop.service.ProductService;

/**
 * 
* @ClassName: ProductListByCidServlet  
* @Description:准备商品列表数据,解析浏览历史记录
* @date 2017年12月14日 下午4:46:55    
* Company www.igeekhome.com
*
 */
public class ProductListByCidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取商品类别ID
		String cid = request.getParameter("cid");
		String page = request.getParameter("currentPage");
		int currentPage = 1;
		if(page!=null)
		{
			currentPage = Integer.parseInt(page);
		}
		int currentCount=12;
		ProductService service = new ProductService();
		PageBean<Product> pageBean = service.findProductListByCid(cid,currentPage,currentCount);
		//System.out.println(pageBean.getList().size());
		
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		
		//获取cookie中名字为pids的Cookie值,查询商品集合,放入request域中,返回product_list.jsp页面中展现
		Cookie[] cookies = request.getCookies();
		List<Product> historyList = new ArrayList<Product>();
		if(cookies!=null)
		{
			for(Cookie c : cookies)
			{
				if("pids".equals(c.getName()))
				{
					String pids = c.getValue();//获取字符串1,31,33,34
					String[] pids_arr = pids.split(",");
					for(String pid : pids_arr)
					{
						//根据id查找商品
						Product product = service.findProductById(pid);
						historyList.add(product);
					}
				}
			}
		}
		
		request.setAttribute("historyList", historyList);
		
		
		
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
