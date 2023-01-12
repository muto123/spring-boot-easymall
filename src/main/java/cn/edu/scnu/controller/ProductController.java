package cn.edu.scnu.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scnu.pojo.Category;
import cn.edu.scnu.pojo.Product;
import cn.edu.scnu.service.ProductService;
import cn.edu.scnu.vo.EasyUIResult;
import cn.edu.scnu.vo.SysResult;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	@RequestMapping("/products/pageManage")
	public EasyUIResult productPageQuery() {
		EasyUIResult result=productService.allProductQuery();
		return result;
	}
	@RequestMapping("/products/pageManageSort")
	public EasyUIResult productPageQuerySort() {
		EasyUIResult result=productService.allProductQuerySort();
		return result;
	}
	@RequestMapping("/products/page")
	public EasyUIResult proPageQuery(String category) {
		if(category==null) {
			EasyUIResult result=productService.allUpProductQuery();
			return result;
		}else {
			EasyUIResult result=productService.allUpProductQuery(category);
			return result;
		}
	}
	@RequestMapping("/products/item/{productId}")
	public Product productInfo(@PathVariable String productId) {
		return productService.selectProdById(productId);
	}
	@RequestMapping("/products/searchs/query")
	public List<Product> searchProduct(String query,String page,String rows) {
		List<Product> result=productService.selectProdByName(query,page,rows);
		return result;
	}
	@RequestMapping("/products/update")
	public SysResult updateProduct(String productId,String productCategory,String productName,
			Double productPrice,int productNum,String productImgurl,String productDescription) {
		Product product=Product.builder().productId(productId).productCategory(productCategory)
				.productName(productName).productPrice(productPrice).productNum(productNum)
				.productImgurl(productImgurl).productDescription(productDescription).build();
		productService.updateProduct(product);
		return SysResult.build(200, null, null);
	}
	@RequestMapping("products/productsCategory")
	public List<Category> productsCategory() {
		return productService.productCategory();
	}
	@RequestMapping("products/save")
	public SysResult productsSave(String productCategory,String productName,Double productPrice,
			Integer productNum,String productImgurl,String productDescription) {
		String productId=UUID.randomUUID().toString();
		Product product=Product.builder().productCategory(productCategory).productName(productName)
				.productPrice(productPrice).productNum(productNum).productImgurl(productImgurl)
				.productDescription(productDescription).productId(productId).build();
		productService.productSave(product);
		return SysResult.build(200, null, null);
	}
}
