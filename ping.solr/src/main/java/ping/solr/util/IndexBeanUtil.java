package ping.solr.util;

import java.util.Collection;

public class IndexBeanUtil extends CommonIndexUtil {

	/**
	 * add bean
	 * 
	 * @param bean
	 * @param commit 是否立即提交
	 */
	public void addBean(Object bean, boolean commit) throws Exception {
		solrServer.addBean(bean);
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
		solrServer.addBean(bean, commitWithinMs);
	}

	/**
	 * add beans
	 * 
	 * @param beans
	 * @param commit 是否立即提交
	 */
	public void addBeans(Collection<Object> beans, boolean commit) throws Exception {
		solrServer.addBeans(beans);
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
		solrServer.addBeans(beans, commitWithinMs);
	}
}
