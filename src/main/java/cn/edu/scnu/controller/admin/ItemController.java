package cn.edu.scnu.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scnu.pojo.Category;
import cn.edu.scnu.service.CategoryService;
import cn.edu.scnu.service.ProductService;
import cn.edu.scnu.vo.SysResult;
import net.sf.json.JSONArray;

@RestController
public class ItemController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/theItem/instock")
	public SysResult uploadImg(String ids) {
		String[] id=ids.split(",");
		productService.upordownProduct(id,0);
		return SysResult.build(200, null, 0);
	}
	@RequestMapping("/theItem/reshelf")
	public SysResult reshelfImg(String ids) {
		String[] id=ids.split(",");
		productService.upordownProduct(id,1);
		return SysResult.build(200, null, 0);
	}
	@RequestMapping("/theItem/change")
	public SysResult itemChange(String productCategory1,String productCategory2) {
		categoryService.itemChange(productCategory1,productCategory2);
		return SysResult.build(200, null, null);
	}
	@RequestMapping("/theItem/delete")
	public SysResult itemDelete(String productCategory) {
		categoryService.itemDelete(productCategory);
		return SysResult.build(200, null, null);
	}
	@RequestMapping("/theItem/add")
	public SysResult itemadd(String productCategory) {
		categoryService.itemAdd(productCategory);
		return SysResult.build(200, null, null);
	}
	@RequestMapping("/theItem/echarts")
	public void itemEcharts(HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		List<String> xAxis=new ArrayList<>();
		List<Category> categories=categoryService.allCategory();
		List<Integer> value=categoryService.categoryNum();
		for(Category category:categories) {
			xAxis.add(category.getName());
		}
		Map<String,List> result=new HashMap<String,List>();
		result.put("xAxis",xAxis);
		result.put("value", value);
		JSONArray array=JSONArray.fromObject(result);
		String json=array.toString();
		try {
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
