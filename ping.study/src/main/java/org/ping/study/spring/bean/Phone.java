package org.ping.study.spring.bean;

import java.io.Serializable;

public class Phone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String telPhone;
	
	private String phone;

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
