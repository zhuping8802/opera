package org.ping.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ping.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/ajaxBatch")
public class AjaxBatchAction {
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 处理批量请求
	 * @return
	 */
	@RequestMapping("doBatch")
	@ResponseBody
	public List<AjaxBatch> doBatch(@RequestParam String ajaxBatches) throws Exception{
		List<AjaxBatch> batches = this.getAjaxBatchList(ajaxBatches);
		CountDownLatch latch = new CountDownLatch(batches.size());
		for(AjaxBatch batch: batches){
			AjaxBatchThread thread = new AjaxBatchThread(batch, latch, restTemplate);
			ThreadUtil.putThreadPool(thread);
		}
		latch.await();
		return batches;
	}
	
	/**
	 * 解析批量请求参数
	 * @param ajaxBatches
	 * @return
	 * @throws JSONException 
	 */
	private List<AjaxBatch> getAjaxBatchList(String ajaxBatches) throws JSONException{
		List<AjaxBatch> batches = new ArrayList<AjaxBatch>();
		JSONArray jsonArray = new JSONArray(ajaxBatches);
		if(jsonArray != null){
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				AjaxBatch ajaxBatch = new AjaxBatch();
				ajaxBatch.setReqUrl(jsonObject.getString("reqUrl"));
				ajaxBatch.setCallback(jsonObject.getString("callback"));
				ajaxBatch.setReqType(jsonObject.getString("reqType"));
				
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

class AjaxBatchThread extends Thread{
	// get请求
	private static final String REQUEST_TYPE_GET = "get";
	// post请求
	private static final String REQUEST_TYPE_POST = "post";
	private RestTemplate restTemplate;
	private AjaxBatch ajaxBatch;
	private CountDownLatch latch;
	
	public AjaxBatchThread(){
		
	}
	
	public AjaxBatchThread(AjaxBatch ajaxBatch, CountDownLatch latch, RestTemplate restTemplate){
		this.ajaxBatch = ajaxBatch;
		this.latch = latch;
		this.restTemplate = restTemplate;
	}

	@Override
	public void run() {
		try {
			String reqUrl = ajaxBatch.getReqUrl();
			if(REQUEST_TYPE_GET.equalsIgnoreCase(ajaxBatch.getReqType())){
				String result = restTemplate.getForObject(reqUrl, String.class, ajaxBatch.getParams());
				ajaxBatch.setResult(result);
			}else if(REQUEST_TYPE_POST.equalsIgnoreCase(ajaxBatch.getReqType())){
				String result = restTemplate.postForObject(reqUrl, null, String.class, ajaxBatch.getParams());
				ajaxBatch.setResult(result);
			}
		} catch (RestClientException e) {
			ajaxBatch.setResult(null);
		}finally{
			latch.countDown();
		}
	}
}
