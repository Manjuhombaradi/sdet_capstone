# NSE Stock Automation

This project automates the verification of stock information on the NSE India website using Java, Selenium, and TestNG.

## Prerequisites

- Java JDK 11 or higher
- Maven
- Chrome Browser (or Firefox/Edge)

## Project Structure

- `src/main/java`: Contains Page Objects (`NSEHomePage`, `StockDetailsPage`) and Utilities (`ExtentManager`).
- `src/test/java`: Contains Test Classes (`BaseTest`, `StockVerificationTest`).
- `src/test/resources`: Contains `log4j2.xml` for logging configuration.
- `testng.xml`: TestNG suite configuration.

## How to Run

1.  **Clone the repository** (if applicable) or navigate to the project directory.
2.  **Install Dependencies**:
    ```bash
    mvn clean install -DskipTests
    ```
3.  **Run Tests**:
    ```bash
    mvn test
    ```
    Or run using the `testng.xml` file:
    ```bash
    mvn test -DsuiteXmlFile=testng.xml
    ```

## Reports

- **Extent Reports**: Generated in `test-output/` directory as `Test-Report-<timestamp>.html`.
- **Logs**: Console logs are generated using Log4j2.

## Test Data

The test `StockVerificationTest` uses a DataProvider to test multiple stocks:
- RCOM
- TATAMOTORS
- RELIANCE

You can modify the `getStockNames` method in `StockVerificationTest.java` to add more stocks.

## Recent Updates

- **Fixed Search Locator**: Updated the search input locator in `NSEHomePage.java` to use a CSS selector based on the placeholder text, as the ID `header-search-input` was removed from the NSE website.

## Test Scenarios

The `StockVerificationTest` class now includes:
1.  **Data Driven Test (`verifyStockDetails`)**: Verifies stock details for multiple stocks (RCOM, TATAMOTORS, RELIANCE).
2.  **Positive Test (`verifyStockPositive`)**: Explicitly checks for a valid stock (SBIN) and asserts that data is retrieved.
3.  **Negative Test (`verifyStockNegative`)**: **INTENTIONALLY FAILS**. This test is designed to demonstrate a failure scenario by asserting an incorrect condition.
