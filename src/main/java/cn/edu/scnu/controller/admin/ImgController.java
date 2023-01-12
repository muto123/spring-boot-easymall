package cn.edu.scnu.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.scnu.vo.PicUploadResult;
import cn.edu.scnu.vo.SysResult;

@RestController
public class ImgController {
	
	@Value("${pic.path}")
	private String basePath;
	
	@RequestMapping("/uploadImg")
	public PicUploadResult uploadImg(@RequestParam("pic") MultipartFile file) throws IOException {
		// 获取图名称，后缀名称
		String originName = file.getOriginalFilename();
		// 截取后缀substring split (".png" ".jgp")
		String extName = originName.substring(originName.lastIndexOf("."));
		// 生成多级路径
		String dir = "";
		// /a/2/e/a/2/3/j/p
		for (int i = 0; i < 8; i++) {
			dir += "/" + Integer.toHexString(new Random().nextInt(16));
		}
		String realpath = basePath;
		realpath += "/upload";
		String imgurl = "/upload" + dir + "/" + originName;
		String fname = imgurl.substring(0,imgurl.lastIndexOf("."));
		imgurl=fname+extName;
		File file1 = new File(realpath + dir, originName);
		if (!file1.getParentFile().exists()) {
			file1.getParentFile().mkdirs();
			file1.createNewFile();
		}
		// 上传文件
		try {
			file.transferTo(file1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		PicUploadResult picUploadResult = new PicUploadResult();
		picUploadResult.setError(0);
		picUploadResult.setUrl("http://image.easymall.com"+imgurl);
		return picUploadResult;
	}
}
