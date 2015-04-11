package ping.solr.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

public abstract class CommonIndexUtil extends SolrServer {

	/**
	 * Detele lucene by ID
	 * 
	 * @param id
	 * @param commit 是否立即提交
	 */
	public void deleteById(String id, boolean commit) throws Exception {
		solrClient.deleteById(id);
		if(commit){
			commit();
		}
	}

	/**
	 * Detele lucene by ID
	 * 
	 * @param id
	 * @param commitWithinMs
	 *            多少毫秒之内提交
	 */
	public void deleteById(String id, int commitWithinMs) throws Exception {
		solrClient.deleteById(id, commitWithinMs);
	}

	/**
	 * Detele lucene by IDs.
	 * 
	 * @param strings
	 * @param commit 是否立即提交
	 */
	public void deleteById(List<String> strings, boolean commit) throws Exception {
		solrClient.deleteById(strings);
		if(commit){
			commit();
		}
	}

	/**
	 * Detele lucene by IDs.
	 * 
	 * @param strings
	 * @param commitWithinMs
	 *            多少毫秒之内提交
	 */
	public void deleteById(List<String> strings, int commitWithinMs)
			throws Exception {
		solrClient.deleteById(strings, commitWithinMs);
	}

	/**
	 * Detele lucene by query.
	 * 
	 * @param query
	 *            查询条件
	 * @param commit 是否立即提交
	 */
	public void deleteByQuery(String query, boolean commit) throws Exception {
		solrClient.deleteByQuery(query);
		if(commit){
			commit();
		}
	}

	/**
	 * Detele lucene by query.
	 * 
	 * @param query
	 *            查询条件
	 * @param commitWithinMs
	 *            多少毫秒之内提交
	 */
	public void deleteByQuery(String query, int commitWithinMs)
			throws Exception {
		solrClient.deleteByQuery(query, commitWithinMs);
	}

	/**
	 * commit
	 */
	public void commit() throws Exception {
		commit(true, true);
	}

	/**
	 * Commit.
	 * 
	 * @param waitFlush
	 *            block until index changes are flushed to disk
	 * @param waitSearcher
	 *            block until a new searcher is opened and registered as the
	 *            main query searcher, making the changes visible
	 */
	public void commit(boolean waitFlush, boolean waitSearcher)
			throws Exception {
		solrClient.commit(waitFlush, waitSearcher);
	}

	/**
	 * 优化索引
	 */
	public void optimize() throws Exception {
		optimize(true, true);
	}

	/**
	 * When controlling the optimizing action at client side, finally execute
	 * optimizing.
	 * 
	 * @param waitFlush
	 *            block until index changes are flushed to disk
	 * @param waitSearcher
	 *            block until a new searcher is opened and registered as the
	 *            main query searcher, making the changes visible
	 */
	public void optimize(boolean waitFlush, boolean waitSearcher)
			throws Exception {
		solrClient.optimize(waitFlush, waitSearcher);
		commit(waitFlush, waitSearcher);
	}
	
	/**
	 * 未提交，可回退数据
	 * @throws Exception
	 */
	public void rollback() throws Exception{
		solrClient.rollback();
	}
	
	/**
	 * 
	 * @param doc
	 * @param commit 是否立即提交
	 * @throws Exception
	 */
	public void addDoc(Map<String, Object> doc, boolean commit) throws Exception {
		if(doc == null || doc.isEmpty()){
			return;
		}
		SolrInputDocument document = new SolrInputDocument();
		Iterator<String> iter = doc.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Object value = doc.get(key);
			document.addField(key, value);
		}
		solrClient.add(document);
		if(commit){
			commit();
		}
	}
	
	/**
	 * 
	 * @param doc
	 * @param commitWithinMs
	 *            多少毫秒之内提交
	 * @throws Exception
	 */
	public void addDoc(Map<String, Object> doc, int commitWithinMs) throws Exception {
		if(doc == null || doc.isEmpty()){
			return;
		}
		SolrInputDocument document = new SolrInputDocument();
		Iterator<String> iter = doc.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Object value = doc.get(key);
			document.addField(key, value);
		}
		solrClient.add(document, commitWithinMs);
	}
	
	/**
	 * 
	 * @param docs
	 * @param commit 是否立即提交
	 * @throws Exception
	 */
	public void addDocs(List<Map<String, Object>> docs, boolean commit) throws Exception {
		if(docs == null || docs.isEmpty()){
			return;
		}
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for(Map<String, Object> doc : docs){
			if(doc == null || doc.isEmpty()){
				continue;
			}
			SolrInputDocument document = new SolrInputDocument();
			Iterator<String> iter = doc.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				Object value = doc.get(key);
				document.addField(key, value);
			}
			documents.add(document);
		}
		
		solrClient.add(documents);
		if(commit){
			commit();
		}
	}
	
	/**
	 * 
	 * @param doc
	 * @param commitWithinMs
	 *            多少毫秒之内提交
	 * @throws Exception
	 */
	public void addDocs(List<Map<String, Object>> docs, int commitWithinMs) throws Exception {
		if(docs == null || docs.isEmpty()){
			return;
		}
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for(Map<String, Object> doc : docs){
			if(doc == null || doc.isEmpty()){
				continue;
			}
			SolrInputDocument document = new SolrInputDocument();
			Iterator<String> iter = doc.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				Object value = doc.get(key);
				document.addField(key, value);
			}
			documents.add(document);
		}
		
		solrClient.add(documents, commitWithinMs);
	}
	
	/**
	 * add bean
	 * 
	 * @param bean
	 * @param commit 是否立即提交
	 */
	public void addBean(Object bean, boolean commit) throws Exception {
		solrClient.addBean(bean);
		if(commit){
			commit();
		}
	}

	/**
	 * add bean
	 * 
	 * @param bean
	 * @param commitWithinMs
	 */
	public void addBean(Object bean, int commitWithinMs) throws Exception {
		solrClient.addBean(bean, commitWithinMs);
	}

	/**
	 * add beans
	 * 
	 * @param beans
	 * @param commit 是否立即提交
	 */
	public void addBeans(Collection<Object> beans, boolean commit) throws Exception {
		solrClient.addBeans(beans);
		if(commit){
			commit();
		}
	}

	/**
	 * add beans
	 * 
	 * @param beans
	 * @param commitWithinMs
	 */
	public void addBeans(Collection<Object> beans, int commitWithinMs) throws Exception {
		solrClient.addBeans(beans, commitWithinMs);
	}
}
