package ping.solr.util;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrServerUtil {

	private static final Logger logger = LoggerFactory.getLogger(SolrServerUtil.class);

	protected ConcurrentUpdateSolrServer solrServer = null;
	private static final int QUEE_SIZE = Integer.MAX_VALUE;
	private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2;

	protected void init(final String url, final int queeSize, final int threadCount) throws Exception {
		if (StringUtils.isBlank(url)) {
			throw new Exception("SolrServer init error, url is not null.");
		}
		
		if (solrServer == null) {
			try {
				solrServer = new ConcurrentUpdateSolrServer(url, queeSize <= 0 ? QUEE_SIZE : queeSize,
						threadCount <= 0 ? THREAD_COUNT : threadCount);
			} catch (Exception e) {
				logger.error("init solrServer error", e);
			}
		}
	}

	protected void destory() {
		if (solrServer != null) {
			solrServer.shutdown();
			solrServer = null;
		}
	}
}
