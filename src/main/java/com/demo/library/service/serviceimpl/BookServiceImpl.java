package com.demo.library.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.library.entity.Author;
import com.demo.library.entity.Book;
import com.demo.library.repository.BookRepository;
import com.demo.library.request.BookData;
import com.demo.library.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public boolean addBook(BookData book, Author author) {
		if (bookRepository.findByIsbn(book.getIsbn()) != null) {
			return false;
		}

		Book newBook = new Book();

		newBook.setTitle(book.getTitle());
		newBook.setAuthor(author);
		newBook.setIsbn(book.getIsbn());
		newBook.setPublicationYear(book.getPublicationYear());
		newBook.setAvailable(true);
		bookRepository.save(newBook);

		return true;
	}

	@Override
	public Book getBookById(int id) {
		return bookRepository.findById(id).orElse(null);
	}

	@Override
	public Book getBookByIsbn(String isbn) {
		return bookRepository.findByIsbn(isbn);
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public boolean updateBook(Book existedBook, BookData updatedBook, Author author) {

		if (!existedBook.isAvailable())
			return false;

		existedBook.setTitle(updatedBook.getTitle());
		existedBook.setAuthor(author);
		existedBook.setIsbn(updatedBook.getIsbn());
		existedBook.setPublicationYear(updatedBook.getPublicationYear());

		bookRepository.save(existedBook);
		return true;
	}

	@Override
	public boolean deleteBook(int id) {
		Book book = bookRepository.findById(id).orElse(null);
		if (book == null || !book.isAvailable())
			return false;

		bookRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Book> getAllBookByAuthor(Author author) {
		return bookRepository.findAllByAuthor(author);
	}

	@Override
	public List<Book> getAllAvailableBooks() {
		return bookRepository.findAllByIsAvailable(true);
	}

	@Override
	public List<Book> getAllRentedBooks() {
		return bookRepository.findAllByIsAvailable(false);
	}
}
