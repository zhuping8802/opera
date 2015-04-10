package org.ping.study.solr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/solr")
public class SolrController {
	
	@RequestMapping("find")
	@ResponseBody
	public Object find(){
		return "OK";
	}
}
