package com.igeek.shop.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.igeek.common.utils.BeanFactory;
import com.igeek.common.utils.CommonUtils;
import com.igeek.shop.entity.Category;
import com.igeek.shop.entity.Product;
import com.igeek.shop.service.AdminService;

/**
 * 
 * @ClassName: AdminAddProduct
 * @Description: 为了能够获取普通内容和文件上传，自己封装的Servlet
 * @date 2017年12月25日 下午1:38:00 Company www.igeekhome.com
 *
 */
public class AdminAddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @Title: doGet
	 * @Description: 添加商品
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			// 获取表单各元素值，封装成Product实体，插入到底层数据
			// 1.创建工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2.创建核心类
			ServletFileUpload upload = new ServletFileUpload(factory);
			Product product = new Product();
			
			Map<String, Object> map = new HashMap<String,Object>();
			
			// 3.解析request的内容
			List<FileItem> parseRequest = upload.parseRequest(request);		
			//遍历集合
			for(FileItem item : parseRequest)
			{
				//判断是不是普通表单
				if(item.isFormField())
				{
					//是普通表单,获取表单中name属性值，获取表单对应的输入值
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("utf-8");
					
					//放入map集合中
					map.put(fieldName, fieldValue);
					
				}else{
					//文件上传
					//获取文件名，文件内容，写入服务器
					String fileName = item.getName();
					
					//文件上传的路径
					String path =this.getServletContext().getRealPath("upload");
					
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(path+"/" + fileName);
					
					IOUtils.copy(in, out);
					in.close();
					out.close();
					
					//文件的路径放入map集合中
					map.put("pimage","upload"+File.separator + fileName);
					
				}
			}
			
			//将map中的属性映射到product实体中
			BeanUtils.populate(product, map);
			
			//	private String pid;
			product.setPid(CommonUtils.getUUID());
			//private String pimage;
			
			//private Date pdate;
			product.setPdate(new Date());
			//	private int pflag;//上架/下架
			product.setPflag(0);
			//private Category category;
			Category c = new Category();
			c.setCid(map.get("cid").toString());
			
			product.setCategory(c);
		
			//添加到底层数据库
			AdminService service = (AdminService)BeanFactory.getBean("adminService");
			
			boolean isSuccess = service.addProduct(product);
			//
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		// 完成商品图片的存入服务器
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
