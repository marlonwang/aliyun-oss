package net.logvv.oss.service;

import static org.junit.Assert.assertTrue;
import net.logvv.oss.AbstractTestBase;
import net.logvv.oss.model.BaseImgInfo;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IMGServiceTest extends AbstractTestBase {
	
	@Autowired
	private IMGService imgService;
	
	@Test
	public void testUploadImage() {
		String filepath = "D:\\QMDownload\\manmankan\\girl_hd.jpg";
		
		String imgurl = imgService.uploadImage(filepath);
		// 对于名访问操作需要设定 bucket的权限为公共读
		System.out.println(imgurl);
		
		assertTrue(StringUtils.isNotBlank(imgurl));
	}
	
	@Test
	public void testGetImgInfo(){
		String key = "dBwcFr.jpg";
		BaseImgInfo image;
		image = imgService.getImgInfo(key);
		System.out.println("H:"+image.getHeight()+";W:"+image.getWidth()+";S:"+image.getSize());
	}
	
	@Test
	public void testGetCiImage(){
		String key = "dBwcFr.jpg";
		int radius = 300;
		int origin = 0;
		System.out.println(imgService.getCiImage(key, radius, origin));
	}
	
	@Test
	public void testRetScaleImage(){
		String key = "dBwcFr.jpg";
		int w = 300;int h = 300;
		System.out.println(imgService.retScaleImage(key, w, h)); 
	}

}