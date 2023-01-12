package cn.edu.scnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scnu.pojo.Cart;
import cn.edu.scnu.service.CartService;
import cn.edu.scnu.vo.SysResult;

@RestController
public class CartController {
	@Autowired
	private CartService cartService;
	@RequestMapping("/cart/save")
	public SysResult cartSave(String productId,String userId,Integer num) {
		try {
			cartService.cartSave(Cart.builder().productId(productId).userId(userId).num(num).build());
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "添加失败", null);
		}
	}
	@RequestMapping("/cart/query")
	public List<Cart> cartQuery(String userId){
		return cartService.selectCartByUserId(userId);
	}
	@RequestMapping("/cart/update")
	public SysResult cartUpdate(String productId,String userId,Integer num) {
		try {
			cartService.cartUpdate(Cart.builder().productId(productId).userId(userId).num(num).build());
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "保存失败", null);
		}
	}
	@RequestMapping("/cart/delete")
	public SysResult cartDelete(String productId,String userId) {
		try {
			cartService.cartDelete(Cart.builder().productId(productId).userId(userId).build());
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "删除失败", null);
		}
	}
}
