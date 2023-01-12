package cn.edu.scnu.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName(value="t_order_item")
public class OrderItem implements Serializable {
	private static final long serialVersionUID = -5644799954031156649L;
    //value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
	@TableId(type=IdType.AUTO)
    private Integer id;
	private String orderId;
	private String productId;
	private int num;
	private String productImage;
	private String productName;
	private Double productPrice;
}
