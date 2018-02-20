Feature: Display Employees

  Scenario: Viewing employees when user clicks on GET Employees
    Given the system has following employees in database
      | Id   | Name         | Company |
      | 1001 | Larry Page   | Google  |
      | 1002 | Kyle Simpson | Oracle  |
    And   the user clicks on GET employees button
    Then  the following employees are displayed in the page
      | Id   | Name         | Company |
      | 1001 | Larry Page   | Google  |
      | 1002 | Kyle Simpson | Oracle  |