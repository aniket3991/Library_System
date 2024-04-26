package com.demo.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.library.entity.Author;
import com.demo.library.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
	
	Book findByIsbn(String isbn);
	
	List<Book> findAllByAuthor(Author author);
	
	List<Book> findAllByIsAvailable(boolean available); 
}
