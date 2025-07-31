REST Endpoints:<br>
Borrower Endpoints:<br>
POST   /api/borrowers      - Register borrower (201 Created)<br>
GET    /api/borrowers/{id} - Get borrower by ID (200 OK)<br>
GET    /api/borrowers      - Get all borrowers (200 OK)<br>
PUT    /api/borrowers/{id} - Update borrower (200 OK)<br>
DELETE /api/borrowers/{id} - Delete borrower (204 No Content)<br><br>
Book Endpoints:<br>
POST /api/books - Register book (201 Created)<br>
GET  /api/books - Get all books (200 OK)<br><br>
Borrow/Return Endpoints:<br>
POST /api/borrow      - Borrow book (201 Created)<br>
POST /api/borrow/return - Return book (200 OK)<br>

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

Documentation for how to use the API:<br>
<img width="1351" height="678" alt="Screenshot 2025-07-31 121750" src="https://github.com/user-attachments/assets/74e26dd3-1246-4609-8917-d2bb0ba7eb88" />

<img width="1353" height="671" alt="Screenshot 2025-07-31 122157" src="https://github.com/user-attachments/assets/142d5200-cc5f-4bf2-a935-f39b73e8a98a" />


Testing:<br>
    Unit tests for services (JUnit 5 + Mockito)<br>
    Integration tests for controllers (SpringBootTest)<br>
<img width="1338" height="715" alt="Screenshot 2025-07-31 064111" src="https://github.com/user-attachments/assets/c78f0938-9a55-4015-85f1-1ee5efc19479" />
<br>
This test suite provides:<br>
    1. 80-90% code coverage<br>
    2. Validation of all business rules<br>
    3. API contract verification<br>
    4. Database interaction testing<br>
    5. Error handling verification<br>
    6. Performance under concurrent access (in integration tests)<br>
The tests ensure the system meets all requirements specified in the task while maintaining robustness and reliability.<br>



Key Assumptions:<br>
    1. Borrower email must be unique<br>
    2. ISBN format validation not implemented (assume valid input)<br>
    3. No authentication/authorization (per task requirements)<br>
    4. Automatic book availability status based on borrow records<br>
    5. Soft deletion not implemented<br>
    6. Pagination not implemented for book listings<br>
    7. Timezone handling uses server default<br>



