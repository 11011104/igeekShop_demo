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
* @Description: �û�ע���webʵ�ֲ�
* @date 2017��12��12�� ����4:21:12    
* Company www.igeekhome.com
*
 */
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//0.���������������
		request.setCharacterEncoding("utf-8");
		
		User user = new User();
		//1.��ȡ���е�����
		try {
			
			//�ֶ���Stringת��Date����
			ConvertUtils.register(new Converter() {
				/**
				 * 
				* @Title: convert  
				* @Description: ʵ������ת���� 
				* @param clazz��Ŀ�����������
				* @param value:Ҫת��������
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
			
			////2.��װ��Userʵ�����
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//uid,state(Ĭ�Ͼ���0),code�������룩
		//UUID���ɲ��ظ����ַ���
		user.setUid(CommonUtils.getUUID());
		String activeCode =CommonUtils.getUUID(); 
		user.setCode(activeCode);
		//3.����service��ע��ķ�ʽ
		
		UserService service = new UserService();
		boolean isSuccess = service.regist(user);
		if(isSuccess)
		{
			String emailMsg = "��ϲ����ע��ɹ�����������ļ���������˻����<br>"
					+ "<a href='http://localhost:8080/IGeekShop/active?activeCode="+activeCode+"'>http://localhost:8080/IGeekShop/active?activeCode="+activeCode+"</a>";
			//�����ʼ��������û�
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//��ת���ɹ�ҳ��
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}else{
			//ʧ��Ҷ��
			//��ת���ɹ�ҳ��
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
