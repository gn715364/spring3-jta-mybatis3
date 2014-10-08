package com.gn.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gn.web.service.TestService;

@Controller
public class TestController {
	
	@Autowired
	private TestService testService;
	@RequestMapping("/Test")
	public ModelAndView helloWorld() {
		try {
			testService.test();
			testService.testQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView model = new ModelAndView("HelloWorldPage");
		model.addObject("msg", "hello world");
		
		return model;
	}
	
}
