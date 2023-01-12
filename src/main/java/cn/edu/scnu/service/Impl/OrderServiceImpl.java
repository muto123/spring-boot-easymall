package cn.edu.scnu.service.Impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.edu.scnu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.edu.scnu.mapper.CartMapper;
import cn.edu.scnu.mapper.OrderItemMapper;
import cn.edu.scnu.mapper.OrdersMapper;
import cn.edu.scnu.mapper.ProductMapper;
import cn.edu.scnu.po.Order;
import cn.edu.scnu.pojo.Cart;
import cn.edu.scnu.pojo.OrderItem;
import cn.edu.scnu.pojo.Orders;
import cn.edu.scnu.pojo.Product;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public void addOrderItems(String orderReceiverinfo,String userId,Double orderMoney) {
		QueryWrapper<Cart> cartQueryWrapper=new QueryWrapper<>();
		cartQueryWrapper.eq("user_id", userId);
		List<Cart> cart=cartMapper.selectList(cartQueryWrapper);
		String orderId=UUID.randomUUID().toString();
		for(int i=0;i<cart.size();i++) {
			orderItemMapper.insert(OrderItem.builder().orderId(orderId).productId(cart.get(i).getProductId())
					.num(cart.get(i).getNum()).productImage(cart.get(i).getProductImage())
					.productName(cart.get(i).getProductName()).productPrice(cart.get(i).getProductPrice()).build());
		}
		ordersMapper.insert(Orders.builder().orderId(orderId).orderMoney(orderMoney)
				.orderReceiverinfo(orderReceiverinfo).orderTime(new Timestamp(new java.util.Date().getTime()))
				.userId(userId).build());
		//cartMapper.delete(cartQueryWrapper);
	}

	@Override
	public List<Order> orderQuery(String userId) {
		List<Order> o=new ArrayList<>();
		
		QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
		ordersQueryWrapper.eq("user_id", userId);
		List<Orders> orders=ordersMapper.selectList(ordersQueryWrapper);
		for(Orders oo:orders) {
			Order order=Order.builder().orderId(oo.getOrderId()).orderMoney(oo.getOrderMoney())
					.orderReceiverinfo(oo.getOrderReceiverinfo()).orderPaystate(oo.getOrderPaystate())
					.orderTime(oo.getOrderTime()).userId(oo.getUserId()).orderDeliverstate(oo.getOrderDeliverstate())
					.orderReceive(oo.getOrderReceive()).build();
			QueryWrapper<OrderItem> orderItemQueryWrapper=new QueryWrapper<>();
			orderItemQueryWrapper.eq("order_id", oo.getOrderId());
			List<OrderItem> orderItems=orderItemMapper.selectList(orderItemQueryWrapper);
			order.setOrderItems(orderItems);
			o.add(order);
		}
		
		return o;
	}

	@Override
	public void deleteOrder(String orderId) {
		QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
		ordersQueryWrapper.eq("order_id", orderId);
		QueryWrapper<OrderItem> orderItemQueryWrapper=new QueryWrapper<>();
		orderItemQueryWrapper.eq("order_id", orderId);
		orderItemMapper.delete(orderItemQueryWrapper);
		ordersMapper.delete(ordersQueryWrapper);
	}

	@Override
	public Integer payOrder(String orderId) {
		QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
		ordersQueryWrapper.eq("order_id", orderId);
		Orders orders=ordersMapper.selectOne(ordersQueryWrapper);
		Integer state=orders.getOrderPaystate();
		if(state==0) {
			orders.setOrderPaystate(1);
			ordersMapper.update(orders, ordersQueryWrapper);
		}
		//商品销售数量
		QueryWrapper<OrderItem> orderItemQueryWrapper=new QueryWrapper<>();
		orderItemQueryWrapper.eq("order_id", orderId);
		List<OrderItem> orderItem=orderItemMapper.selectList(orderItemQueryWrapper);
		for (OrderItem item:orderItem) {
			QueryWrapper<Product> productQueryWrapper=new QueryWrapper<>();
			productQueryWrapper.eq("product_id", item.getProductId());
			Product product=productMapper.selectOne(productQueryWrapper);
			product.setSoldnum(product.getSoldnum()+item.getNum());
			productMapper.update(product, productQueryWrapper);
		}
		return state;
	}

	@Override
	public List<Order> orderQueryAll() {
		List<Order> o=new ArrayList<>();
		
		QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
		List<Orders> orders=ordersMapper.selectList(ordersQueryWrapper);
		for(Orders oo:orders) {
			Order order=Order.builder().orderId(oo.getOrderId()).orderMoney(oo.getOrderMoney())
					.orderReceiverinfo(oo.getOrderReceiverinfo()).orderPaystate(oo.getOrderPaystate())
					.orderTime(oo.getOrderTime()).userId(oo.getUserId()).orderDeliverstate(oo.getOrderDeliverstate()).build();
			QueryWrapper<OrderItem> orderItemQueryWrapper=new QueryWrapper<>();
			orderItemQueryWrapper.eq("order_id", oo.getOrderId());
			List<OrderItem> orderItems=orderItemMapper.selectList(orderItemQueryWrapper);
			order.setOrderItems(orderItems);
			o.add(order);
		}
		
		return o;
	}

	@Override
	public void deliverOrder(String orderId) {
		Orders orders=ordersMapper.selectById(orderId);
		orders.setOrderDeliverstate(1);
		ordersMapper.updateById(orders);
	}

	@Override
	public void recieveOrder(String orderId) {
		Orders orders=ordersMapper.selectById(orderId);
		orders.setOrderReceive(1);
		ordersMapper.updateById(orders);
	}
	
}
