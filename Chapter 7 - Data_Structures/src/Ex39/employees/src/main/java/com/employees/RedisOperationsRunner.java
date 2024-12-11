package com.employees;

import com.employees.interfaceDao.IEmployeeDao;
import com.employees.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RedisOperationsRunner implements CommandLineRunner {

    @Autowired
    private IEmployeeDao empDao;

    @Override
    public void run(String... args) throws Exception {

        //saving one employee
        empDao.saveEmployee(new Employee(1, "John", "Johnson", "Manager", "2016-12-31"));

        //saving multiple employees
        empDao.saveAllEmployees(
                Map.of( 1, new Employee(1, "Tou", "Xiong", "Software Engineer", "2016-10-05"),
                        2, new Employee(2, "Michaela", "Michaelson", "District Manager", "2015-12-19"),
                        3, new Employee(3, "Jake", "Jacobson", "Programmer", "")
                )
        );

        //modifying employee with empId 3
        empDao.updateEmployee(new Employee(3, "Jake", "Jacobson", "Programmer", "2016-12-31"));

        //deleting employee with empID 1
        empDao.deleteEmployee(1);

        //retrieving all employees
        empDao.getAllEmployees().forEach((k,v)-> System.out.println(k +" : "+v));

        //retrieving employee with empID 2
        System.out.println("Emp details for 2 : "+empDao.getOneEmployee(2));
    }
}
