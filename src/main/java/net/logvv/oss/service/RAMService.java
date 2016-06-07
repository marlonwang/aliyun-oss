package net.logvv.oss.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.logvv.oss.commom.utils.DateUtils;
import net.logvv.oss.commom.utils.JsonUtils;
import net.logvv.oss.commom.utils.RestServiceUtils;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.ram.model.v20150501.CreateUserRequest;
import com.aliyuncs.ram.model.v20150501.CreateUserResponse;
import com.aliyuncs.ram.model.v20150501.GetUserRequest;
import com.aliyuncs.ram.model.v20150501.GetUserResponse;
import com.aliyuncs.ram.model.v20150501.GetUserResponse.User;
import com.aliyuncs.ram.model.v20150501.UpdateUserRequest;
import com.aliyuncs.ram.model.v20150501.UpdateUserResponse;

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
    
    @Value("${aliyun.region}")
    private String aliyunRegion;

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
    public String applyRequestId(String format,String userName)
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

        try {

            Map<String,String> param = new HashMap<>();
            param.put("Format", format);
            param.put("Version","2015-05-01");
            param.put("SignatureMethod","HMAC-SHA1");
            param.put("SignatureVersion","1.0");
            param.put("SignatureNonce",UUID.randomUUID().toString());
            param.put("Timestamp",DateUtils.getISO8601Time(new Date()));
            param.put("AccessKeyId",ramAccessKeyId);
            param.put("RegionId", aliyunRegion);
            // 以下参数创建ram 账户
            param.put("Action", "CreateUser");
            param.put("UserName", userName);

            String sign = SignatureService.generateSignature(SignatureService.linkPlainParam(param,true),ramAccessKeySecret);
            // 注意 这里的时间戳中的 %需要被编码为%25

            // 查看签名后的字符串
            String encodeSign = SignatureService.encodeIgnoreWave(sign);
            System.out.println("non-encode sign:"+sign);
            System.out.println("After sign:" + encodeSign);
            param.put("Signature",encodeSign);

            String fullReqParam = ramAccessUrl + SignatureService.linkPlainParam(param,false); 
            // 这里时间戳的% 需要按照RFC3986规则 编码为%25
            
            // 请求的时间戳需要处理成 Timestamp=2015-08-18T03%3A15%3A45Z
            String httpHead = fullReqParam.substring(0, 8);
            String httpTail = fullReqParam.substring(8);
            httpTail = httpTail.replaceAll(":", "%3A");
            
            System.out.println("request string:"+httpHead + httpTail);
            
            //RestServiceUtils.doGet(httpHead + httpTail,CreateUserResponse.class);
            
            return httpHead + httpTail;

        } catch (Exception e) {
            LOGGER.info("request to aliyun error:{}",e);
            
            return null;
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

        } catch (Exception e) {
            LOGGER.info("request to aliyun error:{}",e);
            e.printStackTrace();
        }
		return obj;
    }
    
    public DefaultAcsClient initRamConfig()
    {
        IClientProfile profile = DefaultProfile.getProfile(aliyunRegion,ramAccessKeyId,ramAccessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        
        return client;
    }
    
    /**
     * 
     * createRamUser
     * 调用ram sdk 创建ram<br/>
     * @return void  返回类型 
     * @author wangwei
     * @date 2016年6月7日 上午11:07:35 
     * @version  [1.0, 2016年6月7日]
     * @since  version 1.0
     */
    public void createRamUser(String userName)
    {
    	// 构建一个 Aliyun Client, 用于发起请求
        // 构建Aliyun Client时需要设置AccessKeyId和AccessKeySevcret
        // RAM是Global Service, API入口位于华东 1 (杭州) , 这里Region填写"cn-hangzhou"
        IClientProfile profile = DefaultProfile.getProfile(aliyunRegion,ramAccessKeyId,ramAccessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        // 构造"CreateUser"请求
        final CreateUserRequest request = new CreateUserRequest();

        //设置参数 - UserName
        request.setUserName(userName);

        // 发起请求，并得到response
        try {
            final CreateUserResponse response = client.getAcsResponse(request);

            System.out.println("UserName: " + response.getUser().getUserName());
            System.out.println("CreateTime: " + response.getUser().getCreateDate());
        } catch (ClientException e) {
            System.out.println("Failed.");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
        }
    }
    
    /**
     * getRamUser
     * 查看ram用户信息 <br/>
     * @return
     * @return User  返回类型 
     * @author wangwei
     * @date 2016年6月7日 上午11:22:53 
     * @version  [1.0, 2016年6月7日]
     * @since  version 1.0
     */
    public User getRamUser(String userName)
    {
    	final GetUserRequest request = new GetUserRequest();
    	// AttachPolicyToGroupRequest
    	// AttachPolicyToUserRequest
    	
    	request.setUserName(userName);
    	
    	try {
			final GetUserResponse response = initRamConfig().getAcsResponse(request);
			
			return response.getUser();
			
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info("Failed to get ram user info, error:{}",e);
			return null;
		}
    }
    
    public void updateRamUser()
    {
    	DefaultAcsClient client = initRamConfig();
    	
    	final UpdateUserRequest request = new UpdateUserRequest();
    	
    	request.setNewDisplayName("alex wang");
    	request.setNewEmail("marlonwang@163.com");
    	request.setNewMobilePhone("13714410115");
    	request.setNewComments("字节渲染了我的夜,我沉浸在字节的黑");
    	request.setUserName("alex");
    	request.setNewUserName("alexwang");
    	
    	try {
			final UpdateUserResponse response = client.getAcsResponse(request);
			
			System.out.println(JsonUtils.obj2json(response.getUser()));
			
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info("Failed to get ram user info, error:{}",e);
			e.printStackTrace();
		}
    	
    }

}
