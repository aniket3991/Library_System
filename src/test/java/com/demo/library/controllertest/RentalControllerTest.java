package com.demo.library.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.demo.library.request.RentalData;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentalControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testRentBookSuccess() {
		String url = "http://localhost:" + port + "/rental/rent";
		RentalData rentalData = new RentalData();
		rentalData.setBookId(11);
		rentalData.setRentedDays(10);
		rentalData.setRenterName("Abhinav");

		ResponseEntity<String> response = restTemplate.postForEntity(url, rentalData, String.class);
		assertEquals("Book Successfully rented for " + rentalData.getRentedDays() + " Days", response.getBody());
	}
	
	@Test
	public void testRentBookInvalidBook() {
		String url = "http://localhost:" + port + "/rental/rent";
		RentalData rentalData = new RentalData();
		rentalData.setBookId(145);
		rentalData.setRentedDays(10);
		rentalData.setRenterName("Abhinav");

		ResponseEntity<String> response = restTemplate.postForEntity(url, rentalData, String.class);
		assertEquals("Error:\n Book is already rented or not available at this movement", response.getBody());
	}
	
	@Test
	public void testRentBookInvalidMinRentedDays() {
		String url = "http://localhost:" + port + "/rental/rent";
		RentalData rentalData = new RentalData();
		rentalData.setBookId(11);
		rentalData.setRentedDays(0);
		rentalData.setRenterName("Abhinav");

		ResponseEntity<String> response = restTemplate.postForEntity(url, rentalData, String.class);
		assertEquals("Errors:\nMinimum rented days should be 1\n", response.getBody());
	}
	
	@Test
	public void testRentBookInvalidMaxRentedDays() {
		String url = "http://localhost:" + port + "/rental/rent";
		RentalData rentalData = new RentalData();
		rentalData.setBookId(11);
		rentalData.setRentedDays(61);
		rentalData.setRenterName("Abhinav");

		ResponseEntity<String> response = restTemplate.postForEntity(url, rentalData, String.class);
		System.out.println(response.getBody());
		assertEquals("Errors:\nCan not rent a book for more than 60 days\n", response.getBody());
	}
	
	@Test
	public void testRentBookInvalidRenterName() {
		String url = "http://localhost:" + port + "/rental/rent";
		RentalData rentalData = new RentalData();
		rentalData.setBookId(11);
		rentalData.setRentedDays(10);
		rentalData.setRenterName("Ab");

		ResponseEntity<String> response = restTemplate.postForEntity(url, rentalData, String.class);
		System.out.println(response.getBody());
		assertEquals("Errors:\nRenter name must be between 3 to 100 characters\n", response.getBody());
	}
	
	@Test
	public void testReturnBookSuccess() {
		String url = "http://localhost:" + port + "/rental/return?id=6";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertEquals("Returned Successfully", response.getBody());
	}
	
	@Test
	public void testReturnBookErrorBookNotExistsOrReturned() {
		String url = "http://localhost:" + port + "/rental/return?id=145";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertEquals("Error:\n Incorrect Rental Id or Book is alreay returned", response.getBody());
	}
	
	@Test
	public void testGetAllRentalRecords() {
		String url = "http://localhost:" + port + "/rental/all";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertTrue(response.getBody()
				.contains("{\"id\":1,\"book\":{\"id\":5,\"title\":\"Time Travel 3\",\"author\":{\"id\":3,"
						+ "\"name\":\"Aniket\",\"biography\":\"Writer\"},\"isbn\":\"1234567890\","
						+ "\"publicationYear\":2007,\"available\":true},\"renterName\":\"Aniket Mishra\","
						+ "\"rentalDate\":\"2024-04-16\",\"returnDate\":\"2024-04-26\",\"rentedDays\":10},"
						+ "{\"id\":2,\"book\":{\"id\":3,\"title\":\"The Great novel 5\",\"author\":{\"id\":2,"
						+ "\"name\":\"Premchand\",\"biography\":null},\"isbn\":\"1234567891\","
						+ "\"publicationYear\":2024,\"available\":false},\"renterName\":\"Ajay\","
						+ "\"rentalDate\":\"2024-04-02\",\"returnDate\":\"2024-04-26\",\"rentedDays\":5}"));
	}
}
