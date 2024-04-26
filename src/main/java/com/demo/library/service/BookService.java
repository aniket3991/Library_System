package com.demo.library.service;

import java.util.List;

import com.demo.library.entity.Author;
import com.demo.library.entity.Book;
import com.demo.library.request.BookData;

public interface BookService {
	
	boolean addBook(BookData book, Author author);

	Book getBookById(int id);
	
	Book getBookByIsbn(String isbn);

	List<Book> getAllBooks();
	
	boolean updateBook(Book existedBook, BookData updatedBook, Author author);

	boolean deleteBook(int id);
	
	List<Book> getAllBookByAuthor(Author author);

	List<Book> getAllAvailableBooks();

	List<Book> getAllRentedBooks(); 
}
