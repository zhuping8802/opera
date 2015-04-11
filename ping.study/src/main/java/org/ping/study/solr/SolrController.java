package org.ping.study.solr;

import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.ping.solr.bean.DocBean;
import org.ping.solr.util.CommonQueryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/solr")
public class SolrController {
	
	@Resource(name = "solrQuery")
	private CommonQueryUtil solrQuery;
	
	@RequestMapping("find")
	@ResponseBody
	public Object find(){
		List<DocBean> docs = null;
		try {
			docs = solrQuery.queryBeans(new SolrQuery("*:*"), DocBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docs;
	}
}
