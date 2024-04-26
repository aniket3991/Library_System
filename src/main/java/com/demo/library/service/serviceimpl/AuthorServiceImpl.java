package com.demo.library.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.library.entity.Author;
import com.demo.library.repository.AuthorRepository;
import com.demo.library.service.AuthorService;


@Service
public class AuthorServiceImpl implements AuthorService{
	
	private AuthorRepository authorRepository;
	
	@Autowired
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}
	
	@Override
	public Author addAuthor(Author author) {
		
		return authorRepository.save(author);
	}

	@Override
	public List<Author> getAllAuthor() {
		return authorRepository.findAll();
	}

	@Override
	public Author getAuthorById(int id) {
		return authorRepository.findById(id).orElse(null);
	}

	@Override
	public Author updateAuthor(Author existedAuthor) {
		return authorRepository.save(existedAuthor);
	}

	@Override
	public boolean deleteAuthor(int id) {
		if(getAuthorById(id) == null)
			return false;
		
		authorRepository.deleteById(id);
		return true;
	}

}
