package com.boot;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.boot.dao.repository.TodoRepository;
import com.boot.domain.Todo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class ApplicationTests {
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Test
	public void test() {
		List<String> list = new ArrayList<>();
		list.add("h");
		list.add("i");

		list.forEach(s -> System.out.println(list.indexOf(s) + " : " + s));
		
		list.forEach(System.out::println);
	}

	//@Test
	public void testPagin() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
		
		Page<Todo> result = todoRepository.findAll(pageable);
		
		log.info(result.getTotalElements());
		
		result.getContent().stream().forEach(todo -> log.info(todo));
	}
}
