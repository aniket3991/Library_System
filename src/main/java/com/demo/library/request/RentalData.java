package com.demo.library.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RentalData {
	
	private int bookId;
	
	@NotBlank(message = "Renter name can not be blank")
	@Size(min = 3, max = 100, message = "Renter name must be between 3 to 100 characters")
	private String renterName;
	
	@Min(value = 1, message = "Minimum rented days should be 1")
	@Max(value = 60, message = "Can not rent a book for more than 60 days")
	private int rentedDays;
}
