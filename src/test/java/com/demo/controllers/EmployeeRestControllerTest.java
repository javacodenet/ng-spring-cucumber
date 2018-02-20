package com.demo.controllers;

import com.demo.dto.EmployeeDTO;
import com.demo.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class EmployeeRestControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeDTO employeeDto;

    @InjectMocks
    private EmployeeRestController employeeRestController;

    @Before
    public void setUp() throws Exception {
        employeeRestController = new EmployeeRestController(employeeService);
    }

    @Test
    public void testList() {
        List<EmployeeDTO> employees = Collections.singletonList(employeeDto);
        when(employeeService.getEmployees()).thenReturn(employees);
        List<EmployeeDTO> actualEmployees = employeeRestController.list();
        assertThat(employees, is(actualEmployees));
    }
}
