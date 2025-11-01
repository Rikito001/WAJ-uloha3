package sk.ukf.uloha2.controller;

import sk.ukf.uloha2.model.Employee;
import sk.ukf.uloha2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Value("#{'${employee.job-titles}'.split(',')}")
    private List<String> jobTitles;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ModelAttribute("jobTitles")
    public List<String> jobTitles() {
        return jobTitles;
    }

    @ModelAttribute("employmentStatuses")
    public List<String> employmentStatuses() {
        return Arrays.asList("Plný úväzok", "Čiastočný úväzok", "Dohoda", "Stážista/Praktikant");
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/new";
    }

    @PostMapping
    public String create(@ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "employees/new";
        }
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Zamestnanec s ID " + id + " neexistuje."));
        model.addAttribute("employee", employee);
        return "employees/detail";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}
