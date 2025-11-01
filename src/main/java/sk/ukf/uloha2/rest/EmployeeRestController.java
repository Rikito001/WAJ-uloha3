package sk.ukf.uloha2.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sk.ukf.uloha2.dto.ApiResponse;
import sk.ukf.uloha2.model.Employee;
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
    public ResponseEntity<ApiResponse<Employee>> getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zamestnanec nebol nájdený"));

        ApiResponse<Employee> response = ApiResponse.success(
                "Zamestnanec úspešne načítaný",
                employee
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<ApiResponse<Employee>> addEmployee(@Valid @RequestBody Employee employee) {
        employee.setId(null);
        Employee employee_db = employeeService.save(employee);

        ApiResponse<Employee> response = ApiResponse.success(
                "Zamestnanec úspešne pridaný",
                employee_db
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {

        employeeService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zamestnanec nebol nájdený"));

        employee.setId(id);
        Employee updatedEmployee = employeeService.save(employee);

        ApiResponse<Employee> response = ApiResponse.success(
                "Zamestnanec úspešne aktualizovaný",
                updatedEmployee
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Long id) {
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
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Vyhľadávanie nie je implementované");
    }
}
