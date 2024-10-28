package com.kinder.kindergarten.controller.Employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping(value = "/")
    public String main(){
        System.out.println("Redirecting to main page"); // 로그 추가
        return "main";
    }
}
