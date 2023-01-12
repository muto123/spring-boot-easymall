package cn.edu.scnu.service.Impl;

import java.util.List;

import cn.edu.scnu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.edu.scnu.mapper.CartMapper;
import cn.edu.scnu.mapper.ProductMapper;
import cn.edu.scnu.pojo.Cart;
import cn.edu.scnu.pojo.Product;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private ProductMapper productMapper;

	@Override
	public void cartSave(Cart cart) {
		QueryWrapper<Cart> cartQueryWrapper=new QueryWrapper<>();
		cartQueryWrapper.eq("product_id", cart.getProductId())
			.eq("user_id", cart.getUserId());
		QueryWrapper<Product> productQueryWrapper=new QueryWrapper<>();
		productQueryWrapper.eq("product_id", cart.getProductId());
		Cart cart1=cartMapper.selectOne(cartQueryWrapper);
		Product product=productMapper.selectOne(productQueryWrapper);
		
		if(cart1==null) {
			cart.setProductImage(product.getProductImgurl());
			cart.setProductName(product.getProductName());
			cart.setProductPrice(product.getProductPrice());
			cartMapper.insert(cart);
		}else {
			cart1.setNum(cart1.getNum()+cart.getNum());
			cartMapper.update(cart1, cartQueryWrapper);
		}
	}

	@Override
	public List<Cart> selectCartByUserId(String userId) {
		QueryWrapper<Cart> cartQueryWrapper=new QueryWrapper<>();
		cartQueryWrapper.eq("user_id", userId);
		return cartMapper.selectList(cartQueryWrapper);
	}

	@Override
	public void cartUpdate(Cart cart) {
		QueryWrapper<Cart> cartQueryWrapper=new QueryWrapper<>();
		cartQueryWrapper.eq("product_id", cart.getProductId())
			.eq("user_id", cart.getUserId());//查找cart中等于productId和userId的
		
		Cart cart1=cartMapper.selectOne(cartQueryWrapper);
		
		if(cart.getNum()==0) {
			cartMapper.delete(cartQueryWrapper);
			return;
		}
		
		cart1.setNum(cart.getNum());
		cartMapper.update(cart1, cartQueryWrapper);
	}

	@Override
	public void cartDelete(Cart cart) {
		QueryWrapper<Cart> cartQueryWrapper=new QueryWrapper<>();
		cartQueryWrapper.eq("product_id", cart.getProductId())
			.eq("user_id", cart.getUserId());//查找cart中等于productId和userId的
		cartMapper.delete(cartQueryWrapper);
	}

}
