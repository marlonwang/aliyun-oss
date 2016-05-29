package net.logvv.oss.callback;

import net.logvv.oss.commom.utils.IPUtils;
import net.logvv.oss.commom.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CallBackRest {
	private static final Logger LOGGER = LoggerFactory.getLogger(CallBackRest.class);
	
	@RequestMapping(value="/v1/callback",method=RequestMethod.POST)
	public Object response(@RequestBody String callbackStr,HttpServletRequest request)
	{
		LOGGER.info("Callback from remote ip:{}",IPUtils.getIpAddress(request));
		LOGGER.info("content:{}",callbackStr);

		if(StringUtils.isEmpty(callbackStr)){
			return null;
		}
		Map<String,String> responseMap = new HashMap<>();
		String[] callArray = callbackStr.split("&");
		for(int i = 0;i<callArray.length;i++)
		{
			String[] paras = callArray[i].split("=");
			responseMap.put(paras[0],paras[1]);
		}
//		PrintWriter writer = null;
//		try
//		{
//			JSONObject responseJson = JSONObject.fromObject(JsonUtils.obj2json(responseMap));
//
//			response.setContentLength(callbackStr.length());
//			response.setContentType("application/json; charset=utf-8");
//			response.setCharacterEncoding("UTF-8");
//
//			writer = response.getWriter();
//			writer.append(responseJson.toString());
//
//			LOGGER.info("xxoo:{}",response.toString());
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}finally {
//			writer.close();
//		}

		LOGGER.info("responseMap:{}",JsonUtils.obj2json(responseMap));

		return JsonUtils.obj2json(responseMap);

	}
}
