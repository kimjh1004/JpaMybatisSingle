package com.boot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data                   
@NoArgsConstructor      
@AllArgsConstructor      
@Builder
@Entity
@Table(name = "MEMBER")
public class MemberVo {
          
    @Id 
    @GeneratedValue
    private long id;
          
    private String name;
}