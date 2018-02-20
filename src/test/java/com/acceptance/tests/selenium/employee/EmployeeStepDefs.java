package com.acceptance.tests.selenium.employee;

import com.acceptance.tests.selenium.SharedWebDriver;
import com.demo.dto.EmployeeDTO;
import com.demo.entity.Employee;
import com.demo.entity.EmployeeRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EmployeeStepDefs {
    private WebDriver webDriver = SharedWebDriver.getInstance().getDriver();
    private EmployeeRepository employeeRepository;
    private EmployeeDTO addedEmployee;

    public EmployeeStepDefs(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @When("^the user clicks on GET employees button$")
    public void theUserClicksOnGETEmployeesButton() throws Throwable {
        webDriver.findElement(By.id("get-employees")).click();
        SharedWebDriver.getInstance().waitForAngular();
    }

    @Given("^the system has following employees in database$")
    public void theSystemHasFollowingEmployeesInDatabase(List<EmployeeDTO> employees) throws Throwable {
        employees.forEach(employeeDTO -> employeeRepository.save(getEmployee(employeeDTO)));
    }

    private Employee getEmployee(EmployeeDTO employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setCompany(employeeDto.getCompany());
        return employee;
    }

    @Then("^the following employees are displayed in the page$")
    public void theFollowingEmployeesAreDisplayedInThePage(List<EmployeeDTO> employees) throws Throwable {
        List<WebElement> actualEmployees = webDriver.findElements(By.id("employee-details"));
        List<EmployeeDTO> actualEmployeeDetails = actualEmployees.stream()
                .map(actualEmployee -> {
                    EmployeeDTO employeeDTO = new EmployeeDTO();
                    employeeDTO.setId(actualEmployee.findElement(By.id("employee-id")).getText());
                    employeeDTO.setName(actualEmployee.findElement(By.id("employee-name")).getText());
                    employeeDTO.setCompany(actualEmployee.findElement(By.id("employee-company")).getText());
                    return employeeDTO;
                })
                .collect(Collectors.toList());
        assertThat(actualEmployeeDetails, containsInAnyOrder(employees.toArray()));
    }

    @And("^the user enters following employee details$")
    public void theUserEntersFollowingEmployeeDetails(List<EmployeeDTO> employees) throws Throwable {
        this.addedEmployee = employees.get(0);
        employees.forEach(employee -> {
            webDriver.findElement(By.id("input-id")).sendKeys(employee.getId());
            webDriver.findElement(By.id("input-name")).sendKeys(employee.getName());
            webDriver.findElement(By.id("input-company")).sendKeys(employee.getCompany());
        });

    }

    @When("^user clicks on Add Button$")
    public void userClicksOnAddButton() throws Throwable {
        webDriver.findElement(By.id("add-employee")).click();
        SharedWebDriver.getInstance().waitForAngular();
    }

    @Then("^toaster notification is displayed with \"([^\"]*)\" message$")
    public void toasterNotificationIsDisplayedWithMessage(String toasterMessage) throws Throwable {
      assertThat(this.addedEmployee.getName() + " saved successfully", is(toasterMessage));
    }
}
