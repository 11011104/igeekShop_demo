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
* @Description:���������Ʒ��Web��
* @date 2017��12��14�� ����4:46:25    
* Company www.igeekhome.com
*
 */
public class ProductInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		
		//������Ʒid���Ҹ���Ʒ����
		ProductService service = new ProductService();
		Product product = service.findProductById(pid);
		
		//��ȡcid��currentPage,���ݵ���Ʒ��ϸҳ��
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");
		
		//����request����
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("product", product);
		
		
		//ʹ��cookie�������������ʷ��¼
		//1.��cookie�ж�ȡ�Ƿ���������pids��ֵ
		String pids = pid;
		//��ȡ����Cookie
		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
		{
			for(Cookie cookie:cookies)
			{
				//�ж��Ƿ���Pids���cookie
				if("pids".equals(cookie.getName()))
				{
					//����Ϊpids���cookie,���ȡ��洢��ֵ,
					/*ÿ����ԭֵ��ǰ��ƴ����һ�η��ʵ�pid
					ԭ����:1)�����·��ʵķ�����ǰ��.
						����:�����ȡ��cookieֵΪ 1,2,3 ����Ʒ����˳��Ϊ�ȷ��ʵ�3-->2-->1,
						������һ�η�����ƷID��1
						2)������ظ���Ʒ,���Ƚ��ַ������ظ���IDɾ��,�ٽ����ظ��ķ�����ǰ��
						����:�����ȡ��cookieֵΪ 1,2,3
						�ôη��ʵ�pid=2,��ƴ����ɺ�Ӧ����2,1,3 
					 */
					pids = cookie.getValue();//1,2,3
					
					//��ȡ��cookieֵ��,����,���ַ���תΪ����
					String[] strs = pids.split(",");//{1,2,3}
					
					//������ת��ΪLinkedList���������,�Ƚ�����ת��List����,��ת��LinkedList����
					List<String> arrList = Arrays.asList(strs);
					LinkedList<String> list = new LinkedList<>(arrList);
					if(list.contains(pid))
					{
						list.remove(pid);
					}
					
					//�����ز��ظ���Ҫ�������ʼλ��
					list.addFirst(pid);
					
					StringBuffer sb = new StringBuffer();
					//�ٽ�����תΪString
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
	
		//����Cookie,����ƴ�Ӻõ�pidsЯ���ؿͻ���
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
