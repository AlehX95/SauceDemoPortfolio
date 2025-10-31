# SauceDemo Automation Testing Project

## Overview

This project automates functional tests for the [SauceDemo](https://www.saucedemo.com/) web application using **Selenium WebDriver** with **Java** and **JUnit**.  
It is designed as a modular and scalable framework following the **Page Object Model (POM)** pattern, integrating **ExtentReports** for reporting and **Log4j** for logging, and a **MySQL database** for dynamic user data retrieval.


The project demonstrates a full end-to-end testing workflow — from login and product interactions to checkout — making it ideal for QA automation portfolios or CI/CD integration.

---

## Project Structure

```
SauceDemoPortfolio/
│
├── base/ # Base classes (browser setup, waits, driver handling)
├── pages/ # Page Object Model classes (SignIn, Products, Cart, etc.)
├── tests/ # JUnit test classes organized by feature
├── utils/ # Utilities (ExtentReports, DB connection, helpers)
├── src/test/resources/
│ ├── config/ # config.properties (DB credentials)
│ └── database/ # saucedb_example.sql (optional DB structure)
├── reports/ # Auto-generated ExtentReports HTML files
└── pom.xml # Maven dependencies and project configuration
```

---

## Key Features

- **Page Object Model (POM)** – modular and maintainable test design  
- **Selenium WebDriver** – cross-browser functional automation  
- **JUnit 4** – test structure and assertions  
- **ExtentReports** – detailed and visual HTML reports  
- **Log4j** – structured logging for test monitoring  
- **Database Integration** – reads credentials from a MySQL database  
- **Secure Configuration** – credentials stored in `config.properties` (not pushed to GitHub)
- **CI/CD Ready** – compatible with Jenkins for automated execution  

---

## Technologies Used
| Category        | Tools / Libraries               |
|-----------------|---------------------------------|
| Programming     | Java 19                         |
| Automation      | Selenium WebDriver              |
| Testing         | JUnit 4                         |
| Reporting       | ExtentReports 5.1.1             |
| Logging         | Log4j2 2.23.1                   |
| ORM / Data      | MySQL JDBC                      |
| Build Tool      | Maven                           |
| CI/CD           | Jenkins (optional)              |
---

## How to Run Tests

1. **Clone the Repository**
   ```bash
   git clone https://github.com/AlehX95/SauceDemoPortfolio.git
   cd SauceDemoPortfolio
   ```

2. **Install Dependencies**
   ```bash
   mvn clean install
   ```

3. **Run Tests**
   ```bash
   mvn test
   ```

4. **View Reports**
   - Open the generated HTML report in `/reports/ExtentReport.html`

---

## Example Test Flow

**Login Test (`SignIn_Test`)**
1. Fetch user credentials from the database  
2. Log in using `SignInPage`  
3. Validate that the home page is displayed  
4. Log the results using ExtentReports  

**Checkout Test (`CheckoutProcessPage_Test`)**
1. Add multiple products to the cart  
2. Proceed to checkout  
3. Complete the form and verify the confirmation page  

---

## Continuous Integration

This project can be easily integrated into **Jenkins** or any CI/CD pipeline:

- Trigger builds automatically on code pushes  
- Generate ExtentReports after each run  
- Archive HTML reports as Jenkins build artifacts  

---

## Author

**Ivan Alejandro Rosas Garcia**  
QA Automation Engineer | Based in Ireland  

[LinkedIn](https://www.linkedin.com/in/ivan-alejandro-rosas-garcia-3b57a127b?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app)  
[GitHub](https://github.com/AlehX95)  
alexrosasgarcia69@gmail.com
