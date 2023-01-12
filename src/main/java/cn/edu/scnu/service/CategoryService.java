package cn.edu.scnu.service;

import java.util.List;

import cn.edu.scnu.pojo.Category;

public interface CategoryService {

	void itemChange(String productCategory1, String productCategory2);

	void itemDelete(String productCategory);

	void itemAdd(String productCategory);

	List<Category> allCategory();

	List<Integer> categoryNum();

}
