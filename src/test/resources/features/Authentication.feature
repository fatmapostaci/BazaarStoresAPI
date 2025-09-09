@Feature: Authentication

@Login
Scenario: Get Token


@Logout
Scenario: Logout
Given user logs out
Then user verifies status code 200