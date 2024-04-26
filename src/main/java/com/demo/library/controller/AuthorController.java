package com.demo.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.library.entity.Author;
import com.demo.library.service.AuthorService;
import com.demo.library.utility.ErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("author")
public class AuthorController {

	private AuthorService authorService;

	@Autowired
	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@PostMapping(value = "/new")
	public String addAuthor(@Valid @RequestBody Author author, BindingResult result) {

		if (result.hasErrors()) {
			return ErrorHandler.getErrorMessage(result);
		}

		if (authorService.addAuthor(author) != null) {
			return "Author added Successfully";
		}

		return ErrorHandler.showErrorMessage("Something went wrong please try again");
	}

	@GetMapping(value = "search", params = "id")
	public Author getAuthor(@RequestParam("id") int id) {
		return authorService.getAuthorById(id);
	}

	@GetMapping(value = "all")
	public List<Author> getAllAuthors() {
		return authorService.getAllAuthor();
	}

	@PostMapping(value = "/update")
	public String updateAuthor(@Valid @RequestBody Author author, BindingResult result) {
		
		if (result.hasErrors()) {
			return ErrorHandler.getErrorMessage(result);
		}
		
		Author existedAuthor = authorService.getAuthorById(author.getId());

		if (existedAuthor != null) {
			existedAuthor.setName(author.getName());
			existedAuthor.setBiography(author.getBiography());
			authorService.updateAuthor(existedAuthor);
			return "Updated Successfully";
		}
		return ErrorHandler.showErrorMessage("Author does not exist");
	}

	@GetMapping(value = "/delete", params = "id")
	public String deleteAuthor(@RequestParam("id") int id) {
		if (authorService.deleteAuthor(id)) {
			return "Author removed successfully";
		}
		return ErrorHandler.showErrorMessage("Author not exists please check id again");
	}
}
