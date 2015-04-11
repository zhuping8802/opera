package ping.solr.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;

public class CommonQueryUtil extends SolrServer {

	/**
	 * Query.
	 * 
	 * @param params
	 *            查询参数
	 * @param fields
	 *            返回字段
	 * @return
	 */
	public List<Map<String, Object>> query(SolrParams params, String[] fields) throws Exception {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		SolrDocumentList documents = solrClient.query(params).getResults();
		Iterator<SolrDocument> iter = documents.iterator();
		while (iter.hasNext()) {
			SolrDocument doc = iter.next();
			Map<String, Object> map = new HashMap<String, Object>();
			for (String field : fields) {
				map.put(field, doc.getFieldValue(field));
			}
			results.add(map);
		}

		return results;
	}

	/**
	 * Query.
	 *
	 * @param fields
	 *            返回字段
	 * @param fq
	 *            title:Oracle
	 * @return
	 */
	public List<Map<String, Object>> query(String[] fields, String... fq) throws Exception {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		SolrQuery query = new SolrQuery();
		for (String item : fq) {
			query.setQuery(item);
		}

		SolrDocumentList documents = solrClient.query(query).getResults();
		Iterator<SolrDocument> iter = documents.iterator();
		while (iter.hasNext()) {
			SolrDocument doc = iter.next();
			Map<String, Object> map = new HashMap<String, Object>();
			for (String field : fields) {
				map.put(field, doc.getFieldValue(field));
			}
			results.add(map);
		}

		return results;
	}

	/**
	 * Query.
	 *
	 * @param fields
	 *            返回字段
	 * @param fq
	 *            title:Oracle
	 * @return
	 */
	public List<Map<String, Object>> query(String[] fields, int start, int count, String... fq) throws Exception {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		SolrQuery query = new SolrQuery();
		for (String item : fq) {
			query.setQuery(item);
		}
		query.setStart(start);
		query.setRows(count);

		SolrDocumentList documents = solrClient.query(query).getResults();
		Iterator<SolrDocument> iter = documents.iterator();
		while (iter.hasNext()) {
			SolrDocument doc = iter.next();
			Map<String, Object> map = new HashMap<String, Object>();
			for (String field : fields) {
				map.put(field, doc.getFieldValue(field));
			}
			results.add(map);
		}

		return results;
	}

	/**
	 * Query.
	 *
	 * @param fields
	 *            返回字段
	 * @param sorts
	 *            排序字段:Map<String, String>:("title","1")
	 * @param fq
	 *            title:Oracle
	 * @return
	 */
	public List<Map<String, Object>> query(String[] fields, Map<String, String> sorts, int start, int count, String... fq) throws Exception {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		SolrQuery query = new SolrQuery();
		for (String item : fq) {
			query.setQuery(item);
		}
		query.setStart(start);
		query.setRows(count);

		Iterator<String> iter = sorts.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			String value = sorts.get(key);

			if (value.equals("1")) {
				query.addSort(key, SolrQuery.ORDER.asc);
			} else {
				query.addSort(key, SolrQuery.ORDER.desc);
			}
		}

		SolrDocumentList documents = solrClient.query(query).getResults();
		Iterator<SolrDocument> iterRtn = documents.iterator();
		while (iterRtn.hasNext()) {
			SolrDocument doc = iterRtn.next();
			Map<String, Object> map = new HashMap<String, Object>();
			for (String field : fields) {
				map.put(field, doc.getFieldValue(field));
			}
			results.add(map);
		}

		return results;
	}
	
	public <T> List<T> queryBeans(SolrParams params, Class<T> clazz) throws Exception{
		return solrClient.query(params).getBeans(clazz);
	}
	
	public <T> List<T> queryBeans(Class<T> clazz, Map<String, String> sorts, int start, int count, String... fq) throws Exception{
		SolrQuery query = new SolrQuery();
		for (String item : fq) {
			query.setQuery(item);
		}
		query.setStart(start);
		query.setRows(count);

		Iterator<String> iter = sorts.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			String value = sorts.get(key);

			if (value.equals("1")) {
				query.addSort(key, SolrQuery.ORDER.asc);
			} else {
				query.addSort(key, SolrQuery.ORDER.desc);
			}
		}

		return solrClient.query(query).getBeans(clazz);
	}
	
	public <T> List<T> queryBeans(Class<T> clazz, int start, int count, String... fq) throws Exception{
		SolrQuery query = new SolrQuery();
		for (String item : fq) {
			query.setQuery(item);
		}
		query.setStart(start);
		query.setRows(count);

		return solrClient.query(query).getBeans(clazz);
	}
	
	public <T> List<T> queryBeans(Class<T> clazz, String... fq) throws Exception{
		SolrQuery query = new SolrQuery();
		for (String item : fq) {
			query.setQuery(item);
		}

		return solrClient.query(query).getBeans(clazz);
	}
}
