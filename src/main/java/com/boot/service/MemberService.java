package com.boot.service;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.dao.mapper.MemberMapper;
import com.boot.dao.repository.MemberRepository;
import com.boot.domain.MemberVo;

@Service
public class MemberService {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberMapper memberMapper;
	
 
	
	public String memberCheck(Map<String, Object> paramMap) {
		String result =  memberMapper.healthCheck(paramMap);
		
		System.out.println("result :" + result);
		
		return result;
	}
}
