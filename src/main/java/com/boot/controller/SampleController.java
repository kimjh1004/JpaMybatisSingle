package com.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.service.SampleService;

@Controller
public class SampleController {
	
	@Autowired
	SampleService service;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		service.sampleService();
		return "abc";
	}
}
