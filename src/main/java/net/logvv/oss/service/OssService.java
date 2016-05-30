package net.logvv.oss.service;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import net.logvv.oss.commom.utils.JsonUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class OssService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OssService.class);
	
	// endpoint是访问OSS的域名
	@Value("${aliyun.oss.endpoint}")
    public String endpoint;
	
	@Value("${aliyun.oss.accessKeyId}")
	public String accessKeyId;
	
	@Value("${aliyun.oss.accessKeySecret}")
	public String accessKeySecret;

    @Value("${oss.bucketName}")
    public String bucketName;

    // Object是OSS存储数据的基本单元，称为OSS的对象，也被称为OSS的文件
    //private static String objKey = "obj-001";
    
    /**
     * initOSSClient
     * 初始化OSSClient <br/>
     * @return
     * @return OSSClient  返回类型 
     * @author wangwei
     * @date 2016年5月10日 上午1:30:20 
     * @version  [1.0, 2016年5月10日]
     * @since  version 1.0
     */
    public OSSClient initOSSClient()
    {
        // 创建CNAME的OSSClient,如果直接用oss的外网endpoint则跳过
        // oss1.logvv.net对应 sz-1.oss-cn-shenzhen.aliyuncs.com
    	
        // 使用CNAME时，无法使用ListBuckets接口。 
    	 ClientConfiguration conf = new ClientConfiguration();
         conf.setSupportCname(true);
         conf.setMaxConnections(100);
         conf.setConnectionTimeout(5000);
         conf.setMaxErrorRetry(3);
      	 conf.setSocketTimeout(2000);

         // 生成OSSClient
         OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret,conf);
         //OSSClient ossClient = new OSSClient(endpoint2, accessKeyId, accessKeySecret);
         
         return ossClient;
    }
    
    /**
     * createBucket
     * 创建一个 bucket <br/>
     * @param bucket
     * @return void  返回类型 
     * @author wangwei
     * @date 2016年5月10日 上午1:35:30 
     * @version  [1.0, 2016年5月10日]
     * @since  version 1.0
     */
    public void createBucketIfNotExist(String bucket,OSSClient ossClient)
    {
        if (ossClient.doesBucketExist(bucketName)) {
            System.out.println("已经创建Bucket：" + bucketName);
        } else {
            System.out.println("Bucket不存在，准备创建Bucket：" + bucketName + "。");
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            ossClient.createBucket(bucketName);
        }
        ossClient.shutdown();
    }
    
    /**
     * viewBucket
     * 查看bucket信息 <br/>
     * @param bucket
     * @return void  返回类型 
     * @author wangwei
     * @date 2016年5月10日 上午1:39:25 
     * @version  [1.0, 2016年5月10日]
     * @since  version 1.0
     */
    public void viewBucket(String bucket,OSSClient ossClient)
    {
    	if(ossClient.doesBucketExist(bucket))
    	{
    		System.out.println(JsonUtils.obj2json(ossClient.getBucketInfo(bucket)));
    	}
    	ossClient.shutdown();
    }
    
    /**
     * storeObject
     * 存储一个对象<br/>
     * @return void  返回类型 
     * @author wangwei
     * @date 2016年5月10日 上午1:41:46 
     * @version  [1.0, 2016年5月10日]
     * @since  version 1.0
     */
    public void storeStr(OSSClient ossClient,String content,String objkey)
    {
        InputStream is = new ByteArrayInputStream(content.getBytes());
        ossClient.putObject(bucketName, objkey, is);
        System.out.println("Object：" + objkey + "存入OSS成功。");
        
        ossClient.shutdown();
    }
    
    public String storeFile(OSSClient ossClient,String path)
    {
        String fileKey = RandomStringUtils.randomAlphanumeric(6)+path.substring(path.lastIndexOf("."));
        ossClient.putObject(bucketName, fileKey, new File(path));
        System.out.println("Object：" + fileKey + "存入OSS成功。");
        
        ossClient.shutdown();
        
        return fileKey; 
    }
    
    /**
     * ListObjectInBucket
     * 这里用一句话描述这个方法的作用<br/>
     * @param ossClient
     * @return void  返回类型 
     * @author wangwei
     * @date 2016年5月10日 上午1:53:14 
     * @version  [1.0, 2016年5月10日]
     * @since  version 1.0
     */
    public void ListObjectInBucket(OSSClient ossClient,String bucket)
    {
        ObjectListing objectListing = ossClient.listObjects(bucket);
        List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
        System.out.println("list Object：");
        for (OSSObjectSummary object : objectSummary) {
            System.out.println("\t" + object.getKey());
        }
        
        ossClient.shutdown();
    }
    
    /**
     * downloadObject
     * 下载一个对象 <br/>
     * @param ossClient
     * @param bucket
     * @param objKey
     * @return void  返回类型 
     * @author wangwei
     * @date 2016年5月10日 上午1:46:37 
     * @version  [1.0, 2016年5月10日]
     * @since  version 1.0
     */
    public void downloadObject(OSSClient ossClient,String bucket,String objKey)
    {
        OSSObject ossObject = ossClient.getObject(bucket, objKey);
        
        InputStream inputStream = ossObject.getObjectContent();
        StringBuilder objectContent = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
            if (line == null)
                break;
            objectContent.append(line);
        }
        try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Object：" + objKey + "contet：" + objectContent);
        
        ossClient.shutdown();
    }
    
    /**
     * deleteObjectInBucket
     * 删bucket里的对象 <br/>
     * @param ossClient
     * @param bucket
     * @param key
     * @return
     * @return boolean  返回类型 
     * @author wangwei
     * @date 2016年5月10日 上午1:59:38 
     * @version  [1.0, 2016年5月10日]
     * @since  version 1.0
     */
    public boolean deleteObjectInBucket(OSSClient ossClient,String bucket, String key)
    {
    	if(ossClient.doesObjectExist(bucket, key))
    	{
    		LOGGER.info("Object exists, begin delete...");
    		ossClient.deleteObject(bucket, key);
    		System.out.println("Delete：" + key + "done.");
    	}
    	else {
			LOGGER.info("Object not exists.");
		}
        ossClient.shutdown();
        
        return ossClient.doesObjectExist(bucket, key);
    }

    public static void main(String[] args) {

        LOGGER.info("Begin to connect to oss server...");

        // 创建CNAME的OSSClient,如果直接用oss的外网endpoint则跳过
        // oss1.logvv.net对应 sz-1.oss-cn-shenzhen.aliyuncs.com
        // 使用CNAME时，无法使用ListBuckets接口。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setSupportCname(true);
        conf.setMaxConnections(100);
        conf.setConnectionTimeout(5000);
        conf.setMaxErrorRetry(3);
     	conf.setSocketTimeout(2000);
     	
     	String endpoint = "oss1.logvv.net";
     	String accessKeyId = "ESN4VOjbRRWnzevU";
     	String accessKeySecret="9anCEoZlPUEy2tqITMuDiKyVK44d5i";
     	String bucketName ="sz-1";
     	
        // 生成OSSClient
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret,conf);
        //OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret0)

        try 
        {
            // 判断Bucket是否存在
            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，准备创建Bucket：" + bucketName + "。");
                // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
                ossClient.createBucket(bucketName);
            }

            // 查看Bucket信息
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
//            BucketInfo info = ossClient.getBucketInfo(bucketName);
//            System.out.println("Bucket " + bucketName + "的信息如下：");
//            System.out.println("\t数据中心：" + info.getBucket().getLocation());
//            System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
//            System.out.println("\t用户标志：" + info.getBucket().getOwner());
//
//            // 把字符串存入OSS，Object的名称为objKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
//            InputStream is = new ByteArrayInputStream("Hello OSS".getBytes());
//            ossClient.putObject(bucketName, "obj-001", is);
//            System.out.println("Object：" + "obj-001" + "存入OSS成功。");
//
//            // 下载文件。详细请参看“SDK手册 > Java-SDK > 下载文件”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/download_object.html?spm=5176.docoss/sdk/java-sdk/manage_object
//            OSSObject ossObject = ossClient.getObject(bucketName, objKey);
//            InputStream inputStream = ossObject.getObjectContent();
//            StringBuilder objectContent = new StringBuilder();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            while (true) {
//                String line = reader.readLine();
//                if (line == null)
//                    break;
//                objectContent.append(line);
//            }
//            inputStream.close();
//            System.out.println("Object：" + objKey + "的内容是：" + objectContent);
//
//            // 文件存储入OSS，Object的名称为fileKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
//            String fileKey = "README.md";
//            ossClient.putObject(bucketName, fileKey, new File("README.md"));
//            System.out.println("Object：" + fileKey + "存入OSS成功。");
//
//            // 查看Bucket中的Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
//            ObjectListing objectListing = ossClient.listObjects(bucketName);
//            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
//            System.out.println("您有以下Object：");
//            for (OSSObjectSummary object : objectSummary) {
//                System.out.println("\t" + object.getKey());
//            }
//
//            // 删除Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
//            ossClient.deleteObject(bucketName, objKey);
//            System.out.println("删除Object：" + objKey + "成功。");
//            ossClient.deleteObject(bucketName, fileKey);
//            System.out.println("删除Object：" + fileKey + "成功。");

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        LOGGER.info("Completed");
    }
}
