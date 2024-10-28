package com.kinder.kindergarten.controller.Employee;

import com.kinder.kindergarten.dto.Employee.EmployeeDTO;
import com.kinder.kindergarten.entity.Employee;
import com.kinder.kindergarten.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kinder.kindergarten.exception.OutOfStockException;

@RequestMapping("/employee")
@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String EmployeeForm(Model model){
        model.addAttribute("employeeDTO", new EmployeeDTO());
        return "employee/new";
    }

    @PostMapping(value = "/new")
    public String newEmployee(@Valid EmployeeDTO employeeDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "employee/new";
        }

        try {
            Employee employee = Employee.createEmployee(employeeDTO, passwordEncoder);
            employeeService.saveEmployee(employee);
        } catch (OutOfStockException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "employee/new";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "서버 오류가 발생했습니다.");
            return "employee/new";
        }

        return "redirect:login";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/employee/login";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "employee/login";
    }
}
