package net.logvv.oss.rest;

import net.logvv.oss.commom.GeneralResult;
import net.logvv.oss.service.RAMService;

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
    private RAMService ramService;

    @RequestMapping(value = "/v1/ram/requestid",method = RequestMethod.GET)
    public GeneralResult createRamUser(@RequestParam(value = "format",required = false,defaultValue = "JSON")String format,
    		@RequestParam("userName")String userName)
    {
    	GeneralResult result = new GeneralResult();
        ramService.applyRequestId(format,"chipwell");
        
        return result;
    }

}
