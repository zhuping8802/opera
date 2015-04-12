package org.ping.solr.util;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.ping.solr.bean.DocBean;

public class CloudSolrServerTest {

	public static void main(String[] args) {
		CommonQueryUtil query = new CommonQueryUtil();
		query.setZkHost("182.92.234.132:2781,182.92.234.132:2782,182.92.234.132:2783,182.92.234.132:2784");
		query.setDefaultCollection("eunke_vehicles");
		query.init();
		try {
			SolrQuery solrQuery = new SolrQuery("*:*"); 
			List<DocBean> list = query.queryBeans(solrQuery, DocBean.class);
			for(DocBean bean : list){
				System.out.println(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			query.destory();
		}
	}
}
