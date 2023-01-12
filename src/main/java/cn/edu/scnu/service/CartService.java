package cn.edu.scnu.service;

import java.util.List;

import cn.edu.scnu.pojo.Cart;

public interface CartService {
	public void cartSave(Cart cart);

	public List<Cart> selectCartByUserId(String userId);

	public void cartUpdate(Cart cart);

	public void cartDelete(Cart cart);
}
