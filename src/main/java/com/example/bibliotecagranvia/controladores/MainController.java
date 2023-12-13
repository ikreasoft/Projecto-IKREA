package com.example.bibliotecagranvia.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showHomePage(Model model) {
        // model.addAttribute("laberinto", "/img/labirinto1.jpg");
        return "main/index";
    }

    @GetMapping("/indexAdmin")
    public String showIndexAdmin(Model model) {
        return "main/indexA";
    }

    @GetMapping("/indexBibliotecario")
    public String showIndexBibliotecario(Model model) {
        return "main/indexB";
    }

    @GetMapping("/indexUsuario")
    public String showIndexUsuario(Model model) {
        return "main/indexU";
    }
}
