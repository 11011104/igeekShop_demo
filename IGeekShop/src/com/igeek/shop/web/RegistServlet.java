package com.igeek.shop.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.igeek.common.utils.CommonUtils;
import com.igeek.common.utils.MailUtils;
import com.igeek.shop.entity.User;
import com.igeek.shop.service.UserService;

/**
 * 
* @ClassName: RegistServlet  
* @Description: 用户注册的web实现层
* @date 2017年12月12日 下午4:21:12    
* Company www.igeekhome.com
*
 */
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//0.解决中文乱码问题
		request.setCharacterEncoding("utf-8");
		
		User user = new User();
		//1.获取表单中的数据
		try {
			
			//手动将String转成Date类型
			ConvertUtils.register(new Converter() {
				/**
				 * 
				* @Title: convert  
				* @Description: 实现类型转换啊 
				* @param clazz：目标的数据类型
				* @param value:要转换的数据
				* @return
				* @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
				 */
				@Override
				public Object convert(Class clazz, Object value) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					Date desc = null;
					try {
						desc = sf.parse((String)value);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return desc;
				}
			}, Date.class);
			
			////2.组装成User实体对象
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//uid,state(默认就是0),code（激活码）
		//UUID生成不重复的字符串
		user.setUid(CommonUtils.getUUID());
		String activeCode =CommonUtils.getUUID(); 
		user.setCode(activeCode);
		//3.调用service的注册的方式
		
		UserService service = new UserService();
		boolean isSuccess = service.regist(user);
		if(isSuccess)
		{
			String emailMsg = "恭喜您，注册成功！请点击下面的激活码进行账户激活。<br>"
					+ "<a href='http://localhost:8080/IGeekShop/active?activeCode="+activeCode+"'>http://localhost:8080/IGeekShop/active?activeCode="+activeCode+"</a>";
			//发送邮件，激活用户
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//跳转到成功页面
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}else{
			//失败叶面
			//跳转到成功页面
			response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
