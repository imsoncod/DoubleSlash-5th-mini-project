package com.doubleslash.mini.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/api/test")
@Controller
public class TestController {
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public String Test() {
		return "\"Server Status\" : \"ON\"";
	}
	
}
