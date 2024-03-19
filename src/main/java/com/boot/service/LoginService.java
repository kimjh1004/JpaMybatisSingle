package com.boot.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginService {

	private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();
    
    public LoginService(Environment env) {
        this.env = env;
    }
    
    public Map<String,String> socialLogin(String code, String social) {
    	Map<String,String> resultMap = new HashMap<String,String>();
    	
    	String accessToken = getAccessToken(code, social);
        JsonNode userResourceNode = getUserResource(accessToken, social);
        log.info("userResourceNode = " + userResourceNode);

        switch(social) {
        	case "google" :
        		resultMap.put("id", userResourceNode.get("id").asText());
        	    resultMap.put("email", userResourceNode.get("email").asText());
        	    resultMap.put("nickname", userResourceNode.get("name").asText());
        		break;
        	
        	case "kakao" :
        		resultMap.put("id", userResourceNode.get("id").asText());
        	    resultMap.put("email", userResourceNode.get("kakao_account").get("email").asText());
        	    resultMap.put("nickname", userResourceNode.get("properties").get("nickname").asText());
        	    
        		break;
        	
        }
        
        return resultMap;
    }

    private String getAccessToken(String authorizationCode, String social) {
        String clientId = env.getProperty("oauth2." + social + ".client-id");
        String clientSecret = env.getProperty("oauth2." + social + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + social + ".redirect-uri");
        String tokenUri = env.getProperty("oauth2." + social + ".token-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }
    
    private JsonNode getUserResource(String accessToken, String social) {
        String resourceUri = env.getProperty("oauth2."+social+".resource-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
}
