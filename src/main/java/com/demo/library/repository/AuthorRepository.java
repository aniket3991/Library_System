package com.demo.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.library.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
	
}
