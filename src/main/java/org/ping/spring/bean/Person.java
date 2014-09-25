package org.ping.spring.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.validation.BindingResult;

public class Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4411004026266577219L;

	@NotNull(message="不能为空")
	@Size(min=3,max=20,message="name长度位于3-20之间")
	private String name;
	
	@Email(message="不是正确认的邮箱地址")
	private String eamil;
	
	@DateTimeFormat(pattern="yyyy年MM月dd日")
	private Date birth = new Date();
	
	@NumberFormat(style=Style.CURRENCY)
	private BigDecimal money = new BigDecimal(100000);
	
	private Phone phone;
	
	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	private BindingResult bindingResult;

	public BindingResult getBindingResult() {
		return bindingResult;
	}

	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEamil() {
		return eamil;
	}

	public void setEamil(String eamil) {
		this.eamil = eamil;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}
