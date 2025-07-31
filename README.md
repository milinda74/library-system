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

<img width="1348" height="636" alt="Screenshot 2025-07-31 063226" src="https://github.com/user-attachments/assets/598e1f90-6d0e-4026-96d8-24f7e42693b8" />

<img width="1338" height="715" alt="Screenshot 2025-07-31 064111" src="https://github.com/user-attachments/assets/c78f0938-9a55-4015-85f1-1ee5efc19479" />





