package com.demo.library.service.serviceimpl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.library.entity.Book;
import com.demo.library.entity.Rental;
import com.demo.library.repository.BookRepository;
import com.demo.library.repository.RentalRepository;
import com.demo.library.request.RentalData;
import com.demo.library.service.RentalService;

@Service
public class RentalServiceImpl implements RentalService{
	
	private BookRepository bookRepository;
	private RentalRepository rentalRepository;
	
	@Autowired
	public RentalServiceImpl(BookRepository bookRepository, RentalRepository rentalRepository) {
		this.bookRepository = bookRepository;
		this.rentalRepository = rentalRepository;
	}
	
	@Override
	public boolean rentBook(RentalData rental) {
		
		Book book  = bookRepository.findById(rental.getBookId()).orElse(null);
		if(book == null || !book.isAvailable()) {
			return false;
		}
		
		Rental rentBook = new Rental();
		
		rentBook.setBook(book);
		rentBook.setRentalDate(LocalDate.now());
		rentBook.setRentedDays(rental.getRentedDays());
		rentBook.setRenterName(rental.getRenterName());
		
		markBookRented(book);
		rentalRepository.save(rentBook);
		return true;
	}

	@Override
	public int returnBook(int rentalid) {
		Rental rental = rentalRepository.findById(rentalid).orElse(null);
		int dueDays = 0;
		
		if(rental == null || rental.getReturnDate() != null)
			return -1;
		
		int totalDays = (int)ChronoUnit.DAYS.between(rental.getRentalDate(), LocalDate.now());
		
		if(totalDays > rental.getRentedDays())
			dueDays = totalDays - rental.getRentedDays();
			
		markBookAvailable(rental.getBook());
		rental.setReturnDate(LocalDate.now());
		
		rentalRepository.save(rental);
		
		return dueDays;
		
	}
	
	private void markBookRented(Book book) {
		book.setAvailable(false);
		bookRepository.save(book);
	}
	
	private void markBookAvailable(Book book) {
		book.setAvailable(true);
		bookRepository.save(book);
	}

	@Override
	public List<Rental> getAllRentalRecords() {
		return rentalRepository.findAll();
	}

}
