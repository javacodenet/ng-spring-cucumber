package com.acceptance.tests.cucumber.employee;

import com.demo.controllers.EmployeeRestController;
import com.demo.dto.EmployeeDTO;
import com.demo.entity.Employee;
import com.demo.entity.EmployeeRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class EmployeeStepDefs {

    private EmployeeRepository employeeRepository;

    private EmployeeRestController employeeRestController;

    private List<EmployeeDTO> employees;

    public EmployeeStepDefs(EmployeeRepository employeeRepository, EmployeeRestController employeeRestController) {
        this.employeeRepository = employeeRepository;
        this.employeeRestController = employeeRestController;
    }

    private Employee getEmployee(EmployeeDTO employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setCompany(employeeDto.getCompany());
        return employee;
    }

    @Given("^the system has following employees$")
    public void theSystemHasFollowingEmployees(List<EmployeeDTO> employees) throws Throwable {
        employees.forEach(employeeDTO -> employeeRepository.save(getEmployee(employeeDTO)));
    }

    @When("^the user views list of employees$")
    public void theUserViewsListOfEmployees() throws Throwable {
        employees = employeeRestController.list();
    }

    @Then("^the following employees are displayed$")
    public void theFollowingEmployeesAreDisplayed(List<EmployeeDTO> employees) throws Throwable {
        assertThat(this.employees, containsInAnyOrder(employees.toArray()));
    }

    @When("^the user saves following employees$")
    public void theUserSavesFollowingEmployees(List<EmployeeDTO> employees) throws Throwable {
        employeeRestController.saveEmployee(employees.get(0));
    }
}
