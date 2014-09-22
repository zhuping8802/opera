package org.ping.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/ajaxBatch")
public class AjaxBatchAction {
	
	// get请求
	private static final String REQUEST_TYPE_GET = "get";
	// post请求
	private static final String REQUEST_TYPE_POST = "post";
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 处理批量请求
	 * @return
	 */
	@RequestMapping("doBatch")
	@ResponseBody
	public List<AjaxBatch> doBatch(@RequestParam String ajaxBatches) throws JSONException{
		List<AjaxBatch> batches = this.getAjaxBatchList(ajaxBatches);
		for(AjaxBatch batch: batches){
			String reqUrl = batch.getReqUrl();
			if(REQUEST_TYPE_GET.equalsIgnoreCase(batch.getReqType())){
				String result = restTemplate.getForObject(reqUrl, String.class, batch.getParams());
				batch.setResult(result);
			}else if(REQUEST_TYPE_POST.equalsIgnoreCase(batch.getReqType())){
				String result = restTemplate.postForObject(reqUrl, null, String.class, batch.getParams());
				batch.setResult(result);
			}
		}
		return batches;
	}
	
	/**
	 * 解析批量请求参数
	 * @param ajaxBatches
	 * @return
	 */
	private List<AjaxBatch> getAjaxBatchList(String ajaxBatches){
		List<AjaxBatch> batches = new ArrayList<AjaxBatch>();
		JSONArray jsonArray = new JSONArray(ajaxBatches);
		if(jsonArray != null){
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				AjaxBatch ajaxBatch = new AjaxBatch();
				ajaxBatch.setReqUrl(jsonObject.getString("reqUrl"));
				ajaxBatch.setCallback(jsonObject.getString("callback"));
				
				JSONObject paramsObject = jsonObject.getJSONObject("params");
				if(paramsObject != null){
					Map<String, String> params = new HashMap<String, String>();
					@SuppressWarnings("unchecked")
					Iterator<String> keys = paramsObject.keys();
					while(keys.hasNext()){
						params.put(keys.next(), paramsObject.getString(keys.next()));
					}
					ajaxBatch.setParams(params);
				}
				
				batches.add(ajaxBatch);
			}
		}
		return batches;
	}
}
