package com.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.service.SampleService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SampleController {
	
	@Autowired
	SampleService service;
	
	@GetMapping(value = "/")
    public String home() {
        return "index";
    }
 
	@GetMapping("/test")
	public String test() {
		//service.sampleService();
		return "test";
	}
	
	
	@GetMapping({"/sample/exLayout1", "/sample/exLayout2","/sample/exTemplate","/sample/exSidebar"})
    public void exLayout1() {
        log.info("exLayout1...");
    }
}
