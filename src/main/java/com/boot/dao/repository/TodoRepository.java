package com.boot.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.boot.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{

}
