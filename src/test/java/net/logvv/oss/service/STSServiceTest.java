package net.logvv.oss.service;

import net.logvv.oss.AbstractTestBase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class STSServiceTest extends AbstractTestBase {

	@Autowired
	private STSService stsService;
	
	@Test
	public void testApplySTStoken() {
		
		stsService.applySTStoken();
	}

}
