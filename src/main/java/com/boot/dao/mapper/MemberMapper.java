package com.boot.dao.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boot.domain.MemberVo;

@Repository
public interface MemberMapper {
	
	public List<MemberVo> getList();
}
