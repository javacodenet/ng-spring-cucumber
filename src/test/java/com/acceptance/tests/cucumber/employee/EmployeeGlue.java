/*
package com.acceptance.tests.cucumber.employee;


import com.demo.dto.EmployeeDTO;
import com.demo.entity.Employee;
import com.demo.entity.EmployeeRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("cucumber-glue")
public class EmployeeGlue {
    private EmployeeRepository employeeRepository;

    public EmployeeGlue(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private Employee getEmployee(EmployeeDTO employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setCompany(employeeDto.getCompany());
        return employee;
    }

    public void saveEmployees(List<EmployeeDTO> employees) {
        employees.forEach(employeeDTO -> employeeRepository.save(getEmployee(employeeDTO)));
    }
}
*/
