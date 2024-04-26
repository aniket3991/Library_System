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

import com.demo.library.entity.Author;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerTest {

	@LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Test 
    public void testAddAuthorSuccess() {
        String url = "http://localhost:" + port + "/author/new";
        Author author = new Author();
        author.setName("Author Name");

        ResponseEntity<String> response = restTemplate.postForEntity(url, author, String.class);
        assertEquals("Author added Successfully", response.getBody());
    }

    @Test
    public void testAddAuthorNotBlankValidationError() {
        String url = "http://localhost:" + port + "/author/new";
        Author author = new Author();
        
        ResponseEntity<String> response = restTemplate.postForEntity(url, author, String.class);
        assertEquals("Errors:\n"
        		+ "Author name should not be blank\n", response.getBody());
    }
    
    @Test
    public void testAddAuthorMinLengthValidationError() {
        String url = "http://localhost:" + port + "/author/new";
        Author author = new Author();
        author.setName("an");
        
        ResponseEntity<String> response = restTemplate.postForEntity(url, author, String.class);
        assertEquals("Errors:\n"
        		+ "Author name must be between 3 to 100 characters\n", response.getBody());
    }
    
    @Test 
    public void testSearchAuthorSuccess() {
        String url = "http://localhost:" + port + "/author/search?id=2";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals("{\"id\":2,\"name\":\"Premchand\",\"biography\":null}", response.getBody());
    }
    
    @Test
    public void testSearchAuthorsNotFound() {
        String url = "http://localhost:" + port + "/author/search?id=4562";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertNull(response.getBody());
    }
    
    @Test 
    public void testGetAllAuthorSuccess() {
        String url = "http://localhost:" + port + "/author/all";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertTrue(response.getBody().contains("{\"id\":2,\"name\":\"Premchand\",\"biography\":null}"));
    }
    
    @Test 
    public void testUpdateAuthorSuccess() {
        String url = "http://localhost:" + port + "/author/update";
        Author author = new Author(3, "Aniket", "Writer");

        ResponseEntity<String> response = restTemplate.postForEntity(url, author, String.class);
        assertEquals("Updated Successfully", response.getBody());
    }
    
    @Test 
    public void testUpdateAuthorNotExists() {
        String url = "http://localhost:" + port + "/author/update";
        Author author = new Author(145, "Aniket", "Writer");

        ResponseEntity<String> response = restTemplate.postForEntity(url, author, String.class);
        assertEquals("Error:\n Author does not exist", response.getBody());
    }
    
    @Test 
    public void testDeleteAuthorSuccess() {
        String url = "http://localhost:" + port + "/author/delete?id=11";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals("Author removed successfully", response.getBody());
    }
    
    @Test 
    public void testDeleteAuthorNotExists() {
        String url = "http://localhost:" + port + "/author/delete?id=20";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals("Error:\n Author not exists please check id again", response.getBody());
    }
}
