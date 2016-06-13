package net.logvv.oss.service;

import static org.junit.Assert.*;
import net.logvv.oss.AbstractTestBase;

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

}
