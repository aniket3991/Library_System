package com.demo.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.library.entity.Rental;
import com.demo.library.request.RentalData;
import com.demo.library.service.BookService;
import com.demo.library.service.RentalService;
import com.demo.library.utility.ErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("rental")
public class RentalController {
	
	private RentalService rentalService;
	
	@Autowired
	public RentalController(BookService bookService, RentalService rentalService) {
		this.rentalService = rentalService;
	}
	
	@PostMapping(value = "/rent")
	public String rentBook(@Valid @RequestBody RentalData rental, BindingResult result) {
		
		if (result.hasErrors()) {
			return ErrorHandler.getErrorMessage(result);
		}
		
		if(rentalService.rentBook(rental)) {
			return "Book Successfully rented for " + rental.getRentedDays() + " Days";
		}
		return ErrorHandler.showErrorMessage("Book is already rented or not available at this movement");
	}
	
	@GetMapping(value = "/return", params = "id")
	public String returnBook(@RequestParam("id") int rentalId) {
		
		int dueDays = rentalService.returnBook(rentalId);
		
		if(dueDays == -1)
			return ErrorHandler.showErrorMessage("Incorrect Rental Id or Book is alreay returned");
		
		if(dueDays > 0)
			return "Returned Successfully but you will be chargable for extra " + dueDays + " days";
		
		return "Returned Successfully";
	}
	
	@GetMapping(value = "/all")
	public List<Rental> getAllRentalRecords(){
		return rentalService.getAllRentalRecords();
	}
}
