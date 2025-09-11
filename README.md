BazaarStoresAPI

A BDD-style REST API test automation framework for the Bazaar Stores API.

This project validates key business flows such as authentication, store management, user management, products, favorites, and customer carts.

📌 Features
BDD (Behavior Driven Development) with Cucumber and Gherkin feature files

Step Definitions in Java linking feature steps to automation logic

Reusable JSON files for request payloads and expected responses

Multiple Runners for failed or team-based execution (RunnerFatma, FailedRunner, etc.)

Base URL managed separately for flexibility across environments

Cucumber Reporting for rich, interactive test results


Covers core Bazaar modules:
Authentication
Stores
Users
Products
Favorites
Customer Cart

🛠️ Technology Stack

Language: Java
BDD Framework: Cucumber (Gherkin syntax)
Test Execution: TestNG
Reporting: Allure Reports
Build Tool: Maven
JSON Handling: Jackson / Rest-Assured
Version Control: GitHub


📂 Project Structure
src/test/java
├── base_urls/                # Base URL setup for environments
├── json_files/               # Request/response JSON payloads
├── runners/                  # Runner classes for Cucumber/TestNG
├── stepdefinitions/          # Step Definitions (Given/When/Then in Java)
└── utilities/                # Utility classes (helpers, config, etc.)
src/test/resources
├── features/                 # Gherkin feature files
└── cucumber.properties       # Cucumber config


⚙️ Setup & Installation

Clone the repository:
git clone https://github.com/fatmapostaci/BazaarStoresAPI.git
cd BazaarStoresAPI

Install dependencies:
mvn clean install

Update configuration in:
configuration.properties (base URI, credentials, environment variables)
JSON payloads in src/test/java/json_files/

🧪 Example Feature (Stores.feature)
Feature: Store operations

  Scenario: Get all stores
    Given the user sets the stores endpoint
    When the user sends a GET request
    Then the response status code should be 200
    And the response body should contain a list of stores

  Scenario: Create a store with valid data
    Given the user has a valid store JSON payload
    When the user sends a POST request
    Then the response status code should be 201
    And the response body should include the new store id

