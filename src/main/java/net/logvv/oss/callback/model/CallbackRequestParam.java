package net.logvv.oss.callback.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackRequestParam {
	private String callbackUrl;
	private String callbackHost;
	private String callbacBody;
	private String callbackBodyType;
	
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getCallbackHost() {
		return callbackHost;
	}
	public void setCallbackHost(String callbackHost) {
		this.callbackHost = callbackHost;
	}
	public String getCallbacBody() {
		return callbacBody;
	}
	public void setCallbacBody(String callbacBody) {
		this.callbacBody = callbacBody;
	}
	public String getCallbackBodyType() {
		return callbackBodyType;
	}
	public void setCallbackBodyType(String callbackBodyType) {
		this.callbackBodyType = callbackBodyType;
	}
	
}
