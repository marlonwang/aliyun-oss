package net.logvv.oss.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.language.Soundex;

import java.net.URLEncoder;
import java.util.*;

/**
 * Author: wangwei
 * Created on 2016/5/31.
 */
public class SignatureUtils {

    /**
     * filter empty param and specific key
     * @param sArray
     * @return
     */
    public static Map<String,String> paramFilter(Map<String,String> sArray)
    {
        Map<String,String> result = new HashMap<>();
        if(sArray == null || sArray.size() <= 0)
        {
            return result;
        }
        for(String key:sArray.keySet())
        {
            String value = sArray.get(key);
            if(value == null || value.equals("") || key.equalsIgnoreCase("sign_type")) // || key.equalsIgnoreCase("sign")
            {
                continue;
            }
            result.put(key,value);
        }

        return result;
    }

    /**
     * create linked str of params as key1="value"&key2="value2"
     * @param params
     * @return
     */
    public static String linkParam(Map<String,String> params,boolean encValue)
    {
        // sort key by alpha asc
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        String prestr = "";
        for(int i = 0;i<keys.size();i++)
        {
            String key = keys.get(i);
            String value = params.get(key);
            if(i == keys.size() - 1)
            {
                prestr += key + "=" + "\"" + (encValue ? encodeIgnoreWave(value) : value) + "\"";
            }
            else {
                prestr += key + "=" + "\"" + (encValue ? encodeIgnoreWave(value) : value) + "\"" + "&";
            }
        }
        return prestr;
    }

    /**
     * create linked str of params as key1=value&key2=value2
     * @param params
     * @return
     */
    public static String linkPlainParam(Map<String,String> params,boolean encValue)
    {
        // sort key by alpha asc
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        String prestr = "";
        for(int i = 0;i<keys.size();i++)
        {
            String key = keys.get(i);
            String value = params.get(key);
            if(i == keys.size() - 1)
            {
                prestr += key + "=" + (encValue ? encodeIgnoreWave(value) : value);
            }
            else {
                prestr += key + "=" + (encValue ? encodeIgnoreWave(value) : value) + "&";
            }
        }
        return prestr;
    }

    /**
     * url encode the input string and replace the + * %7E
     * only used in ali RAM sign process
     * default url encode will replace space with + but space refer to %20 in ASCII
     * @param inputStr
     * @return
     */
    public static String encodeIgnoreWave(String inputStr)
    {
        try {
            String tmp = URLEncoder.encode(inputStr,"utf-8");

            //System.out.println("after url encode:"+tmp);

            return tmp.replaceAll("\\+","%20")
                    .replaceAll("\\*","%2A")
                    .replaceAll("(%7E)","~")
                    ;

        }catch (Exception e)
        {
            System.out.println("encode error.");
            return "";
        }
    }

    public static void main(String[] args)
    {
        String input = ":.Alex wang_2016-05-12*sz~";
        System.out.println("input:"+input);
        System.out.println("ouput:"+encodeIgnoreWave(input));

        Map<String,String> map = new HashMap<>();
        map.put("name","alex~vv*");
        map.put("sex","man_");
        map.put("call","010 123456");

        System.out.println(linkParam(map,false));
        System.out.println(linkPlainParam(map,true));

    }
}
