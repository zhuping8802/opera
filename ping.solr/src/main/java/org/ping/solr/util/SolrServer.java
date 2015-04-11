package org.ping.solr.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrServer {

	private static final Logger logger = LoggerFactory.getLogger(SolrServer.class);

	protected SolrClient solrClient = null;
	protected int queeSize = QUEE_SIZE;
	protected int threadCount = THREAD_COUNT;
	protected int zkClientTimeout = ZKCLIENTTIMEOUT;
	protected int zkConnectTimeout = ZKCONNECTTIMEOUT;
	protected String zkHost;
	protected String solrUrl;
	protected String defaultCollection;
	/**
	 * solr服务类型  1 CloudServer 2 CurrentServer
	 */
	protected int type = 1;
	private static final int QUEE_SIZE = Integer.MAX_VALUE;
	private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2;
	private static final int ZKCLIENTTIMEOUT = 20000;
	private static final int ZKCONNECTTIMEOUT = 1000; 

	private void initCurrentSolrServer() {
		if (StringUtils.isBlank(solrUrl)) {
			throw new RuntimeException("initCurrentSolrServer error, url is not null.");
		}
		
		if (solrClient == null) {
			try {
				ConcurrentUpdateSolrClient concurrentUpdateSolrClient = new ConcurrentUpdateSolrClient(solrUrl, queeSize <= 0 ? QUEE_SIZE : queeSize,
						threadCount <= 0 ? THREAD_COUNT : threadCount);
				solrClient = concurrentUpdateSolrClient;
			} catch (Exception e) {
				logger.error("initCurrentSolrServer error", e);
			}
			logger.info("initCurrentSolrServer success");
		}
	}
	
	private void initColudSolrServer() {
		if (StringUtils.isBlank(zkHost)) {
			throw new RuntimeException("initColudSolrServer error, zkHost is not null.");
		}
		
		if(StringUtils.isBlank(defaultCollection)){
			throw new RuntimeException("default collection is not null.");
		}
		
		if(solrClient == null){
			try {
				CloudSolrClient cloudSolrClient = new CloudSolrClient(zkHost);
				cloudSolrClient.setZkClientTimeout(zkClientTimeout <= 0 ? ZKCLIENTTIMEOUT : zkClientTimeout); 
				cloudSolrClient.setZkConnectTimeout(zkConnectTimeout <= 0 ? ZKCONNECTTIMEOUT : zkConnectTimeout); 
				cloudSolrClient.setDefaultCollection(defaultCollection);
				cloudSolrClient.connect();
				solrClient = cloudSolrClient;
			} catch (Exception e) {
				logger.error("initColudSolrServer error", e);
			}
			logger.info("initColudSolrServer success");
		}
	}
	
	protected void init(){
		if(type == 1){
			initColudSolrServer();
		} else if(type == 2){
			initCurrentSolrServer();
		} else {
			initColudSolrServer();
		}
	}

	protected void destory() {
		if (solrClient != null) {
			solrClient.shutdown();
			solrClient = null;
		}
		logger.info("SolrServer closed");
	}

	public void setQueeSize(int queeSize) {
		this.queeSize = queeSize;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public void setZkClientTimeout(int zkClientTimeout) {
		this.zkClientTimeout = zkClientTimeout;
	}

	public void setZkConnectTimeout(int zkConnectTimeout) {
		this.zkConnectTimeout = zkConnectTimeout;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setZkHost(String zkHost) {
		this.zkHost = zkHost;
	}

	public void setSolrUrl(String solrUrl) {
		this.solrUrl = solrUrl;
	}

	public void setDefaultCollection(String defaultCollection) {
		this.defaultCollection = defaultCollection;
	}
	
}
