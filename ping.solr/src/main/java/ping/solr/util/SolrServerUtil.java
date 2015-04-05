package ping.solr.util;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrServerUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SolrServerUtil.class);

	protected ConcurrentUpdateSolrServer solrServer = null;
	private static final int queeSize = Integer.MAX_VALUE;
	private static final int threadCount = Runtime.getRuntime()
			.availableProcessors() * 2;
	private String solrServerUrl = "";

	public void setSolrServerUrl(String solrServerUrl) {
		this.solrServerUrl = solrServerUrl;
	}

	public void init() throws Exception {
		if(StringUtils.isBlank(solrServerUrl)){
			throw new Exception("SolrServer init error, solrServerUrl is not null.");
		}
		if (solrServer == null) {
			try {
				solrServer = new ConcurrentUpdateSolrServer(solrServerUrl,
						queeSize, threadCount);
			} catch (Exception e) {
				logger.error("init solrServer error", e);
			}
		}
	}
	
	public void destory(){
		if(solrServer != null){
			solrServer.shutdown();
			solrServer = null;
		}
	}
}
