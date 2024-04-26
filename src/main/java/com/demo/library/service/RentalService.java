package com.demo.library.service;

import java.util.List;

import com.demo.library.entity.Rental;
import com.demo.library.request.RentalData;

public interface RentalService {
	boolean rentBook(RentalData rental);
	
	int returnBook(int rentalid);

	List<Rental> getAllRentalRecords();
}
