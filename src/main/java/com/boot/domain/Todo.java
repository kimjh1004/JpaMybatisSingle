package com.boot.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import lombok.*;

@Entity
@Table(name="tbl_todo")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
		name = "TODO_SEQ_GENERATOR"
	    , sequenceName = "TNO_SEQ"
	    , initialValue = 1
	    , allocationSize = 1
	)
public class Todo {
	@Id
	@GeneratedValue(
	    	strategy = GenerationType.SEQUENCE
	    	, generator = "TODO_SEQ_GENERATOR"
	    )
	private Long tno;
	
	private String title;
	
	private String wirter;
	
	private boolean complete;
	
	private LocalDate dueDate;

	public void changeTitle(String title) {
		this.title = title;
	}
	
	public void changeComplete(boolean complete) {
		this.complete=complete;
	}
	
	public void changeDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
}
