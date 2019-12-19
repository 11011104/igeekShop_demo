package com.igeek.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.igeek.common.utils.CommonUtils;
import com.igeek.common.utils.JedisPoolUtils;
import com.igeek.common.utils.PaymentUtil;
import com.igeek.shop.entity.Cart;
import com.igeek.shop.entity.CartItem;
import com.igeek.shop.entity.Category;
import com.igeek.shop.entity.Order;
import com.igeek.shop.entity.OrderItem;
import com.igeek.shop.entity.PageBean;
import com.igeek.shop.entity.Product;
import com.igeek.shop.entity.User;
import com.igeek.shop.service.ProductService;

import redis.clients.jedis.Jedis;

/**
 * 
 * @ClassName: ProductServlet
 * @Description: 实现商品模块的所有web部分的功能
 * @date 2017年12月18日 上午11:17:24 Company www.igeekhome.com
 *
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	// product--->service--->父类中是否重写service
	/*
	 * protected void doGet(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { // 處理用戶的請求 String method
	 * = request.getParameter("method"); if("categoryList".equals(method)) {
	 * categoryList(request,response); }else if("index".equals(method)) {
	 * index(request,response); }else if("productInfo".equals(method)) {
	 * productInfo(request,response); }else if("productList".equals(method)) {
	 * productList(request,response); }
	 * 
	 * 
	 * }
	 * 
	 * protected void doPost(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { // TODO Auto-generated
	 * method stub doGet(request, response); }
	 */

	// 每个方法就实现了原来的一Servlet的功能
	// 商品分类
	public void categoryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = new ProductService();

		// 先从缓存获取categoryList，如果获取不到再去数据库中获取
		Jedis jedis = JedisPoolUtils.getJedis();
		// 从缓存中获取数据
		String categoryListJson = jedis.get("categoryListJson");
		if (categoryListJson == null) {
			//
			System.out.println("缓存中没有数据，从数据库中获取");
			List<Category> categoryList = service.findCategoryList();
			// 需要将categoryList转成json
			Gson gson = new Gson();
			categoryListJson = gson.toJson(categoryList);

			// 放入缓存中
			jedis.set("categoryListJson", categoryListJson);
		}

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(categoryListJson);
	}

	// 首页
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = new ProductService();

		// 获取热门商品---List<Product>
		List<Product> hotProductList = service.findHotProductList();
		// 获取最新商品---List<Product>
		List<Product> newProductList = service.findNewProductList();

		/*
		 * List<Category> categoryList = service.findCategoryList();
		 * 
		 * 
		 * request.setAttribute("categoryList", categoryList);
		 */
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);

		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	// 展现商品详情
	public void productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取商品ID
		String pid = request.getParameter("pid");
		ProductService service = new ProductService();
		Product product = service.findProductById(pid);

		// cid
		String cid = request.getParameter("cid");

		// currentPage
		String currentPage = request.getParameter("currentPage");
		request.setAttribute("product", product);
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);

		/**
		 * 保存历史记录
		 */
		// 1.先获取Cookie
		Cookie[] cookies = request.getCookies();

		String pidsValue = pid;

		if (cookies != null) {
			for (Cookie c : cookies) {
				if ("pids".equals(c.getName())) {
					// 获取pids值
					String pids = c.getValue();

					// 2. 48-31-2-1 每次将最新访问的记录放在最前面
					// 新访问 5 ----->5-48-31-2-1
					// 新访问 31 ----->31-5-48-2-1
					// 1 ------>1-5-48-31-2 如果有重复的，在原串中将其删除，再将重复的ID放在最前面

					// 将String-->数组 -->集合（LinkedList）
					String[] arrays = pids.split("-");
					// 将数组转成List
					List<String> list = Arrays.asList(arrays);
					//
					LinkedList<String> pidsList = new LinkedList<String>(list);

					if (pidsList.contains(pid)) {
						// 将其删除
						pidsList.remove(pid);
					}
					// 将其放在最前面
					pidsList.addFirst(pid);
					pidsValue = "";
					// 将集合转成字符串
					for (int i = 0; i < pidsList.size() && i < 7; i++) {
						pidsValue += pidsList.get(i) + "-";
					}
					// 除去最后一个-
					pidsValue = pidsValue.substring(0, pidsValue.length() - 1);
				}
			}
		}

		// 生成Cookie
		Cookie cookie = new Cookie("pids", pidsValue);

		// 携带回客户端
		response.addCookie(cookie);

		// 页面跳转
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	// 商品列表
	public void productList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 根据商品的分类查找该分类下的所有商品(默认找第一页的信息)
		String cid = request.getParameter("cid");

		// 获取页码
		String page = request.getParameter("currentPage");

		int currentPage = 1;
		// 判断page是否有值
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}

		int currentCount = 12;
		//
		ProductService service = new ProductService();
		PageBean<Product> pageBean = service.findProductListByCid(cid, currentPage, currentCount);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);

		// 历史浏览记录，获取pids的Cookie
		Cookie[] cookies = request.getCookies();
		List<Product> list = new ArrayList<Product>();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if ("pids".equals(c.getName())) {
					String pids = c.getValue();

					// 数组
					String[] arr = pids.split("-");

					for (String pid : arr) {
						Product pro = service.findProductById(pid);
						list.add(pro);
					}

				}
			}
		}
		// 解析字符串，根据查找商品

		// 放入request中
		request.setAttribute("historyList", list);
		// 页面的跳转
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	// 清空购物车方法
	public void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		// 清除
		// session.invalidate();
		session.removeAttribute("cart");

		// 跳转
		// 页面的跳转
		response.sendRedirect(request.getContextPath() + "/cart.jsp");

	}

	// 删除单个购物项

	public void delFromCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		// 获取商品id
		String pid = request.getParameter("pid");

		// 获取购物车对象
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			Map<String, CartItem> list = cart.getList();

			// 重新计算总金额 = 原来的总金额-移除的项的小计
			cart.setTotal(cart.getTotal() - list.get(pid).getSubTotal());

			// 将删除的购物项从购物车中移除
			list.remove(pid);
		}

		session.setAttribute("cart", cart);

		// 页面的跳转
		response.sendRedirect(request.getContextPath() + "/cart.jsp");

	}

	public void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		ProductService service = new ProductService();

		// 获取商品id
		String pid = request.getParameter("pid");

		// 根据ID获取商品对象
		Product product = service.findProductById(pid);

		// 获取商品数量
		String num = request.getParameter("buyNum");// 大概处理
		int buyNum = Integer.parseInt(num);

		// 计算小计
		double subTotal = buyNum * product.getShop_price();

		// 生成一个CartItem对象

		double newSubTotal = subTotal;// 本次小计

		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			// 第一次加购
			cart = new Cart();
		}

		// 获取购物车中的购物项集合
		Map<String, CartItem> list = cart.getList();

		// 判断list中是否已经存在当前商品的pid
		if (list.containsKey(pid)) {
			// 重复商品，需要合并
			int oldNum = list.get(pid).getBuyNum();
			// 合并之后的数量buyNum
			buyNum += oldNum;

			// 合并之后的小计
			newSubTotal = buyNum * product.getShop_price();

		}

		CartItem item = new CartItem(product, buyNum, newSubTotal);

		// cart肯定存在
		// 将CartItem存到cart购物车中
		cart.getList().put(product.getPid(), item);

		// 计算总计，设置到cart中
		double total = cart.getTotal() + subTotal;
		cart.setTotal(total);

		// 将购物车存入session
		session.setAttribute("cart", cart);

		// 页面跳转到cart.jsp
		// request.getRequestDispatcher("/cart.jsp").forward(request, response);
		response.sendRedirect(request.getContextPath() + "/cart.jsp");

	}

	/**
	 * 
	 * @Title: submitOrder
	 * @Description: 提交订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void submitOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		// 验证用户是否登录
		User user = (User) session.getAttribute("user");
		if (user == null) {
			// 未登录状态
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		// 创建Order对象
		Order order = new Order();

		// 属性封装
		/**
		 * 
		 * 1.private String oid; 2.private Date ordertime;//下单时间 3.private
		 * double total;//总金额 4.private int state;//订单状态 付款状态1--已付款 0--未付款
		 * 5.private String address;//收货地址 6.private String name;//收货人姓名
		 * 7.private String telephone;//联系方式 8.private User user;//谁下的单
		 * 
		 * 9.private List<OrderItem> orderItems = new ArrayList<OrderItem>();
		 */
		// 订单编号
		String oid = CommonUtils.getUUID();
		order.setOid(oid);
		// 下单时间
		order.setOrdertime(new Date());
		// 总金额

		// 获取购物车
		Cart cart = (Cart) session.getAttribute("cart");
		// 处理详细逻辑（省略）
		order.setTotal(cart.getTotal());

		// 设置购买用户
		order.setUser(user);

		// 设List<OrderItem> orderitems;
		// 从购物车中获取CartItem转成OrderItem
		Map<String, CartItem> list = cart.getList();

		for (Entry<String, CartItem> entry : list.entrySet()) {
			// 得到每一个购物项
			CartItem cartItem = entry.getValue();

			// 准备OrderItem，从CartItem转换数据
			OrderItem orderitem = new OrderItem();
			// private String itemid;
			orderitem.setItemid(CommonUtils.getUUID());
			// private int count;//购买的数量
			orderitem.setCount(cartItem.getBuyNum());
			// private double subtotal;//小计
			orderitem.setSubtotal(cartItem.getSubTotal());
			// private Product product;//该项中购买的商品对象
			orderitem.setProduct(cartItem.getProduct());
			// private Order order;
			orderitem.setOrder(order);

			// 将订单项存入到order中的订单项的集合中
			order.getOrderItems().add(orderitem);

		}

		// 调用service层 方法
		ProductService service = new ProductService();
		service.submitOrders(order);

		// order存到session
		session.setAttribute("order", order);

		// 跳转
		response.sendRedirect(request.getContextPath() + "/order_info.jsp");

	}

	// 确认订单功能
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = new ProductService();
		HttpSession session = request.getSession();
		// 表单信息
		Order order = (Order) session.getAttribute("order");
		try {
			BeanUtils.populate(order, request.getParameterMap());

			// 1.更新用户的收货信息
			service.updateOrderInfo(order);

			// 2.完成支付功能
			// 获取用户选择的银行

			// 获得 支付必须基本数据
			// 订单编号
			String orderid = order.getOid();
			// 付款
			// String money = order.getTotal()+"";
			String money = "0.01";
			// 银行
			String pd_FrpId = request.getParameter("pd_FrpId");

			// 发给支付公司需要哪些数据
			String p0_Cmd = "Buy";
			String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
			String p2_Order = orderid;
			String p3_Amt = money;
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
			// 第三方支付可以访问网址
			String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			// 加密hmac 需要密钥
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
					p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

			// 请易宝支付的地址
			String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId=" + pd_FrpId + "&p0_Cmd=" + p0_Cmd
					+ "&p1_MerId=" + p1_MerId + "&p2_Order=" + p2_Order + "&p3_Amt=" + p3_Amt + "&p4_Cur=" + p4_Cur
					+ "&p5_Pid=" + p5_Pid + "&p6_Pcat=" + p6_Pcat + "&p7_Pdesc=" + p7_Pdesc + "&p8_Url=" + p8_Url
					+ "&p9_SAF=" + p9_SAF + "&pa_MP=" + pa_MP + "&pr_NeedResponse=" + pr_NeedResponse + "&hmac=" + hmac;

			// 重定向到第三方支付平台
			response.sendRedirect(url);

			// 3.更新order的状态

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: myOrders
	 * @Description: 我的订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void myOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 验证用户是否登录
		User user = (User) session.getAttribute("user");
		if (user == null) {
			// 未登录状态
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		//用户肯定登录了
		ProductService service = new ProductService();
		//查找该用户下的所有订单集合
		List<Order> orderList = service.findAllOrders(user.getUid());
		if(orderList!=null)
		{
			//遍历集合
			for(Order order : orderList)
			{
				//获取每一个Order对象,根据oid查找对应的List<OrderItem>订单项的集合
				List<Map<String, Object>> listMap = service.findAllOrderItems(order.getOid());
				
				//组装订单下的所有订单项数据
				for(Map<String, Object> map : listMap)
				{
					
					try {
						OrderItem orderItem = new OrderItem();
						//orderItem.setCount(Integer.parseInt(map.get("count").toString()));//手动单个转换
						//映射orderitem对象
						BeanUtils.populate(orderItem, map);
						
						Product product = new Product();
						///映射product对象
						BeanUtils.populate(product, map);//pimage,pname,shop_price
						
						//关联两者之间的关系
						orderItem.setProduct(product);
						
						//orderItem存入order的List<OrderItem>订单项的集合
						order.getOrderItems().add(orderItem);
						
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//count,subtotal
					
				}

			}
		}

		
		//将存入
		request.setAttribute("orderList", orderList);
		//跳转到前台页面
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
		
		
	}
}
