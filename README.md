# Book Management API

## Description
A simple RESTful API for managing a collection of books. This project allows users to perform CRUD operations on books, rate them, and filter the list by various criteria. The API uses an H2 in-memory database for quick setup and testing.

## Requirements
- Java 17
- Maven

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/MariusDevelops/sa-api-task.git
cd sa-api-task
```

### Run the Project
```bash
./mvnw spring-boot:run
```

# Access H2 Database
- Navigate to: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave blank)

Write simple SQL script in empty field ```SELECT * FROM books;``` and press run:


# Test the API

Open your browser and navigate to: `http://localhost:8080/api/books`

### Testing via Browser (GET Requests)
You can use your browser to test **GET** requests. Simply navigate to the following URLs:

- **Get all books**: [http://localhost:8080/api/books](http://localhost:8080/api/books)
- **Get a specific book**: [http://localhost:8080/api/books/1](http://localhost:8080/api/books/1)
- **Filter by author**: [http://localhost:8080/api/books?author=George%20Orwell](http://localhost:8080/api/books?author=George%20Orwell)
- **Filter by title**: [http://localhost:8080/api/books?title=Dune](http://localhost:8080/api/books?title=Dune)
- **Filter by year**: [http://localhost:8080/api/books?year=1954](http://localhost:8080/api/books?year=1954)
- **Filter by rating**: [http://localhost:8080/api/books?rating=5](http://localhost:8080/api/books?rating=5)

### Testing via Postman (for POST and PUT Requests):

PUT http://localhost:8080/api/books/{id}/rate
```bash
Body (raw, JSON):
{
"rating": 4.5
}
```

#### Create a Book (if necessary):

POST http://localhost:8080/api/books
```bash
Body (raw, JSON):
{
    "title": "New Book Title",
    "author": "Author Name",
    "year": 2022,
    "rating": 4.0
}
```

# Run Tests

```bash
./mvnw test
```

# License
This project is open-source and free to use.