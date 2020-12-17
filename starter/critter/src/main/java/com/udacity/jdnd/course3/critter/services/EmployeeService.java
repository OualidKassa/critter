package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.dao.EmployeeDao;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    public Employee saveEmployee(Employee employee){
        return employeeDao.save(employee);
    }

    public Employee getEmployee(long employeeId){
        return employeeDao.getOne(employeeId);
    }

    public List<Employee> findEmployeesForService(LocalDate dayOfWeeks, Set<EmployeeSkill> employeeSkills){
        List<Employee> employees = employeeDao.getEmployeesByDaysAvailable(dayOfWeeks.getDayOfWeek());
        List<Employee> results = new ArrayList<>();
        for (Employee currentEmployee : employees) {
            if (currentEmployee.getSkills().containsAll(employeeSkills)) {
                results.add(currentEmployee);
            }
        }
        return results;
    }

    public List<Employee> getAllEmployeesByIds(List<Long> ids){
        return employeeDao.findAllById(ids);
    }
}
