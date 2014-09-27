package org.ping.spring.controller;

import javax.validation.Valid;

import org.ping.spring.bean.Mobile;
import org.ping.spring.bean.MobileFilter;
import org.ping.spring.bean.Person;
import org.ping.spring.bean.PersonFilter;
import org.ping.spring.json.annotation.AllowProperty;
import org.ping.spring.json.annotation.IgnoreProperty;
import org.ping.spring.json.annotation.ObjectJsonFilter;
import org.ping.spring.json.annotation.ObjectJsonFilters;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * spring 
 * @author ping.zhu
 *
 */
@Controller
@RequestMapping("/springjson")
@ObjectJsonFilters(value={@ObjectJsonFilter(target=Mobile.class, mixin = MobileFilter.class)})
//@AllowProperty(pojo=Person.class, name={"name"})
public class SpringJsonController {
	
	/**
	 * 第一种动态过滤对象字段属性
	 * @param person
	 * @param result
	 * @return
	 */
//	http://localhost:8080/opera/springjson/show.do?phone.phone=2222&phone.telPhone=3333&name=zh&eamil=d&mobiles[0].name=dddd&mobiles[1].name=apple&numbers[n1]=21&numbers[0]=44&array[4]=aaaa&mobiles[1].info.desc=appledesc
	@RequestMapping("show")
	@ResponseBody
//	@ObjectJsonFilter(target=Person.class, mixin = PersonFilter.class)
//	@ObjectJsonFilter(target=Mobile.class, mixin = MobileFilter.class)
	public Person showPerson(@Valid Person person, BindingResult result){
		return person;
	}
	
//	http://localhost:8080/opera/springjson/show2.do?phone.phone=2222&phone.telPhone=3333&name=zh&eamil=d&mobiles[0].name=dddd&mobiles[1].name=apple&numbers[n1]=21&numbers[0]=44&array[4]=aaaa&mobiles[1].info.desc=appledesc
	@RequestMapping("show2")
	@ResponseBody
	@IgnoreProperty(pojo=Person.class, name={"money", "eamil"})
//	@AllowProperty(pojo=Person.class, name={"money"})
	public Person showPerson2(@Valid Person person, BindingResult result){
		return person;
	}
	
	@RequestMapping("hello")
	@ResponseBody
	public String hello(){
		return "hello world!";
	}
}
