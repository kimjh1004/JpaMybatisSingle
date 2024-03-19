package com.boot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.service.LoginService;
import com.boot.service.SampleService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	@Autowired
	SampleService service;
	
	@Autowired
	LoginService loginService;
	
	@ResponseBody 
	@GetMapping(value = "/login/kakaoOauth")
	public String getKakaoAuthUrl(HttpServletRequest request) throws Exception {
		String reqUrl = 
				"https://kauth.kakao.com/oauth/authorize?"
				+ "client_id=2caeef4e8238b4dafe827ac7d7b26204"
				+ "&redirect_uri=http://localhost:2222/login/kakao"
				+ "&response_type=code";
		
		return reqUrl;
	}
	
	
	@ResponseBody
	@GetMapping("/login/googleOauth")
	public String googleGet() {
		String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
				      + "client_id=1052686149956-b24734oudhor0pv84l1gi15jehac95kq.apps.googleusercontent.com"
	                  + "&redirect_uri=http://localhost:2222/login/google"
	                  + "&response_type=code"
	                  + "&scope=email%20profile%20openid"
	                  + "&access_type=offline";
		  
		return reqUrl;
	}
	
	@GetMapping("/login/{registrationId}")
    public String googleLogin(@RequestParam String code, @PathVariable String registrationId) {
		Map<String,String> memberData = loginService.socialLogin(code, registrationId);
		
		log.info("소셜 로그인 정보  {}" , memberData.toString());
		return "redirect:/";
    }
	
	@GetMapping(value = "/login")
    public String home() {
        //return "login";
		return "login2";
    }
	
	@ResponseBody
	@PostMapping(value = "/login")
    public HashMap<String, Object> login(@RequestParam Map<String, Object> paramMap) {
		HashMap<String, Object> resulthashMap = new HashMap<>();
		log.info("파람 :" + paramMap);
		
		//resulthashMap.put("resultList", service.memberCheck(paramMap));
        return resulthashMap;
    }
	
	// 로그아웃
	@GetMapping("/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
