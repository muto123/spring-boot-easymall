package cn.edu.scnu.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName(value="t_product")
public class Product implements Serializable {
	private static final long serialVersionUID = -5644799954031156649L;
    //value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
	@TableId(value = "product_id")
	private String productId;
	private String productName;
	private Double productPrice;
	private String productCategory;
	private String productImgurl;
	private Integer productNum;
	private String productDescription;
	private Integer soldnum;
	private Integer upordown;
}
