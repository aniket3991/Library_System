package com.demo.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.library.entity.Author;



@Service
public interface AuthorService {
	
	Author addAuthor(Author author);
	
	Author getAuthorById(int id);
	
	List<Author> getAllAuthor();
	
	Author updateAuthor(Author existedAuthor);
	
	boolean deleteAuthor(int id);
}
