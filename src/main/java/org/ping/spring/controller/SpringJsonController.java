package org.ping.spring.controller;

import javax.validation.Valid;

import org.ping.spring.bean.Person;
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

	@RequestMapping("show")
	@ResponseBody
	public Person showPerson(@Valid Person person, BindingResult result){
		person.setBindingResult(result);
		return person;
	}
}
