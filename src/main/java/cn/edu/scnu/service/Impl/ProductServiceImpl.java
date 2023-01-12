package cn.edu.scnu.service.Impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.edu.scnu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.edu.scnu.mapper.CategoryMapper;
import cn.edu.scnu.mapper.ProductMapper;
import cn.edu.scnu.pojo.Category;
import cn.edu.scnu.pojo.Product;
import cn.edu.scnu.vo.EasyUIResult;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public EasyUIResult allProductQuery() {
		//1.创建一个返回的对象，将查询数据set进去然后返回
		EasyUIResult result=new EasyUIResult();
		//2.封装第一个属性total的数量
		QueryWrapper<Product> productQueryWrapper=new QueryWrapper<>();
		Integer total=productMapper.selectCount(productQueryWrapper);
		//3.封装第二个属性List<Product> pList
		List<Product> pList=productMapper.selectList(productQueryWrapper);
		result.setTotal(total);
		result.setRows(pList);
		return result;
	}

	@Override
	public Product selectProdById(String productId) {
		return productMapper.selectById(productId);
	}

	@Override
	public List<Product> selectProdByName(String prodName, String page, String rows) {
		QueryWrapper<Product> productQueryWrapper=new QueryWrapper<>();
		productQueryWrapper.like("product_name", prodName);
		List<Product> pList=productMapper.selectList(productQueryWrapper);
		return pList;
	}

	@Override
	public EasyUIResult allUpProductQuery() {
		//1.创建一个返回的对象，将查询数据set进去然后返回
		EasyUIResult result=new EasyUIResult();
		//2.封装第一个属性total的数量
		QueryWrapper<Product> productQueryWrapper=new QueryWrapper<>();
		productQueryWrapper.eq("upordown", 1);
		Integer total=productMapper.selectCount(productQueryWrapper);
		//3.封装第二个属性List<Product> pList
		List<Product> pList=productMapper.selectList(productQueryWrapper);
		result.setTotal(total);
		result.setRows(pList);
		return result;
	}
	
	@Override
	public EasyUIResult allUpProductQuery(String category) {
		//1.创建一个返回的对象，将查询数据set进去然后返回
		EasyUIResult result=new EasyUIResult();
		//2.封装第一个属性total的数量
		QueryWrapper<Product> productQueryWrapper=new QueryWrapper<>();
		productQueryWrapper.eq("product_category", category);
		productQueryWrapper.eq("upordown", 1);
		Integer total=productMapper.selectCount(productQueryWrapper);
		//3.封装第二个属性List<Product> pList
		List<Product> pList=productMapper.selectList(productQueryWrapper);
		result.setTotal(total);
		result.setRows(pList);
		return result;
	}

	@Override
	public void upordownProduct(String[] ids,int upordown) {
		for(String id:ids) {
			Product product=productMapper.selectById(id);
			product.setUpordown(upordown);
			productMapper.updateById(product);
		}
	}

	@Override
	public void updateProduct(Product product) {
		Product prod=productMapper.selectById(product.getProductId());
		prod.setProductId(product.getProductId());
		prod.setProductCategory(product.getProductCategory());
		prod.setProductName(product.getProductName());
		prod.setProductPrice(product.getProductPrice());
		prod.setProductNum(product.getProductNum());
		prod.setProductImgurl(product.getProductImgurl());
		prod.setProductDescription(product.getProductDescription());
		productMapper.updateById(prod);
	}

	@Override
	public List<Category> productCategory() {
		QueryWrapper<Category> categoryQueryWrapper=new QueryWrapper<>();
		return categoryMapper.selectList(categoryQueryWrapper);
	}

	@Override
	public EasyUIResult allProductQuerySort() {
		//1.创建一个返回的对象，将查询数据set进去然后返回
		EasyUIResult result=new EasyUIResult();
		//2.封装第一个属性total的数量
		QueryWrapper<Product> productQueryWrapper=new QueryWrapper<>();
		Integer total=productMapper.selectCount(productQueryWrapper);
		//3.封装第二个属性List<Product> pList
		List<Product> pList=productMapper.selectList(productQueryWrapper);
		Collections.sort(pList, new Comparator<Product>() {
			@Override
			public int compare(Product o1, Product o2) {
				int diff=o1.getSoldnum()-o2.getSoldnum();
				if(diff<0) {
					return 1;
				}else if(diff>0) {
					return -1;
				}
				return 0;
			}
		});
		result.setTotal(total);
		result.setRows(pList);
		return result;
	}

	@Override
	public void productSave(Product product) {
		productMapper.insert(product);
	}

}
