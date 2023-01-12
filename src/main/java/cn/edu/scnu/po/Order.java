package cn.edu.scnu.po;

import java.sql.Timestamp;
import java.util.List;

import cn.edu.scnu.pojo.OrderItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
	private String orderId;
	private Double orderMoney;
	private String orderReceiverinfo;
	private Integer orderPaystate;
	private Timestamp orderTime ;
	private String userId;
	private List<OrderItem> orderItems;
	private Integer orderDeliverstate;
	private Integer orderReceive;
}
