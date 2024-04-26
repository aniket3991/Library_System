package com.demo.library.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.library.entity.Author;
import com.demo.library.entity.Book;
import com.demo.library.request.BookData;
import com.demo.library.service.AuthorService;
import com.demo.library.service.BookService;
import com.demo.library.utility.ErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("book")
public class BookController {
	
	private BookService bookService;
	private AuthorService authorService;
	
	@Autowired
	public BookController(BookService bookService, AuthorService authorService) {
		this.bookService = bookService;
		this.authorService = authorService;
	}
	
	@PostMapping(value = "/new")
	public String addNewBook(@Valid @RequestBody BookData book, BindingResult result){
		Author author = authorService.getAuthorById(book.getAuthorId());
		
		if(author == null) {
			result.addError(new ObjectError("InvalidAuthor", "Invalid Author Id"));
		}
		
		if(ErrorHandler.isFutureYear(book.getPublicationYear())) {
			result.addError(new ObjectError("FuterYear", "Year Must be Past or Present"));
		}
		
		if (result.hasErrors()) {
			return ErrorHandler.getErrorMessage(result);
		}
		
		if(!bookService.addBook(book, author)) {
			return ErrorHandler.showErrorMessage("Book Already present with same ISBN no."
					+ " please check carefully");
		}
		 
		return "Added new Book Successfully";
	}
	
	@GetMapping(value = "search", params = "id")
	public Book getBook(@RequestParam("id") int id) {
		return bookService.getBookById(id);
	}
	
	@GetMapping(value = "all")
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}
	
	@PostMapping(value = "/update")
	public String updateBook(@Valid @RequestBody BookData updatedBook, BindingResult result) {
		
		if(updatedBook.getId() != 0) {
			Author author = authorService.getAuthorById(updatedBook.getAuthorId());
			Book existedBook = bookService.getBookById(updatedBook.getId());
			
			if(existedBook == null)
				return ErrorHandler.showErrorMessage("Book not available in the records"
						+ " with given book id");
			
			if(!existedBook.isAvailable()) {
				return ErrorHandler.showErrorMessage("Rented Book can not be updated");
			}
			
			if(!updatedBook.getIsbn().equals(existedBook.getIsbn())) {
				if(bookService.getBookByIsbn(updatedBook.getIsbn()) != null) {
					result.addError(new ObjectError("ISBN", "Book already present with the updated "
							+ "ISBN number"));
				}
			}
			
			if(author == null) {
				result.addError(new ObjectError("InvalidAuthor", "Invalid Author Id"));
			}
			
			if(ErrorHandler.isFutureYear(updatedBook.getPublicationYear())) {
				result.addError(new ObjectError("FuterYear", "Year Must be Past or Present"));
			}
			
			if (result.hasErrors()) {
				return ErrorHandler.getErrorMessage(result);
			}
			
			bookService.updateBook(existedBook, updatedBook, author);
			return "Book Updated Successfully";
		}
		return ErrorHandler.showErrorMessage("Book not available in the records");
	}
	
	@GetMapping(value = "/delete", params = "id")
	public String deleteBook(@RequestParam("id") int id) {
		if (bookService.deleteBook(id)) {
			return "Book removed successfully";
		}
		return ErrorHandler.showErrorMessage("Book not exists or rented");
	}
	
	@GetMapping(value = "search", params = "authorId")
	public List<Book> getBookByAuthor(@RequestParam("authorId") int authorId) {
		Author author = authorService.getAuthorById(authorId);
		return bookService.getAllBookByAuthor(author);
	}
	
	@GetMapping(value = "/all/available")
	public List<Book> getAllAvailableBooks() {
		return bookService.getAllAvailableBooks();
	}
	
	@GetMapping(value = "/all/rented")
	public List<Book> getAllRentedBooks() {
		return bookService.getAllRentedBooks();
	}
}
