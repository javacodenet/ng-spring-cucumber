package com.demo.service;

import com.demo.dto.EmployeeDTO;
import com.demo.entity.Employee;
import com.demo.entity.EmployeeRepository;
import com.demo.helpers.LogHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LogHelper.class, EmployeeService.class})
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeDTO employeeDto;

    @Mock
    private Employee employee;

    @InjectMocks
    private EmployeeService employeeService;

    @Before
    public void setUp() throws Exception {
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    public void testSaveEmployee() throws Exception {
        //when(employeeRepository.save(isA(Employee.class))).thenReturn(null);
        whenNew(Employee.class).withNoArguments().thenReturn(employee);
        when(employeeDto.getId()).thenReturn("id");
        //doNothing().when(employee).setId(null);
        when(employeeRepository.save(employee)).thenReturn(null);

        mockStatic(LogHelper.class);
        doNothing().when(LogHelper.class);
        LogHelper.logMessage("Employee Saved...");

        assertThat(employeeService.saveEmployee(employeeDto), is(true));
        verify(employeeRepository).save(employee);
        verifyNew(Employee.class).withNoArguments();
        verify(employee).setId("id");
        verifyStatic();
        LogHelper.logMessage("Employee Saved...");
    }
}
