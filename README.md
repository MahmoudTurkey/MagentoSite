# Magento Automation Testing Project

This project automates end-to-end user scenarios for the [Magento demo website](https://magento.softwaretestingboard.com/) using:

- ✪ Selenium WebDriver
- ✅ TestNG for test management
- ✨ Allure for reporting
- ☕ Java 17+ (compatible with JDK 21)
- 📦 Maven as the build tool

---

## 📁 Project Structure

```
MagentoTest/
│
├── src/
│   ├── main/java/pages/        # Page Object classes
│   └── test/java/tests/        # Test classes (TestNG)
│
├── testng.xml                  # TestNG suite
├── pom.xml                     # Maven dependencies
└── README.md
```

---

## ⚙️ Setup Instructions

1. ✅ Install Java JDK 17+ and configure `JAVA_HOME`.
2. ✅ Install Maven and verify via: `mvn -v`
3. ✅ Clone the project:

```bash
git clone https://github.com/your-username/MagentoTest.git
cd MagentoTest
```

---

## 🚀 Run Tests

To run all tests defined in `testng.xml`:

```bash
mvn clean test
```

---

## 📊 Generate Allure Report

After test execution:

```bash
allure serve allure-results
```

> Make sure Allure CLI is installed and added to system PATH: [Installation Guide](https://docs.qameta.io/allure/#_installing_a_commandline)

---

## 💪 Sample Test Scenarios

- 🔎 Search for product
- 📒 Add two products to cart, remove one
- 💳 Complete checkout process

---

## 🐞 Bug Reporting & 🔐 Security Testing

### Bug Reporting

- During automated test execution, the following issues were observed:
  - ⚠️ **Success message not always appearing** after adding an item to the cart due to slow loading.
  - ⚠️ **UI element overlap (ElementClickInterceptedException)** when attempting to click the "Place Order" button.

> These issues were handled using `WebDriverWait` and fallback `JavascriptExecutor` when needed.

### Security Testing

Basic client-side security validations were performed:

- ✅ **Input Validation**:

  - Injected invalid input (HTML/JS) into email and address fields.
  - Result: Inputs were properly sanitized and not executed.

- ✅ **XSS Testing**:

  - Payload example:
    ```html
    <script>alert('XSS')</script>
    ```
  - No alert was triggered, indicating proper output encoding or input sanitization.

- ✅ **SQL Injection Simulation**:

  - Input: `admin' OR '1'='1` tested on login fields.
  - Result: Input was rejected; no unusual behavior was detected.

> 🔒 No critical vulnerabilities found. For deeper analysis, using tools like OWASP ZAP or Burp Suite is recommended.

---

## 🧾 Security Checklist Summary

| Area                       | Test Applied                       | Result                |
| -------------------------- | ---------------------------------- | --------------------- |
| Input Validation           | Injected special characters        | ✅ Properly sanitized  |
| XSS (Cross-site Scripting) | `<script>alert('XSS')</script>`    | ✅ No script executed  |
| SQL Injection              | `admin' OR '1'='1` in login fields | ✅ Input rejected      |
| HTTPS                      | Connection verified                | ✅ Site uses HTTPS     |
| Client-side validation     | Missing fields/forms               | ✅ Validation enforced |

> For production-grade security, run dynamic scans via tools like OWASP ZAP or Burp Suite.

---

## 🐞 Bug Summary Table

| ID  | Description                                               | Severity | Status             |
| --- | --------------------------------------------------------- | -------- | ------------------ |
| B01 | Success message delay after "Add to Cart"                 | Medium   | Handled via waits  |
| B02 | "Place Order" button not clickable due to overlay element | High     | Bypassed with JS   |
| B03 | Some test flakiness due to dynamic product data           | Low      | Intermittent issue |

---

## 📬 Author

**Mahmoud Hassan Elsayed Turky**\
💼 QA Engineer | Automation Specialist\
📧 [mahmoudtorki78@gmail.com](mailto\:mahmoudtorki78@gmail.com)

