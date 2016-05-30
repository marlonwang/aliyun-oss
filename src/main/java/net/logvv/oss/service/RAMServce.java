package net.logvv.oss.service;

import net.logvv.oss.commom.utils.DateUtils;
import net.logvv.oss.commom.utils.RestServiceUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Author : wangwei
 * Created on 2016/05/30 23:46.
 */

@Service
public class RAMServce {

    private static final Logger LOGGER = LoggerFactory.getLogger(RAMServce.class);

    @Value("${ram.access.url}")
    public String ramAccessUrl;

    public Object applyRequestId(String format)
    {
        /**
         https://ram.aliyuncs.com/
         ?Format=xml
         &Version=2015-05-01
         &Signature=Pc5WB8gokVn0xfeu%2FZV%2BiNM1dgI%3D
         &SignatureMethod=HMAC-SHA1
         &SignatureNonce=15215528852396
         &SignatureVersion=1.0
         &AccessKeyId=key-test
         &Timestamp=2012-06-01T12:00:00Z
         */
        Object obj = null;

        String sign = "";
        String fullReqParam = ramAccessUrl + "Format=" + format +
                "&Version=2015-05-01" +
                "&SignatureMethod=HMAC-SHA1" +
                "&SignatureVersion=1.0" +
                "&SignatureNonce="+ RandomStringUtils.randomNumeric(8) +
                "&Timestamp="+ DateUtils.date2Str(DateTime.now(),"yyyy-MM-ddTHH:mm:ssZ") +
                "&AccessKeyId=" + "" +
                " &Signature=" + sign;


        try {
            obj = RestServiceUtils.doGet(fullReqParam, Object.class);

        } catch (Exception e) {
            LOGGER.info("reques to aliyun error:{}",e);
            return null;
        }
        return obj;
    }

}
