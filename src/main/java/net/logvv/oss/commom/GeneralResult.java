package net.logvv.oss.commom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralResult {
	// 结果状态, true 成功 false 失败
	private boolean resultStatus;

	// 如果错误则返回 错误代码
	private String errorCode;

	// 错误详情描述
	private String errorMessage;

	// 结果为true时的 业务数据，可为空
	private Object resultData;

	public boolean isResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(boolean resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMsg) {
		this.errorMessage = errorMsg;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

	public void setError(ErrorCode errorCode) {

		this.errorCode = errorCode.getCode();
		this.errorMessage = errorCode.getMessage();
	}

}
