package com.demo.library.utility;

import java.time.LocalDate;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ErrorHandler {
	
	public static String getErrorMessage(BindingResult result) {
	StringBuilder errorMessages = new StringBuilder("Errors:\n");
	
		List<ObjectError> allErrors = result.getAllErrors();
		
		for(ObjectError error : allErrors) {
			errorMessages.append(error.getDefaultMessage() + "\n");
		}
		
		return errorMessages.toString();
	}
	
	public static boolean isFutureYear(int year) {
		return year > getCurrentYear();
	}
	
	public static int getCurrentYear() {
		return LocalDate.now().getYear();
	}
	
	public static String showErrorMessage(String messsage) {
		
		return "Error:\n " + messsage;
	}
}
