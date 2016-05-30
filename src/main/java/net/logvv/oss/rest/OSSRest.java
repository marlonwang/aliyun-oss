package net.logvv.oss.rest;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.PutObjectRequest;
import net.logvv.oss.commom.utils.JsonFileUtils;
import net.logvv.oss.commom.utils.JsonUtils;
import net.logvv.oss.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * Created by marlon on 2016/5/27.
 */
@RestController
public class OSSRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OSSRest.class);

    @Autowired
    private OssService ossService;

    @RequestMapping(value ="/v1/object/default",method = RequestMethod.GET)
    public String putObjectWithCallBack()
    {
        LOGGER.info("begin to put object...");

        OSSClient client = ossService.initOSSClient();
        String filepath = "/opt/app/oss/res/image/jKhna.jpeg";

        //String callbackBody = "{\"bucket\":${bucket},\"object\":${object},\"mimeType\":${mimeType},\"size\":${size}}";
        String callbackBody = "bucket=${bucket}&object=${object}&etag=${etag}&size=${size}&mimeType=${mimeType}";
        Callback callback = new Callback();
        //callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
        callback.setCalbackBodyType(Callback.CalbackBodyType.URL);
        callback.setCallbackUrl("www.logvv.net:31101/v1/callback");
        callback.setCallbackBody(callbackBody);

        PutObjectRequest request = new PutObjectRequest(ossService.bucketName, ossService.bucketName+".jpeg", new File(filepath));
        request.setCallback(callback);

        System.out.println(JsonUtils.obj2json(request));
        try
        {
            client.putObject(request);
        }
        catch (Exception e){
            LOGGER.info("error:{}",e);
            return "error";
        }

        return "done.";
    }

    @RequestMapping(value = "/test/v1/echo", method = RequestMethod.GET)
    public String testOut()
    {
        return "got it";
    }


    @RequestMapping(value = "/test/v1/debug-json", method = RequestMethod.GET)
    public Object readjson()
    {
        // json file path
        String filePath = System.getProperties().getProperty("user.dir")
                +File.separator
                +"debug-json"
                +File.separator
                +"debug-json.json";

        LOGGER.info("target path:{}",filePath);

        File file = new File(filePath);
        if(null == file || !file.exists()){
            System.out.print("No file exist.");

            return "No file exist.";
        }
        //System.out.print(Thread.currentThread().getContextClassLoader().getResource(""));
        //file:/E:/_93wei/svn/project/oss/target/classes/

        LOGGER.info("method:{}",Thread.currentThread().getStackTrace()[1].getMethodName());

//        System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());//invoke0
//        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());//readjson
//        System.out.println(Thread.currentThread().getStackTrace()[0].getMethodName());//getStackTrace

        return JsonFileUtils.readPathJson(filePath);

    }
}
