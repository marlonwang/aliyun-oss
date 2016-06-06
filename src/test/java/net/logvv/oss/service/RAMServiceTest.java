package net.logvv.oss.service;

import net.logvv.oss.AbstractTestBase;
import net.logvv.oss.commom.utils.DateUtils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RAMServiceTest extends AbstractTestBase {

	@Autowired
	private RAMService ramService;
	
	@Test
	public void testApplyRequestId() {
		System.out.println(ramService.applyRequestId("JSON"));
		
	}
	
	@Test
	public void testDemoApplyRequestId(){
		System.out.println(ramService.demoApplyRequestId());
	}
	
	@Test
	public void ISO8601DateFormat()
	{
//		System.out.println(DateUtils.date2Str(DateTime.now(),"yyyy-MM-dd'T'HH:mm:ss'Z'"));
//		
//		System.out.println("2016-06-06T17:56:24Z".replaceAll(":", "%3A"));
//		
//		System.out.println("2016-06-06T17:56:24Z".substring(0,3));
//		System.out.println("2016-06-06T17:56:24Z".substring(3));
		
		System.out.println(DateUtils.parseISO8601());
	}

}
