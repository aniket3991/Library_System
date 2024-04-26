package com.demo.library.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class BookData {
	
	private int id;
	
	@NotBlank(message = "Title can not be blank")
	@Size(min = 3, max = 100, message = "Title must be between 3 to 100 characters")
	private String title;
	
	private int authorId;
	
	@NotBlank(message = "ISBN no. can not be blank")
	@Pattern(regexp = "(?:((\\d-?){10})|((\\d-?){13}))", message = "ISBN must be 10 or 13 digits only")
	private String isbn;
	
	@Max(value = 9999, message = "Invalid year, should be in formate (yyyy)")
	private int publicationYear;
}
