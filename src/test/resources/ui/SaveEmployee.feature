Feature: Save Employee

  Scenario: Save employee when user clicks on Save Button
    Given the system has following employees in database
      | Id   | Name         | Company |
      | 1001 | Larry Page   | Google  |
      | 1002 | Kyle Simpson | Oracle  |
    And   the user enters following employee details
      | Id   | Name      | Company |
      | 1003 | Tom Paine | Intel   |
    When user clicks on Add Button
    Then toaster notification is displayed with "Tom Paine saved successfully" message