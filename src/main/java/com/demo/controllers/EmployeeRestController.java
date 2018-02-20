package com.demo.controllers;

import com.demo.dto.EmployeeDTO;
import com.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<EmployeeDTO> list() {
        return employeeService.getEmployees();
    }

    @PutMapping
    public boolean updateEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.updateEmployee(employee);
    }

    @RequestMapping(method = RequestMethod.POST)
    public boolean saveEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.saveEmployee(employee);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public boolean deleteEmployee(@PathVariable Integer id) {
        return employeeService.deleteEmployee(id);
    }

}
