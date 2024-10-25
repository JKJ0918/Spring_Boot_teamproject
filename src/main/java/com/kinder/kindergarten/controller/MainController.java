package com.kinder.kindergarten.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class MainController {

  @GetMapping(value={"/main","/"})
    String main(){
    return "main";
    }

    @GetMapping(value = "/member/login")
  String login(){
    return "/member/login";
    }
}
