package net.logvv.oss.service;

import net.logvv.oss.AbstractTestBase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCallBackTest extends AbstractTestBase{
	
	@Autowired
	private TestCallBack testCallBack;

	@Test
	public void testPutObjectWithCallBack() {
		try {
			testCallBack.putObjectWithCallBack();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("done.");;
	}

}
