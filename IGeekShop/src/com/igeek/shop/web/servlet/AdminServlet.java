package com.igeek.shop.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.igeek.common.utils.BeanFactory;
import com.igeek.shop.entity.Category;
import com.igeek.shop.entity.Order;
import com.igeek.shop.service.AdminService;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	* @Title: findOrderInfoByOid  
	* @Description: 根据订单编号查找订单详情 
	* @param request
	* @param response
	* @throws ServletException
	* @throws IOException
	 */
	public void findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//休眠
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//获取订单编号
		String oid=request.getParameter("oid");
		
		//面向接口编程
		//AdminService service = new AdminServiceImpl();
		//bean---名称---对应一个类名  --写到配置文件中
		AdminService service = (AdminService)BeanFactory.getBean("adminService");
		
		List<Map<String,Object>> mapList = service.findOrderInfoByOid(oid);
		
		//ajax请求通过response写回去
		Gson gson = new Gson();
		String json = gson.toJson(mapList);
		//[
		//{"shop_price":2299.0,"count":2,"pname":"宏碁（acer）ATC705-N50 台式电脑","pimage":"products/1/c_0031.jpg","subtotal":4598.0},
		//{"shop_price":1299.0,"count":4,"pname":"小米 4c 标准版","pimage":"products/1/c_0001.jpg","subtotal":5196.0},
		//{"shop_price":2298.0,"count":1,"pname":"vivo X5Pro","pimage":"products/1/c_0014.jpg","subtotal":2298.0}
		//]

		//System.out.println(json);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		
	}
   /**
    *     
   * @Title: findAllOrders  
   * @Description: 查找所有订单（单表）
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
    */
	public void findAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service = (AdminService)BeanFactory.getBean("adminService");
		List<Order> orderList = service.findAllOrders();
		
		//放入request
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
		
		
	}
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取所有类别
		AdminService service = (AdminService)BeanFactory.getBean("adminService");
		List<Category> categoryList = service.findAllCategory();
		
		//异步请求
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		
		//设置文件格式
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
	}

}
