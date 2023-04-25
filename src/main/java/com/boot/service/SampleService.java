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
public class SampleService {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberMapper memberMapper;
	
	public void sampleService() {
		 
//		IntStream.rangeClosed(1, 10).forEach(i -> {
//			MemberVo member = MemberVo.builder()
//			.name("Sample..." + i)
//			.id(i)
//			.build();
//			
//			System.out.println("member !!" + member);
//			memberRepository.save(member);
//		});
//		 
		List<MemberVo> memberList = memberMapper.getList();
		System.out.println("memberList >>" + memberList);
	}
	
	public String healthCheck(Map<String, Object> paramMap) {
		String result =  memberMapper.healthCheck(paramMap);
		
		System.out.println("result :" + result);
		
		return result;
	}
}
