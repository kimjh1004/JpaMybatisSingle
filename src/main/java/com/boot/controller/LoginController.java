package com.boot.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.boot.service.SampleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	@Autowired
	SampleService service;
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@GetMapping(value = "/login/getKakaoAuthUrl")
	public @ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {
		String reqUrl = 
				"https://kauth.kakao.com/oauth/authorize"
				+ "?client_id=2caeef4e8238b4dafe827ac7d7b26204"
				+ "&redirect_uri=http://localhost:2222/login/kakao"
				+ "&response_type=code";
		
		return reqUrl;
	}
	
	
	@GetMapping(value = "/login")
    public String home() {
        //return "login";
		return "login2";
    }
	
	@PostMapping(value = "/googleTest")
	@ResponseBody
    public void test() {
        System.out.println("---->");
    }
	
	@ResponseBody
	@PostMapping(value = "/login")
    public HashMap<String, Object> login(@RequestParam Map<String, Object> paramMap) {
		HashMap<String, Object> resulthashMap = new HashMap<>();
		log.info("파람 :" + paramMap);
		
		//resulthashMap.put("resultList", service.memberCheck(paramMap));
        return resulthashMap;
    }
	
	@GetMapping("/login/kakao")
	public String kakaoGet(@RequestParam String code) throws JsonProcessingException {
		System.out.println("카카오 로그인 응답 : " + code);
		
		//getAccessToken(code);
		
		// HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getAccessToken(code));
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.get("kakao_account").get("email").asText();
        String nickname = jsonNode.get("properties").get("nickname").asText();
		
 
        System.out.println("id :" + id);
        System.out.println("email :" + email);
        System.out.println("nickname : " + nickname);
		
		return "redirect:/";
	}
	
	public String getAccessToken (String authorize_code) {
		String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=2caeef4e8238b4dafe827ac7d7b26204");  //본인이 발급받은 key
			sb.append("&redirect_uri=http://localhost:2222/login/kakao");     // 본인이 설정해 놓은 경로
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();
			
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			
			//    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			 System.out.println("response body : " + result);
			
			//    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			
			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
			
			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);
			
		    br.close();
		    bw.close();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

        return access_Token;
    }
	
	
	
	@ResponseBody
	@GetMapping("/login/googleOauth")
	public String googleGet() {
		System.out.println("구글 GET 응답 : ");
		
		String googleClientId = "1052686149956-b24734oudhor0pv84l1gi15jehac95kq.apps.googleusercontent.com";
		
		
		String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
	                + "&redirect_uri=http://localhost:2222/login/google&response_type=code&scope=email%20profile%20openid&access_type=offline";
		  
		 
		
		return reqUrl;
	}
	
	@GetMapping("/login/google")
	public String googleGet(@RequestParam String code) {
		System.out.println("구글 GET 응답 : " + code);
		
		String accessToken = getAccessToken(code, "google"); 
		
		System.out.println("구글 accessToken :" + accessToken);
		
		JsonNode userResourceNode = getUserResource(accessToken, "google");
		
		System.out.println("구글 사용자 정보 :" + userResourceNode);
		
		return "redirect:/";
	}
	
	// 로그아웃
	@GetMapping("/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	
	 
	private String getAccessToken(String authorizationCode, String registrationId) {
		 String clientId = "1052686149956-b24734oudhor0pv84l1gi15jehac95kq.apps.googleusercontent.com";
         String clientSecret = "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";
         String redirectUri = "http://localhost:2222/login/google";
         String tokenUri = "https://oauth2.googleapis.com/token";

         MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
         params.add("code", authorizationCode);
         params.add("client_id", clientId);
         params.add("client_secret", clientSecret);
         params.add("redirect_uri", redirectUri);
         params.add("grant_type", "authorization_code");

         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

         HttpEntity entity = new HttpEntity(params, headers);

         //RestTemplate restTemplate = new RestTemplate();
         ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
         JsonNode accessTokenNode = responseNode.getBody();
	     
         return accessTokenNode.get("access_token").asText();
	
	}
	 
    private JsonNode getUserResource(String accessToken, String registrationId) {
    	String resourceUri = "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
}
