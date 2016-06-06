package net.logvv.oss.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.logvv.oss.commom.utils.DateUtils;
import net.logvv.oss.commom.utils.RestServiceUtils;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Author : wangwei
 * Created on 2016/05/30 23:46.
 */

@Service
public class RAMService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RAMService.class);

    @Value("${ram.access.url}")
    private String ramAccessUrl;
    
    @Value("${ram.accessKeyId_1}")
    private String ramAccessKeyId;
    
    @Value("${ram.accessKeySecret_1}")
    private String ramAccessKeySecret;

    /**
     * applyRequestId
     * 这里用一句话描述这个方法的作用<br/>
     * 这里描述这个方法适用条件 – 可选<br/>
     * @param format JSON\XML
     * @return
     * @return Object  返回类型 
     * @author wangwei
     * @date 2016年6月6日 下午3:50:34 
     * @version  [1.0, 2016年6月6日]
     * @since  version 1.0
     */
    public Object applyRequestId(String format)
    {
        /**
         https://ram.aliyuncs.com/
         ?Format=xml
         &Version=2015-05-01
         &Signature=Pc5WB8gokVn0xfeu%2FZV%2BiNM1dgI%3D
         &SignatureMethod=HMAC-SHA1
         &SignatureNonce=15215528852396
         &SignatureVersion=1.0H
         &AccessKeyId=key-test
         &Timestamp=2012-06-01T12:00:00Z
         */
        Object obj = null;

        try {

            Map<String,String> param = new HashMap<>();
            param.put("Format", format);
            param.put("Version","2015-05-01");
            param.put("SignatureMethod","HMAC-SHA1");
            param.put("SignatureVersion","1.0");
            param.put("SignatureNonce",UUID.randomUUID().toString());
            param.put("Timestamp",DateUtils.parseISO8601());
            param.put("AccessKeyId",ramAccessKeyId);
            // 以下参数创建ram 账户
            param.put("Action", "CreateUser");
            param.put("UserName", "test_user");

            String sign = SignatureService.generateSignature(SignatureService.linkPlainParam(param,false),ramAccessKeySecret);

            // 查看签名后的字符串
            String encodeSign = SignatureService.encodeIgnoreWave(sign);
            System.out.println("non-encode sign:"+sign);
            System.out.println("After sign:" + encodeSign);
            param.put("Signature",encodeSign);

            String fullReqParam = ramAccessUrl + SignatureService.linkPlainParam(param,false);
            
            // 请求的时间戳需要处理成 Timestamp=2015-08-18T03%3A15%3A45Z
            String httpHead = fullReqParam.substring(0, 8);
            String httpTail = fullReqParam.substring(8);
            httpTail = httpTail.replaceAll(":", "%3A");
            
            		
            System.out.println("request string:"+httpHead + httpTail);

            obj = RestServiceUtils.doGet(httpHead + httpTail, JSONObject.class);
            return obj;

        } catch (Exception e) {
            LOGGER.info("request to aliyun error:{}",e);
            return obj.toString();
        }
    }

    public Object demoApplyRequestId()
    {
        /**
         https://ram.aliyuncs.com/
         ?Format=xml
         &Version=2015-05-01
         &Signature=Pc5WB8gokVn0xfeu%2FZV%2BiNM1dgI%3D
         &SignatureMethod=HMAC-SHA1
         &SignatureNonce=15215528852396
         &SignatureVersion=1.0H
         &AccessKeyId=key-test
         &Timestamp=2012-06-01T12:00:00Z
         */
    	/*ttps://ram.aliyuncs.com/?
    	UserName=test
    	&SignatureVersion=1.0
    	&Format=JSON
    	&Timestamp=2015-08-18T03%3A15%3A45Z
    	&AccessKeyId=testid
    	&SignatureMethod=HMAC-SHA1
    	&Version=2015-05-01
    	&Action=CreateUser
    	&SignatureNonce=6a6e0ca6-4557-11e5-86a2-b8e8563dc8d2
    			
    			*/
        Object obj = null;

        try {

            Map<String,String> param = new HashMap<>();
            param.put("Format", "JSON");
            param.put("Version","2015-05-01");
            param.put("SignatureMethod","HMAC-SHA1");
            param.put("SignatureVersion","1.0");
            param.put("SignatureNonce","6a6e0ca6-4557-11e5-86a2-b8e8563dc8d2");
            param.put("Timestamp","2015-08-18T03:15:45Z");
            param.put("AccessKeyId","testid");
            // 以下参数创建ram 账户
            param.put("Action", "CreateUser");
            param.put("UserName", "test");

            String sign = SignatureService.generateSignature(SignatureService.linkPlainParam(param,true),"testsecret");

            // 查看签名后的字符串
            String encodeSign = SignatureService.encodeIgnoreWave(sign);
            System.out.println("non-encode sign:"+sign);
            System.out.println("After sign:" + encodeSign);
            param.put("Signature",encodeSign);

            String fullReqParam = ramAccessUrl + SignatureService.linkPlainParam(param,false);
            
            // 请求的时间戳需要处理成 Timestamp=2015-08-18T03%3A15%3A45Z
            String httpHead = fullReqParam.substring(0, 8);
            String httpTail = fullReqParam.substring(8);
            httpTail = httpTail.replaceAll(":", "%3A");
            
            		
            System.out.println("request string:"+httpHead + httpTail);

            obj = RestServiceUtils.doGet(httpHead + httpTail, JSONObject.class);
            return obj;

        } catch (Exception e) {
            LOGGER.info("request to aliyun error:{}",e);
            return obj.toString();
        }
    }

}
