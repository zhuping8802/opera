package org.ping.spring.controller;

import javax.validation.Valid;

import org.ping.spring.bean.Person;
import org.ping.spring.bean.PersonFilter;
import org.ping.spring.json.annotation.ObjectJsonFilter;
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
public class SpringJsonController {
	
//	http://localhost:8080/opera/springjson/show.do?phone.phone=2222&phone.telPhone=3333&name=zh&eamil=d&mobiles[0].name=dddd&mobiles[1].name=apple&numbers[n1]=21&numbers[0]=44&array[4]=aaaa&mobiles[1].info.desc=appledesc
	@RequestMapping("show")
	@ResponseBody
	@ObjectJsonFilter(target=Person.class, mixin = PersonFilter.class)
	public Person showPerson(@Valid Person person, BindingResult result){
		return person;
	}
	
	@RequestMapping("hello")
	@ResponseBody
	public String hello(){
		return "hello world!";
	}
}
