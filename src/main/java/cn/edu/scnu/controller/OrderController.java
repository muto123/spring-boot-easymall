package cn.edu.scnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scnu.po.Order;
import cn.edu.scnu.pojo.OrderItem;
import cn.edu.scnu.service.OrderService;
import cn.edu.scnu.vo.SysResult;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/save")
	public SysResult orderSave(String orderReceiverinfo,String userId,Double orderMoney) {
		try {
			orderService.addOrderItems(orderReceiverinfo,userId,orderMoney);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, null, null);
		}
	}
	@RequestMapping("/order/query/{userId}")
	public List<Order> orderQuery(@PathVariable String userId) {
		return orderService.orderQuery(userId);
	}
	@RequestMapping("/order/delete/{orderId}")
	public SysResult deleteOrder(@PathVariable String orderId) {
		try {
			orderService.deleteOrder(orderId);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "删除失败", null);
		}
	}
	@RequestMapping("/order/pay/{orderId}")
	public SysResult payOrder(@PathVariable String orderId) {
		try {
			Integer state=orderService.payOrder(orderId);
			if (state==1) {
				return SysResult.build(201, "已经支付了", null);
			}else {
				return SysResult.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "支付失败", null);
		}
	}
	@RequestMapping("/order/queryAll")
	public List<Order> orderQueryAll() {
		return orderService.orderQueryAll();
	}
	@RequestMapping("/order/recieveOrder")
	public SysResult recieveOrder(String orderId) {
		orderService.recieveOrder(orderId);
		return SysResult.build(200, null, null);
	}
}
