package com.example.bibliotecagranvia.controladores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.bibliotecagranvia.entidades.Autor;
import com.example.bibliotecagranvia.entidades.Titulo;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;

@Controller
public class MainController {
    @Autowired
    TituloRepositorio tituloRepositorio;
    @GetMapping("/")
    public String showHomePage(Model model){
        model.addAttribute("laberinto", "/img/labirinto1.jpg");
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
    @GetMapping("/deleteTitle/{id}")
    public String deletTitle(@PathVariable("id") long id, Model model) {
    Titulo titulo = tituloRepositorio.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Id de titulo no valido:" + id));
    tituloRepositorio.delete(titulo);
    return "redirect:/";
}
}
