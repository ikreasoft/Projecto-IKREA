package com.example.bibliotecagranvia.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactoController {
    @GetMapping("/about-us")
    public String showAboutUs() {
        return "contact/about-us";
    }
    @GetMapping("/contacts")
    public String showContact() {
        return "contact/contacts";
    }
}
