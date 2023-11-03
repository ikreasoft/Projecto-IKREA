package com.registro;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Contolador {

    @GetMapping("")
    public String showHomePage() {
        return "index";
    }
}
