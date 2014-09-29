package org.ping.study.spring.batch;

import java.util.HashMap;
import java.util.Map;

/**
 * 批量请求对象
 * @author ping.zhu
 *
 */
public class AjaxBatch {
	
	/**
	 * 请求地址
	 */
	private String reqUrl = null;
	
	/**
	 * 请求结果返回后可执行回调函数
	 */
	private String callback = null;
	
	/**
	 * 请求参数
	 */
	private Map<String, String> params = new HashMap<String, String>();
	
	/**
	 * 请求类型
	 */
	private String reqType = "get";
	
	/**
	 * 结果集
	 */
	private Object result;

	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Object getResult() {
		return result;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
