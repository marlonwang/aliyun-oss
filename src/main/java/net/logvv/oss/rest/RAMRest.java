package net.logvv.oss.rest;

import net.logvv.oss.service.RAMServce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author : wangwei
 * Created on 2016/05/30 23:47.
 */
@RestController
public class RAMRest {

    @Autowired
    private RAMServce ramServce;

    @RequestMapping(value = "/v1/ram/requestid",method = RequestMethod.GET)
    public Object applyRAMRequestId(@RequestParam(value = "format",required = false,defaultValue = "xml")String format)
    {

        return "";
    }

}
