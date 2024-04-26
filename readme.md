# System Requirements

Before setting up and running the Library System, ensure your system meets the following requirements:

- **Java Development Kit (JDK) 17**: JDK 17 is used for this project.

- **Spring Boot 3.x**: Download and install Spring Boot 3.x on your machine. You can download it from the official [Spring Boot Website](https://spring.io/projects/spring-boot).

- **MySQL Database**: Ensure you have MySQL installed on your machine. The Library System uses MySQL as its database. You can download MySQL from the official [MySQL website](https://www.mysql.com/).

# Setup Instructions

Follow these step-by-step instructions to set up and run the Library System on your local machine.

## 1. Clone the Repository

- Clone the repository to your local machine using Git:

```git
git clone https://github.com/aniket3991/Library_System.git
```
## 2. Import Project into IDE

- Open your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
- Import the project into your IDE as a Maven project.

## 3. Enable Annotation Processing for Lombok
### If using IntelliJ IDEA:
- Go to File -> Settings.
- Navigate to Build, Execution, Deployment -> Compiler -> Annotation Processors.
- Check the box for Enable annotation processing.

### If using Eclipse:
- Ensure you have installed the Lombok plugin.
- Enable annotation processing in project settings.

## 4. Download Dependencies
- Run the Maven pom.xml file to download project dependencies.

## 5. Create MySQL Database
- Ensure you have MySQL installed.
- Open a MySQL command line or GUI client.
- Run the following command to create a database named library:

```sql
CREATE DATABASE library;
```

## 6. Configure Database Credentials
- Open the application.properties file located in the src/main/resources directory of the project.
- Update the database connection settings (URL, username, password) to match your MySQL database credentials:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/library
spring.datasource.username=your-username
spring.datasource.password=your-password
```

## 7. Run the Application
- Run the application from your IDE.
- By following these steps, you'll be able to set up and run the Library System on your local machine.


# Testing Endpoints

After setting up and running the Book Rental System, you can test the endpoints using sample requests.

## Sample Requests

### Add new Author

To create a new author, send a POST request to the following endpoint with the author's details in the request body:

```http
POST /author/new

{
	"name" : "Author Name",
	"biography" : "Biography of author"
}
```

### Update an Author

To update an author, send a POST request to the following endpoint with the author's details in the request body:

```http
POST /author/update

{
	"id" : {id},
	"name" : "Author Name",
	"biography" : "Biography of author"
}
```

### Get an Author

To retrieve details of a single author by their ID, send a GET request to the following endpoint:

```http
GET /author/search?id={authorId}
```

### Get All Author

To retrieve all author from the system, send a GET request to the following endpoint:

```http
GET /author/all
```

### Add new Book

To create a new Book, send a POST request to the following endpoint with the Book's details in the request body:

```http
POST /book/new

{
	"title" : "Book Title",
	"authorId" : {authorId},
	"publicationYear" : {publicationYear},
	"isbn" : "valid-isbn-number"
}
```

### Update an Book

To update a book, send a POST request to the following endpoint with the book's details in the request body:

```http
POST /book/update

{
	"id" : {id},
	"title" : "Book Title",
	"authorId" : {authorId},
	"publicationYear" : {publicationYear},
	"isbn" : "valid-isbn-number"
	"isAvailable" : {true/false}
}
```

### Get an Book

To retrieve details of a single book by their ID, send a GET request to the following endpoint:

```http
GET /book/search?id={bookId}
```

### Get All Book

To retrieve all books from the system, send a GET request to the following endpoint:

```http
GET /book/all
```

### Get All Available Book

To retrieve all books available for rent from the system, send a GET request to the following endpoint:

```http
GET /book/all/available
```

### Get All Rented Book

To retrieve all rented books from the system, send a GET request to the following endpoint:

```http
GET /book/all/rented
```

### Rent a Book and create Rental record

To create a rental record of rented book, sent a POST request to the following endpoint:

```http
POST /rental/rent
{
	"bookId" : {bookId},
	"renterName" : "Renter Name",
	"rentedDays" : {numberOfdaysForRent}
}
```

### Return a book

To return a rented book, send a GET request to the following endpoint with rental Id:

```http
GET /rental/return?id={rentalId}
```

### Get all rental data 

To retrive all rental data, sent a GET request to the following endpoint:

```http
GET /rental/all
```