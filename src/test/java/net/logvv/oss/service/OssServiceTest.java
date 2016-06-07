package net.logvv.oss.service;

import net.logvv.oss.AbstractTestBase;
import net.logvv.oss.commom.utils.JsonUtils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.oss.OSSClient;

public class OssServiceTest extends AbstractTestBase {

	@Autowired
	private OssService demo;
	@Test
	public void testInitOSSClient() {
		OSSClient client = demo.initOSSClient();
		System.out.println(JsonUtils.obj2json(client));
	}

	@Test
	public void testCreateBucketIfNotExist() {
		OSSClient client = demo.initOSSClient();
		
		String bucket = "bucket-001";
		
		demo.createBucketIfNotExist(bucket, client);
	}

	@Test
	public void testViewBucket() {
		OSSClient client = demo.initOSSClient();
		String bucket = "sz-1";//"bucket-001"; //
		
		demo.viewBucket(bucket, client);
	}
	
	@Test
	public void testListBucket() {
		OSSClient client = demo.initOSSClient();
		
		demo.listBuckets(client);
	}

	@Test
	public void testStoreStr() {
		OSSClient client = demo.initOSSClient();
		String content = "Hello OSS, Hi 30days-tech";
		String objkey = "30days";
		
		demo.storeStr(client, content, objkey);
	}

	@Test
	public void testStoreFile() {
		String path = "E:\\_93wei\\svn\\project\\oss\\logs\\oss.log";
		
		OSSClient client = demo.initOSSClient();
		demo.storeFile(client, path);
	}

	@Test
	public void testListObjectInBucket() {
		String bucket = "sz-1";
		OSSClient client = demo.initOSSClient();
		
		demo.ListObjectInBucket(client, bucket);
	}

	@Test
	public void testDownloadObject() {
		OSSClient client = demo.initOSSClient();
		String bucket = "sz-1";
		String objKey = "ferri_458.png";
		
		demo.downloadObject(client, bucket, objKey);
	}

	@Test
	public void testDeleteObjectInBucket() {
		OSSClient client = demo.initOSSClient();
		String bucket = "sz-1";
		String key = "TM99Wt.log";
		demo.deleteObjectInBucket(client, bucket, key);
	}

}
