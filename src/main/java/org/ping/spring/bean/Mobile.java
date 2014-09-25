package org.ping.spring.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(value={"info"})
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
