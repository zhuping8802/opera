package org.ping.spring.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.annotation.JsonFormat;

//@JsonInclude(Include.NON_NULL)
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
	
	private Date birth = new Date();
	
	private BigDecimal money = new BigDecimal(100000);
	
	private Phone phone;
	
	private Map<String, Integer> numbers;
	
	private String[] array = new String[5];
	
	public String[] getArray() {
		return array;
	}

	public void setArray(String[] array) {
		this.array = array;
	}

	public Map<String, Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(Map<String, Integer> numbers) {
		this.numbers = numbers;
	}

	private List<Mobile> mobiles;
	
	public List<Mobile> getMobiles() {
		return mobiles;
	}

	public void setMobiles(List<Mobile> mobiles) {
		this.mobiles = mobiles;
	}

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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone="GMT+8")
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	@NumberFormat(style=Style.CURRENCY)
	public BigDecimal getMoney() {
		return money;
	}
	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}
