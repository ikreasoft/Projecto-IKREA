package com.example.bibliotecagranvia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.bibliotecagranvia.entidades.Autor;
import com.example.bibliotecagranvia.entidades.Titulo;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;

@Controller
public class MainController {
    @Autowired
    TituloRepositorio tituloRepositorio;
    @GetMapping("/")
    public String showHomePage(){
        return "index";
    }

    @GetMapping("/addTitle")
    public String addTitle(Model model){
        model.addAttribute("titulo", new Titulo());
        //model.addAttribute("autores", autorRepositorio.findAll());
        return "addTitle";
    }
    @PostMapping("/addTitle")
    public String addTitlePost(@ModelAttribute Titulo titulo,Model model){
           // titulo.setAutor(autorRepositorio.findAll().iterator().next());
       // titulo.setAutor(autorRepositorio.findById(autore.getId()).get());
       //titulo.addAutor(new Autor("José Carlos","Gonz."));
        tituloRepositorio.save(titulo);
        return "redirect:/";
    }
}
