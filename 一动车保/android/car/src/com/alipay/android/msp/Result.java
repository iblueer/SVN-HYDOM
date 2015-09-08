package com.alipay.android.msp;public class Result {	public String resultStatus;	String result;	String memo;//	sResultStatus.put("9000", "支付成功");//	sResultStatus.put("4000", "系统异常");//	sResultStatus.put("4001", "订单参数错误");//	sResultStatus.put("6001", "用户取消支付");//	sResultStatus.put("6002", "网络连接异常");	public String getResultStatus() {		return resultStatus;	}	public void setResultStatus(String resultStatus) {		this.resultStatus = resultStatus;	}	public Result(String rawResult) {		try {			String[] resultParams = rawResult.split(";");			for (String resultParam : resultParams) {				if (resultParam.startsWith("resultStatus")) {					resultStatus = gatValue(resultParam, "resultStatus");				}				if (resultParam.startsWith("result")) {					result = gatValue(resultParam, "result");				}				if (resultParam.startsWith("memo")) {					memo = gatValue(resultParam, "memo");				}			}		} catch (Exception e) {			e.printStackTrace();		}	}	@Override	public String toString() {		return "resultStatus={" + resultStatus + "};memo={" + memo				+ "};result={" + result + "}";	}	private String gatValue(String content, String key) {		String prefix = key + "={";		return content.substring(content.indexOf(prefix) + prefix.length(),				content.lastIndexOf("}"));	}}