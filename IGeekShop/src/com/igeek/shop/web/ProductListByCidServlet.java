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
* @Description:׼����Ʒ�б�����,���������ʷ��¼
* @date 2017��12��14�� ����4:46:55    
* Company www.igeekhome.com
*
 */
public class ProductListByCidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ��Ʒ���ID
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
		
		//��ȡcookie������Ϊpids��Cookieֵ,��ѯ��Ʒ����,����request����,����product_list.jspҳ����չ��
		Cookie[] cookies = request.getCookies();
		List<Product> historyList = new ArrayList<Product>();
		if(cookies!=null)
		{
			for(Cookie c : cookies)
			{
				if("pids".equals(c.getName()))
				{
					String pids = c.getValue();//��ȡ�ַ���1,31,33,34
					String[] pids_arr = pids.split(",");
					for(String pid : pids_arr)
					{
						//����id������Ʒ
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
