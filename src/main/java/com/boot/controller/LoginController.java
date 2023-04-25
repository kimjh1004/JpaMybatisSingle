package com.boot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.service.SampleService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	@Autowired
	SampleService service;
	
	@GetMapping(value = "/login")
    public String home() {
        return "login";
    }
	
	@PostMapping(value = "/login")
	@ResponseBody
    public HashMap<String, Object> login(@RequestParam Map<String, Object> paramMap) {
		HashMap<String, Object> resulthashMap = new HashMap<>();
		log.info("파람 :" + paramMap);
		
		resulthashMap.put("resultList", service.healthCheck(paramMap));
        return resulthashMap;
    }
}
