package com.krzyszkowski.sandbox.flowcontrol.module.management.controllers;

import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.EmployeeDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.services.EmployeeService;
import com.krzyszkowski.sandbox.flowcontrol.module.management.services.TimeCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("management")
public class ManagementController {

    private final EmployeeService employeeService;
    private final TimeCardService timeCardService;

    public ManagementController(EmployeeService employeeService, TimeCardService timeCardService) {
        this.employeeService = employeeService;
        this.timeCardService = timeCardService;
    }

    @GetMapping
    public String management() {
        return "management";
    }

    @GetMapping("employees")
    public String employees(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                            Model model) {
        model.addAttribute("employees", employeeService.getPagedEmployees(page - 1, size));
        return "management/employees";
    }

    @GetMapping("employees/add")
    public String add(Model model) {
        model.addAttribute("employee", new EmployeeDto());
        return "management/employees-add";
    }

    @PostMapping("employees/add")
    public ModelAndView add(@ModelAttribute("employee") @Valid EmployeeDto employeeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var response = new ModelAndView("management/employees-add");
            response.addObject("employee", employeeDto);
            return response;
        }

        employeeService.addEmployee(employeeDto);

        return new ModelAndView("redirect:/management/employees/add?success");
    }

    @GetMapping("employees/edit/{id}")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployee(id));
        return "management/employees-edit";
    }

    @PostMapping("employees/edit/{id}")
    public ModelAndView edit(@PathVariable long id,
                             @ModelAttribute("employee") @Valid EmployeeDto employeeDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var response = new ModelAndView("management/employees-edit");
            response.addObject("employee", employeeDto);
            return response;
        }

        employeeService.updateEmployee(employeeDto);

        return new ModelAndView("redirect:/management/employees");
    }

    @GetMapping("employees/delete/{id}")
    public String delete(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/management/employees";
    }

    @GetMapping("time")
    public String time(@RequestParam(value = "id") long id,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                       @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                       Model model) {
        model.addAttribute("timeCards", timeCardService.getPagedTimeCards(id, page - 1, size));
        return "management/employees-time";
    }

    @GetMapping("report")
    public String report(@RequestParam Map<String, String> params, Model model) {
        model.addAttribute("report", timeCardService.getReport(params));
        return "management/report";
    }
}
