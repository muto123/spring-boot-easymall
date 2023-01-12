package cn.edu.scnu.service.Impl;

import java.util.ArrayList;
import java.util.List;

import cn.edu.scnu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.edu.scnu.mapper.CategoryMapper;
import cn.edu.scnu.mapper.ProductMapper;
import cn.edu.scnu.pojo.Category;
import cn.edu.scnu.pojo.Product;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ProductMapper productMapper;

	@Override
	public void itemChange(String productCategory1, String productCategory2) {
		QueryWrapper<Category> categoryQueryWrapper=new QueryWrapper<>();
		categoryQueryWrapper.eq("name", productCategory1);
		Category category=categoryMapper.selectOne(categoryQueryWrapper);
		category.setName(productCategory2);
		category.setDescription(productCategory2);
		QueryWrapper<Product> productQueryWrapper=new QueryWrapper<>();
		productQueryWrapper.eq("product_category", productCategory1);
		List<Product> products=productMapper.selectList(productQueryWrapper);
		for(Product product:products) {
			product.setProductCategory(productCategory2);
			productMapper.updateById(product);
		}
		categoryMapper.update(category, categoryQueryWrapper);
	}

	@Override
	public void itemDelete(String productCategory) {
		QueryWrapper<Category> categoryQueryWrapper=new QueryWrapper<>();
		categoryQueryWrapper.eq("name", productCategory);
		categoryMapper.delete(categoryQueryWrapper);
	}

	@Override
	public void itemAdd(String productCategory) {
		Category category=Category.builder().name(productCategory).description(productCategory).build();
		categoryMapper.insert(category);
	}

	@Override
	public List<Category> allCategory() {
		QueryWrapper<Category> categoryQueryWrapper=new QueryWrapper<>();
		return categoryMapper.selectList(categoryQueryWrapper);
	}

	@Override
	public List<Integer> categoryNum() {
		List<Integer> cate = new ArrayList<>();
		QueryWrapper<Category> categoryQueryWrapper=new QueryWrapper<>();
		List<Category> categories=categoryMapper.selectList(categoryQueryWrapper);
		for(Category category:categories) {
			QueryWrapper<Product> ProductQueryWrapper=new QueryWrapper<>();
			ProductQueryWrapper.eq("product_category", category.getName());
			List<Product> products=productMapper.selectList(ProductQueryWrapper);
			int num=0;
			for(Product product:products) {
				num+=product.getSoldnum();
			}
			cate.add(num);
		}
		return cate;
	}
	
}
