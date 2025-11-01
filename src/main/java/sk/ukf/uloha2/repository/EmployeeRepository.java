package sk.ukf.uloha2.repository;

import sk.ukf.uloha2.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
