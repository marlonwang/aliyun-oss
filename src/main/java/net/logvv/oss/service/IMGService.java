package net.logvv.oss.service;

import net.logvv.oss.commom.exception.RestInvocationException;
import net.logvv.oss.commom.utils.RestServiceUtils;
import net.logvv.oss.model.BaseImgInfo;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IMGService {
	// 单个图片最大20M
	// channel 对应 bucket, 且对应名称保持一致
	// style 统一channel下的对图片的同种处理方式
	// 支持格式 jpg,png,bmp,webp,gif; 文件大小20M; 缩略图大小 不超过 4096*4096, 单边不超过 4096*4, 默认jpg
	// http://image-demo.img-cn-hangzhou.aliyuncs.com/example.jpg@100w_100h.jpg
	
	@Value("${img.channel}")
	private String imgChannel;
	
	@Value("${img.channel.alias}")
	private String imgChannelAlias;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IMGService.class);
	
	@Autowired
	private OssService ossService;
	
	/**
	 * uploadImage
	 * 上传图片 返回图片的访问连接 <br/>
	 * @param filepath
	 * @return
	 * @return String  返回类型 
	 * @author wangwei
	 * @date 2016年6月13日 下午4:24:46 
	 * @version  [1.0, 2016年6月13日]
	 * @since  version 1.0
	 */
	public String uploadImage(String filepath)
	{
		String fkey = RandomStringUtils.randomAlphanumeric(6)+filepath.substring(filepath.lastIndexOf("."));
		
		// 上传图片到sz-1
		String etag = ossService.storeImage(fkey, filepath);
		LOGGER.info("upload image to bucket sz-1, object key:{}, etag:{}",fkey,etag);
		
		return imgChannel + "/" +fkey;
	}
	
	public BaseImgInfo getImgInfo(String key) throws RestInvocationException
	{
		BaseImgInfo info = RestServiceUtils.doGet(imgChannel+"/"+key+"@info", BaseImgInfo.class);
		return info;
	}
	
	/**
	 * getImageACL
	 * 带认证的图片获取 <br/>
	 * @param key
	 * @return
	 * @return String  返回类型 
	 * @author wangwei
	 * @date 2016年6月13日 下午8:50:17 
	 * @version  [1.0, 2016年6月13日]
	 * @since  version 1.0
	 */
	public String getImageACL(String key)
	{
		
		return "";
	}
}
