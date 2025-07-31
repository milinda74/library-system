REST Endpoints
Method	    -Endpoint	              -Description
======================================================
POST	     /api/borrowers	        -Register new borrower <br>
POST	     /api/books	            -Register new book <br>
GET	       /api/books	            -Get all books <br>
POST	     /api/borrow	          -Borrow a book <br> 
POST	     /api/return	          -Return a book <br>

<br>

Database Implementation
    Database Choice: PostgreSQL (ACID-compliant RDBMS) <br>
    Justification: <br>
        Ensures data integrity for financial systems <br>
        Supports complex relationships (borrowers/books) <br>
        Environment configurable (dev/prod) <br>
    Configuration:<br>
# application-dev.properties <br>
spring.datasource.url=jdbc:h2:mem:librarydb <br>
spring.jpa.hibernate.ddl-auto=update <br>

# application-prod.properties <br>
spring.datasource.url=jdbc:postgresql://prod-db/library <br>
spring.datasource.username=${DB_USER} <br>
spring.datasource.password=${DB_PASS} <br>

![7606c01](https://github.com/user-attachments/assets/f2989bcd-5d15-472b-8626-2b3f1e0437c9)



