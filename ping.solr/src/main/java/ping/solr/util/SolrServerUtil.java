package ping.solr.util;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrServerUtil {

	private static final Logger logger = LoggerFactory.getLogger(SolrServerUtil.class);

	protected SolrServer solrServer = null;
	private static final int QUEE_SIZE = Integer.MAX_VALUE;
	private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2;
	private static final int ZKCLIENTTIMEOUT = 20000;
	private static final int ZKCONNECTTIMEOUT = 1000; 

	protected void initCurrentSolrServer(final String url, final int queeSize, final int threadCount) throws Exception {
		if (StringUtils.isBlank(url)) {
			throw new Exception("initCurrentSolrServer error, url is not null.");
		}
		
		if (solrServer == null) {
			try {
				solrServer = new ConcurrentUpdateSolrServer(url, queeSize <= 0 ? QUEE_SIZE : queeSize,
						threadCount <= 0 ? THREAD_COUNT : threadCount);
			} catch (Exception e) {
				logger.error("initCurrentSolrServer error", e);
			}
			logger.info("initCurrentSolrServer success");
		}
	}
	
	protected void initColudSolrServer(final String zkHost, final String defaultCollection, int clientTimeOut, final int connectTimeOut) throws Exception{
		if (StringUtils.isBlank(zkHost)) {
			throw new Exception("initColudSolrServer error, zkHost is not null.");
		}
		
		if(StringUtils.isBlank(defaultCollection)){
			throw new Exception("default collection is not null.");
		}
		if(solrServer == null){
			try {
				CloudSolrServer cloudSolrServer = new CloudSolrServer(zkHost);
				cloudSolrServer.setZkClientTimeout(clientTimeOut <= 0 ? ZKCLIENTTIMEOUT : clientTimeOut); 
				cloudSolrServer.setZkConnectTimeout(connectTimeOut <= 0 ? ZKCONNECTTIMEOUT : connectTimeOut); 
				cloudSolrServer.setDefaultCollection(defaultCollection);
				cloudSolrServer.connect();
				solrServer = cloudSolrServer;
			} catch (Exception e) {
				logger.error("initColudSolrServer error", e);
			}
			logger.info("initColudSolrServer success");
		}
	}

	protected void destory() {
		if (solrServer != null) {
			solrServer.shutdown();
			solrServer = null;
		}
		logger.info("SolrServer closed");
	}
}
