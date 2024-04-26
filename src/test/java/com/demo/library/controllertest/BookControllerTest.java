package com.demo.library.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.demo.library.request.BookData;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

	@LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Test 
    public void testAddBookSuccess() {
        String url = "http://localhost:" + port + "/book/new";
        BookData bookData = new BookData();
        bookData.setTitle("Time Travel 2");
        bookData.setAuthorId(3);
        bookData.setIsbn("7895632564359");
        bookData.setPublicationYear(2006);

        ResponseEntity<String> response = restTemplate.postForEntity(url, bookData, String.class);
        assertEquals("Added new Book Successfully", response.getBody());
    }
    
    @Test 
    public void testAddBookTitleError() {
        String url = "http://localhost:" + port + "/book/new";
        BookData bookData = new BookData();
        bookData.setTitle("Ti");
        bookData.setAuthorId(3);
        bookData.setIsbn("7896542365896");
        bookData.setPublicationYear(2024);

        ResponseEntity<String> response = restTemplate.postForEntity(url, bookData, String.class);
        assertEquals("Errors:\nTitle must be between 3 to 100 characters\n", response.getBody());
    }
    
    @Test 
    public void testAddBookInvalidAuthor() {
        String url = "http://localhost:" + port + "/book/new";
        BookData bookData = new BookData();
        bookData.setTitle("Title of book");
        bookData.setAuthorId(120);
        bookData.setIsbn("7896542365896");
        bookData.setPublicationYear(2024);

        ResponseEntity<String> response = restTemplate.postForEntity(url, bookData, String.class);
        assertEquals("Errors:\nInvalid Author Id\n", response.getBody());
    }
    
    @Test 
    public void testAddBookFutureYear() {
        String url = "http://localhost:" + port + "/book/new";
        BookData bookData = new BookData();
        bookData.setTitle("Title of book");
        bookData.setAuthorId(2);
        bookData.setIsbn("7896542365896");
        bookData.setPublicationYear(2030);

        ResponseEntity<String> response = restTemplate.postForEntity(url, bookData, String.class);
        assertEquals("Errors:\nYear Must be Past or Present\n", response.getBody());
    }
    
    @Test 
    public void testAddBookInvalidIsbn() {
        String url = "http://localhost:" + port + "/book/new";
        BookData bookData = new BookData();
        bookData.setTitle("Title of book");
        bookData.setAuthorId(2);
        bookData.setIsbn("789656589");
        bookData.setPublicationYear(2021);

        ResponseEntity<String> response = restTemplate.postForEntity(url, bookData, String.class);
        System.out.println(response.getBody());
        assertEquals("Errors:\nISBN must be 10 or 13 digits only\n", response.getBody());
    }
    
    @Test 
    public void testAddBookErrorIsbnAlreadyPresent() {
        String url = "http://localhost:" + port + "/book/new";
        BookData bookData = new BookData();
        bookData.setTitle("Title of book");
        bookData.setAuthorId(2);
        bookData.setIsbn("7896565896");
        bookData.setPublicationYear(2021);

        ResponseEntity<String> response = restTemplate.postForEntity(url, bookData, String.class);
        assertEquals("Error:\n Book Already present with same ISBN no. please check carefully",
        		response.getBody());
    }
    
	@Test
	public void testSearchBookSuccess() {
		String url = "http://localhost:" + port + "/book/search?id=3";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		System.out.println(response.getBody());
		assertEquals("{\"id\":3,\"title\":\"The Great novel 5\"," + "\"author\":{\"id\":2,\"name\":\"Premchand\","
				+ "\"biography\":null},\"isbn\":\"1234567891\"," + "\"publicationYear\":2024,\"available\":false}",
				response.getBody());
	}
    
    @Test
    public void testSearchBookNotFound() {
        String url = "http://localhost:" + port + "/book/search?id=4562";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertNull(response.getBody());
    }
    
	@Test
	public void testGetAllBookSuccess() {
		String url = "http://localhost:" + port + "/book/all";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertTrue(response.getBody()
				.contains("{\"id\":3,\"title\":\"The Great novel 5\"," + "\"author\":{\"id\":2,\"name\":\"Premchand\","
						+ "\"biography\":null},\"isbn\":\"1234567891\","
						+ "\"publicationYear\":2024,\"available\":false}"));
	}
    
    @Test 
    public void testUpdateBookSuccess() {
        String url = "http://localhost:" + port + "/book/update";
        BookData bookData = new BookData();
        bookData.setId(5);
        bookData.setTitle("Time Travel 3");
        bookData.setAuthorId(3);
        bookData.setIsbn("1234567890");
        bookData.setPublicationYear(2007);

        ResponseEntity<String> response = restTemplate.postForEntity(url, bookData, String.class);
        assertEquals("Book Updated Successfully", response.getBody());
    }
    
    @Test 
    public void testUpdateBookIsbnError() {
        String url = "http://localhost:" + port + "/book/update";
        BookData bookData = new BookData();
        bookData.setId(6);
        bookData.setTitle("Time Travel 3");
        bookData.setAuthorId(3);
        bookData.setIsbn("1234567890");
        bookData.setPublicationYear(2007);

        ResponseEntity<String> response = restTemplate.postForEntity(url, bookData, String.class);
        assertEquals("Errors:\nBook already present with the updated ISBN number\n", response.getBody());
    }
    
    @Test 
    public void testUpdateBookAuthorError() {
        String url = "http://localhost:" + port + "/book/update";
        BookData bookData = new BookData();
        bookData.setId(5);
        bookData.setTitle("Time Travel 3");
        bookData.setAuthorId(145);
        bookData.setIsbn("1234567820");
        bookData.setPublicationYear(2007);

        ResponseEntity<String> response = restTemplate.postForEntity(url, bookData, String.class);
        assertEquals("Errors:\nInvalid Author Id\n", response.getBody());
    }
    
    @Test 
    public void testUpdateBookInvalidBookError() {
        String url = "http://localhost:" + port + "/book/update";
        BookData bookData = new BookData();
        bookData.setId(145);
        bookData.setTitle("Time Travel 3");
        bookData.setAuthorId(4);
        bookData.setIsbn("1234567820");
        bookData.setPublicationYear(2007);

        ResponseEntity<String> response = restTemplate.postForEntity(url, bookData, String.class);
        System.out.println(response.getBody());
        assertEquals("Error:\n Book not available in the records with given book id", response.getBody());
    }
    
    @Test 
    public void testDeleteBookSuccess() {
        String url = "http://localhost:" + port + "/book/delete?id=7";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals("Book removed successfully", response.getBody());
    }
    
    @Test 
    public void testDeleteBookNotExistsOrRented() {
        String url = "http://localhost:" + port + "/book/delete?id=20";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals("Error:\n Book not exists or rented", response.getBody());
    }
	
	@Test
	public void testGetAllBookByAuthor() {
		String url = "http://localhost:" + port + "/book/search?authorId=2";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertTrue(response.getBody()
				.contains("{\"id\":3,\"title\":\"The Great novel 5\",\"author\":{\"id\":2,\"name\":\"Premchand\","
						+ "\"biography\":null},\"isbn\":\"1234567891\",\"publicationYear\":2024,"
						+ "\"available\":false},{\"id\":10,\"title\":\"Title of book\",\"author\":{\"id\":2,"
						+ "\"name\":\"Premchand\",\"biography\":null},\"isbn\":\"7896565896\","
						+ "\"publicationYear\":2021,\"available\":true}"));
	}
	
	@Test
	public void testGetAllAvailableBook() {
		String url = "http://localhost:" + port + "/book/all/available";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		System.out.println(response.getBody());
		assertTrue(!response.getBody()
				.contains("\"available\":false"));
	}
	
	@Test
	public void testGetAllRentedBook() {
		String url = "http://localhost:" + port + "/book/all/rented";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		System.out.println(response.getBody());
		assertTrue(!response.getBody()
				.contains("\"available\":true"));
	}
}