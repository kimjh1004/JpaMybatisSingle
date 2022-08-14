package com.boot.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.domain.MemberVo;

public interface MemberRepository extends JpaRepository<MemberVo, Long>{

}
