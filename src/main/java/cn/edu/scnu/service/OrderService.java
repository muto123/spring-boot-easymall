package cn.edu.scnu.service;

import java.util.List;

import cn.edu.scnu.po.Order;
import cn.edu.scnu.pojo.OrderItem;

public interface OrderService {
	
	void addOrderItems(String orderReceiverinfo,String userId,Double orderMoney);

	List<Order> orderQuery(String userId);

	void deleteOrder(String orderId);

	Integer payOrder(String orderId);

	List<Order> orderQueryAll();

	void deliverOrder(String orderId);

	void recieveOrder(String orderId);

}
