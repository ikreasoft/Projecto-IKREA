package com.example.bibliotecagranvia.controladores;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    
    

    // GET method for showing the list of authors
    @GetMapping("/")
    public String showHomePage(Model model) {
        return "main/index";
    }

    @GetMapping("/indexAdmin")
    public String showIndexAdmin(Model model) {
        return "main/indexA";
    }

    @GetMapping("/indexBiblio")
    public String showIndexBibliotecario(Model model) {
        return "main/indexB";
    }

    @GetMapping("/indexUser")
    public String showIndexUsuario(Model model) {
        return "main/indexU";}
    
}
