@txn
Feature: List of Employees

  Scenario: API call to retrieve list of employees
    Given the system has following employees
      | Id   | Name         | Company |
      | 1001 | Larry Page   | Google  |
      | 1002 | Kyle Simpson | Oracle  |
    When  the user views list of employees
    Then  the following employees are displayed
      | Id   | Name         | Company |
      | 1001 | Larry Page   | Google  |
      | 1002 | Kyle Simpson | Oracle  |