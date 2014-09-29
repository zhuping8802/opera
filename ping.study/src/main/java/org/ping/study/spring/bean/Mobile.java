package org.ping.study.spring.bean;

import java.io.Serializable;

//@JsonInclude(Include.NON_NULL)
//@JsonIgnoreProperties(value={"info"})
public class Mobile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6929161758689915477L;
	
	private String name;
	
	private MobileInfo info;

	public MobileInfo getInfo() {
		return info;
	}

	public void setInfo(MobileInfo info) {
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
