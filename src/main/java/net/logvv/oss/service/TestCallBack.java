package net.logvv.oss.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.internal.ResponseParsers.PutObjectCallbackReponseParser;
import com.aliyun.oss.internal.ResponseParsers.PutObjectReponseParser;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.Callback.CalbackBodyType;
import com.aliyun.oss.model.PutObjectRequest;

@Service
public class TestCallBack {
	
	@Autowired
	private OssService ossService;
	
	public void putObjectWithCallBack()
	{
		 OSSClient client = ossService.initOSSClient();
		 String filepath = "D:\\QMDownload\\issnake.png";
		 
		 //String callbackBody = "{\"bucket\": ${bucket},\"object\": ${object},\"mimeType\": ${mimeType},\"size\": ${size}}";
		 String callbackBody = "key=$(key)&etag=$(etag)";
		 Callback callback = new Callback();
		 //callback.setCalbackBodyType(CalbackBodyType.JSON);
		 callback.setCalbackBodyType(CalbackBodyType.URL);
		 callback.setCallbackUrl("127.0.0.1:31101/v1/callback");
		 callback.setCallbackBody(callbackBody);
		 
		 PutObjectRequest request = new PutObjectRequest(ossService.bucketName, ossService.bucketName, new File(filepath));
		 request.setCallback(callback);
		 
		 client.putObject(request);
	}
	
	public static void main(String[] args) {
		
	}
}
