package com.igeek.shop.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.igeek.shop.entity.Product;
import com.igeek.shop.service.ProductService;

/**
 * 
* @ClassName: ProductInfoServlet  
* @Description:处理具体商品的Web层
* @date 2017年12月14日 下午4:46:25    
* Company www.igeekhome.com
*
 */
public class ProductInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		
		//根据商品id查找该商品对象
		ProductService service = new ProductService();
		Product product = service.findProductById(pid);
		
		//获取cid和currentPage,传递到商品详细页面
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");
		
		//传入request域中
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("product", product);
		
		
		//使用cookie技术保存浏览历史记录
		//1.从cookie中读取是否有名字是pids的值
		String pids = pid;
		//获取所有Cookie
		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
		{
			for(Cookie cookie:cookies)
			{
				//判断是否是Pids这个cookie
				if("pids".equals(cookie.getName()))
				{
					//有名为pids这个cookie,则获取其存储的值,
					/*每次在原值的前面拼接这一次访问的pid
					原则是:1)将最新访问的放在最前面.
						例如:如果获取的cookie值为 1,2,3 则商品访问顺序为先访问的3-->2-->1,
						即最新一次访问商品ID是1
						2)如果有重复商品,则先将字符串中重复的ID删除,再将该重复的放在最前面
						例如:如果获取的cookie值为 1,2,3
						该次访问的pid=2,则拼接完成后应该是2,1,3 
					 */
					pids = cookie.getValue();//1,2,3
					
					//获取到cookie值后,处理,将字符串转为数组
					String[] strs = pids.split(",");//{1,2,3}
					
					//将数组转换为LinkedList操作更简便,先将数组转成List集合,再转成LinkedList集合
					List<String> arrList = Arrays.asList(strs);
					LinkedList<String> list = new LinkedList<>(arrList);
					if(list.contains(pid))
					{
						list.remove(pid);
					}
					
					//无论重不重复都要添加在起始位置
					list.addFirst(pid);
					
					StringBuffer sb = new StringBuffer();
					//再将集合转为String
					for(int i=0;i<list.size()&&i<7;i++)
					{
						sb.append(list.get(i));
						sb.append(",");
					}
					
					sb.substring(0, sb.length()-1);
					
					pids = sb.toString();
				}
			}
		}
	
		//创建Cookie,将新拼接好的pids携带回客户端
		Cookie c = new Cookie("pids",pids);
		response.addCookie(c);
		
		
		
		
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
