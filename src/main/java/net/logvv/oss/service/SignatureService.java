package net.logvv.oss.service;

import java.util.*;

/**
 * Author: wangwei
 * Created on 2016/5/31.
 */
public class SignatureService {

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
            if(value == null || value.equals("")
                    || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type"))
            {
                continue;
            }
            result.put(key,value);
        }

        return result;
    }

    /**
     * create linked str of params as key1=value&key2=value2
     * @param params
     * @return
     */
    public static String linkParam(Map<String,String> params)
    {
        // sort key by alpha asc
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";
        for(int i = 0;i<keys.size();i++)
        {
            String key = keys.get(i);
            String value = params.get(key);
            if(i == keys.size() - 1)
            {
                prestr += key + "=" + "\"" + value + "\"";
            }
            else {
                prestr += key + "=" + "\"" + value + "\"" + "&";
            }
        }
        return prestr;
    }
}
