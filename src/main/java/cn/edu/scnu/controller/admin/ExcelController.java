package cn.edu.scnu.controller.admin;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scnu.pojo.Product;
import cn.edu.scnu.service.ProductService;
import cn.edu.scnu.vo.EasyUIResult;

@RestController
public class ExcelController {
	@Autowired
	private ProductService productService;
	@RequestMapping("/products/exportExcel")
	public void downloadExcel(HttpServletResponse response) throws Exception {
		EasyUIResult result=productService.allProductQuerySort();
		HSSFWorkbook workbook=new HSSFWorkbook();//文档对象
		HSSFSheet exsheet=workbook.createSheet();//表单
		// 创建合并单元格对象
		CellRangeAddress cellRangeAddress=new CellRangeAddress(0, 0, 0, 5);
		exsheet.addMergedRegion(cellRangeAddress);
		//创建头标题行
		HSSFRow createRow=exsheet.createRow(0);//第一行
		createRow.createCell(0).setCellValue("销售榜单");
		HSSFRow row1=exsheet.createRow(1);//第二行
		row1.createCell(0).setCellValue("商品ID");
		row1.createCell(1).setCellValue("商品名称");
		row1.createCell(2).setCellValue("商品类目");
		row1.createCell(3).setCellValue("商品价格");
		row1.createCell(4).setCellValue("商品描述");
		row1.createCell(5).setCellValue("商品数量");
		for(int i=0;i<result.getRows().size();i++) {
			HSSFRow row=exsheet.createRow(i+2);
			Product product=(Product)result.getRows().get(i);
			row.createCell(0).setCellValue(product.getProductId());
			row.createCell(1).setCellValue(product.getProductName());
			row.createCell(2).setCellValue(product.getProductCategory());
			row.createCell(3).setCellValue(product.getProductPrice());
			row.createCell(4).setCellValue(product.getProductDescription());
			row.createCell(5).setCellValue(product.getSoldnum());
		}
		//把响应头数据类型设置为任意二进制流，用于上传下载
		response.setContentType("application/vnd.ms-excel");
		//告诉浏览器通过下载方式打开，并设置下载文件名
		response.setHeader("Content-Disposition", "attachment;fileName="+ new String("销售榜单.xls".getBytes(),"ISO8859-1"));
		ServletOutputStream sos = response.getOutputStream();
		workbook.write(sos);
		sos.flush();
		if(sos != null){
		    sos.close();
		}
	}
}
