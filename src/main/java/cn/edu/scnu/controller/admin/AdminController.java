package cn.edu.scnu.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scnu.pojo.User;
import cn.edu.scnu.service.OrderService;
import cn.edu.scnu.service.UserService;
import cn.edu.scnu.vo.SysResult;

@RestController
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/admin/query/{ticket}")
	public SysResult checkAdminUser(@PathVariable String ticket) {
		if (ticket==null) {
			return SysResult.build(201, null, null);
		}
		User user=userService.queryUserJson(ticket);
		System.out.println(user.getUserType());
		if (user.getUserType()!=0) {
			return SysResult.build(200, null, null);
		}else {
			return SysResult.build(201, null, null);
		}
	}
	@RequestMapping("/admin/deliverOrder")
	public SysResult deliverOrder(String orderId) {
		try {
			orderService.deliverOrder(orderId);
			return SysResult.build(200, null, null);
		} catch (Exception e) {
			return SysResult.build(201, null, null);
		}
	}
}
