@txn
Feature: Save an Employee

  Scenario: API call to save an Employee
    Given the system has following employees
      | Id   | Name         | Company |
      | 1001 | Larry Page   | Google  |
      | 1002 | Kyle Simpson | Oracle  |
    When  the user saves following employees
      | Id   | Name      | Company |
      | 1003 | Tom Paine | Intel   |
    And   the user views list of employees
    Then  the following employees are displayed
      | Id   | Name         | Company |
      | 1001 | Larry Page   | Google  |
      | 1002 | Kyle Simpson | Oracle  |
      | 1003 | Tom Paine    | Intel   |