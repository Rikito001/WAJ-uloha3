package sk.ukf.uloha2.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.ukf.uloha2.dto.ApiResponse;
import sk.ukf.uloha2.entity.Employee;
import sk.ukf.uloha2.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<ApiResponse<List<Employee>>> findAll() {
        List<Employee> employees = employeeService.findAll();
        ApiResponse<List<Employee>> response = ApiResponse.success(
                "Zoznam zamestnancov úspešne načítaný",
                employees
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployee(@PathVariable int id) {
        Employee employee = employeeService.findById(id);

        ApiResponse<Employee> response = ApiResponse.success(
                "Zamestnanec úspešne načítaný",
                employee
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<ApiResponse<Employee>> addEmployee(@Valid @RequestBody Employee employee) {
        employee.setId(0);
        Employee employee_db = employeeService.save(employee);

        ApiResponse<Employee> response = ApiResponse.success(
                "Zamestnanec úspešne pridaný",
                employee_db
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable int id,
            @Valid @RequestBody Employee employee) {

        employeeService.findById(id);

        employee.setId(id);
        Employee updatedEmployee = employeeService.save(employee);

        ApiResponse<Employee> response = ApiResponse.success(
                "Zamestnanec úspešne aktualizovaný",
                updatedEmployee
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable int id) {
        employeeService.deleteById(id);

        ApiResponse<String> response = ApiResponse.success(
                "Zamestnanec úspešne zmazaný",
                "Zmazaný zamestnanec s ID: " + id
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/employeeSearch")
    public ResponseEntity<ApiResponse<List<Employee>>> search(
            @RequestParam String firstName,
            @RequestParam String lastName) {

        List<Employee> employees = employeeService.findByFirstNameAndLastName(firstName, lastName);

        ApiResponse<List<Employee>> response = ApiResponse.success(
                "Vyhľadávanie dokončené",
                employees
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}