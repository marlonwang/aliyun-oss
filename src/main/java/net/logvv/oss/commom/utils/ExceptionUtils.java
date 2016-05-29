package net.logvv.oss.commom.utils;


import net.logvv.oss.commom.exception.ExceptionLevel;
import net.logvv.oss.commom.exception.ExceptionMessage;
import net.logvv.oss.commom.exception.RestInvocationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 
 * 异常信息管理服务类<br>
 * 
 * @author chenxiaojin
 * @data 2014年9月26日
 * @version 1.0.0
 *
 */
@Service
public class ExceptionUtils
{
	
	/** 日志记录器 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtils.class);
	
	/** 网页换行 */
	private static final String newLine = "</br>";
	
	/** html字体颜色模板 */
	private static final String colorTemplate = "%s%s%s";
	
	private static final String defaultExceptionServiceUrl = "http://www.jiasuqi.net:28030/platform/manager/exception";
	
	public ExceptionUtils()
	{
		
	}
	
	private static String bold(String str)
	{
		return String.format(colorTemplate, "<b>", str, "</b>");
	}
	
	/**
	 * 
	 * 生成异常信息实体类<br>
	 * @param phoneNumber 请求用户手机号码
	 * @param productCode 产品Code
	 * @param versionName 产品版本名称
	 * @param clientType 客户端类型
	 * @param exceptionLevel 异常级别
	 * @param operationDesc 出现异常的操作描述
	 * @param exceptionMsg 异常详细信息
	 * @return
	 * ExceptionMessage
	 * @Author chenxiaojin
	 * @data 2014年10月17日
	 * @exception 
	 * @version
	 *
	 */
	public static ExceptionMessage gernelExceptionMsg(String phoneNumber, String productCode, String versionName, 
			String clientType, ExceptionLevel exceptionLevel, String operationDesc, String exceptionMsg)
	{
		ExceptionMessage exceptionMsgObj = new ExceptionMessage();
		exceptionMsgObj.setPhoneNumber(null == phoneNumber ? "NONE" : phoneNumber);
		exceptionMsgObj.setExceptionLevel(null == exceptionLevel ? ExceptionLevel.CRITICAL : exceptionLevel);
		exceptionMsgObj.setExceptionMessage(null == exceptionMsg ? "Unkown Exception" : exceptionMsg);
		exceptionMsgObj.setProductCode(null == productCode ? "" : productCode);
		exceptionMsgObj.setClientType(null == clientType ? "SERVER" : clientType);
		exceptionMsgObj.setUserOperationDesc(null == operationDesc ? "" : operationDesc);
		exceptionMsgObj.setVersionName(null == versionName ? "" : versionName);
		return exceptionMsgObj;
	}

	/**
	 * 
	 * 获取异常堆栈异常信息<br>
	 *
	 * @param e
	 * @return
	 * String
	 * @Author chenxiaojin
	 * @data 2014年10月17日
	 * @exception 
	 * @version
	 *
	 */
	public static String getStackTraceMsg(Throwable e)
	{
		if (null == e)
		{
			return "";
		}
		
		StackTraceElement[] messages = e.getStackTrace();
		StringBuilder exceptionMsg = new StringBuilder(2000);
		exceptionMsg.append(bold(e.toString())).append(newLine);
		int length = messages.length;
		exceptionMsg.append("<font color=\"red\">");
		for (int i = 0; i < length; i++)
		{
			exceptionMsg.append("at " + messages[i].toString()).append(newLine);
		}
		exceptionMsg.append("</font>");
		return exceptionMsg.toString();
	}

	/**
	 * 
	 * 发送异常信息邮件<br>
	 *
	 * @param exceptionMsg
	 * @return
	 * boolean
	 * @Author chenxiaojin
	 * @data 2014年10月16日
	 * @exception 
	 * @version
	 *
	 */
	public static void sendException(ExceptionMessage exceptionMsg)
	{
		// 异常rest服务地址
		try
		{
			RestServiceUtils.doPut(defaultExceptionServiceUrl, exceptionMsg);
		}
		catch (RestInvocationException e)
		{
			LOGGER.error("Send exception message email failed in user module. Error message : {}", e);
		}
	}

	/**
	 * 
	 * 发送异常信息邮件<br>
	 *
	 * @param exceptionMsg
	 * @return
	 * boolean
	 * @Author chenxiaojin
	 * @data 2014年10月16日
	 * @exception 
	 * @version
	 *@deprecated
	 */
	public static void sendExceptionByUrl(String exceptionUrl,ExceptionMessage exceptionMsg)
	{
		if(StringUtils.isEmpty(exceptionUrl))
		{
			exceptionUrl = defaultExceptionServiceUrl;
		}
		
		// 异常rest服务地址
		try
		{
			RestServiceUtils.doPut(exceptionUrl, exceptionMsg);
		}
		catch (RestInvocationException e)
		{
			LOGGER.error("Send exception message email failed in user module. Error message : {}", e);
		}
	}
	
}
