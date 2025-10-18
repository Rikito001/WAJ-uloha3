package sk.ukf.uloha2.dao;

import sk.ukf.uloha2.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> findAll();

    Employee findById(int id);

    Employee save(Employee employee);

    void deleteById(int id);

    List<Employee> findByFirstNameAndLastName(String firstname,  String lastname);
}