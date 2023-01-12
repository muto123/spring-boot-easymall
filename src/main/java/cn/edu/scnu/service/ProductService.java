package cn.edu.scnu.service;

import java.util.List;

import cn.edu.scnu.pojo.Category;
import cn.edu.scnu.pojo.Product;
import cn.edu.scnu.vo.EasyUIResult;

public interface ProductService {

	public EasyUIResult allProductQuery();

	public Product selectProdById(String productId);

	public List<Product> selectProdByName(String prodName, String page, String rows);

	public EasyUIResult allUpProductQuery();

	public void upordownProduct(String[] id,int upordown);

	public void updateProduct(Product product);

	public EasyUIResult allUpProductQuery(String category);

	public List<Category> productCategory();

	public EasyUIResult allProductQuerySort();

	public void productSave(Product product);

}
