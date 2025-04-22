# 📚 Bookstore REST API

A RESTful API for a Bookstore application built with Java and JAX-RS. Supports CRUD operations for books, authors, customers, carts, and orders using in-memory storage. All endpoints are designed following REST principles and tested with Postman.

---

## 🚀 Features

- Full CRUD operations for:
  - 📘 Books
  - ✍️ Authors
  - 👤 Customers
  - 🛒 Shopping Carts
  - 🧾 Orders
- Exception handling with custom mappers
- Follows JAX-RS standards and annotations
- Tested thoroughly using Postman collections
- Uses in-memory data structures (no external DB)

---

## 🧰 Tech Stack

- **Java 11+**
- **JAX-RS (Jersey or RESTEasy)**
- **JSON** for request/response
- **Postman** for testing
- **Maven** (if applicable)

---

## 📁 Project Structure

src/ │ 
├── resources/ 
  │ ├── BookResource.java 
  │ ├── AuthorResource.java 
  │ ├── CustomerResource.java
  │ ├── CartResource.java 
  │ └── OrderResource.java 
│ ├── models/ 
  │ ├── Book.java 
  │ ├── Author.java 
  │ ├── Customer.java
  │ └── Order.java 
│ ├── exceptions/
  │ ├── BookNotFoundException.java
  │ ├── ExceptionMapperConfig.java 
  │ └── ... 
│ └── Main.java


---

## 📦 Setup Instructions


### Prerequisites

- Java 11 or higher
- Maven installed (optional)
- Postman

### Running the Application

1. Clone the repo:
   ```bash
   git clone https://github.com/ThiyumiS/BookStore_Application.git
