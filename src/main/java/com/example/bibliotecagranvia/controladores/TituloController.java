package com.example.bibliotecagranvia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.bibliotecagranvia.entidades.Titulo;
import com.example.bibliotecagranvia.persistencia.AutorRepositorio;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;

@Controller
public class TituloController {
    @Autowired
    TituloRepositorio tituloRepositorio;
    @Autowired
    AutorRepositorio autorRepositorio;
      @GetMapping("/addTitle")
    public String addTitle(Model model){
        model.addAttribute("titulo", new Titulo());
        model.addAttribute("autoresTodos", autorRepositorio.findAll());
        return "addTitle";
    }
    @PostMapping("/addTitle")
    public String addTitlePost(@ModelAttribute Titulo titulo,Model model){
        tituloRepositorio.save(titulo);
        return "redirect:/";
    }
    @GetMapping("/editTitle/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Titulo titulo = tituloRepositorio.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Id de titulo no valido:" + id));
        
        model.addAttribute("titulo", titulo);
        model.addAttribute("autoresTodos", autorRepositorio.findAll());
        return "updateTitle";
    }
    @PostMapping("/editTitle/{id}")
    public String updateTitlePost(@PathVariable("id") long id, Titulo titulo, 
        BindingResult result, Model model){
            System.out.println(titulo.getNombre());
        /*if (result.hasErrors()) {
            titulo.setId(id);
            return "updateTitle";
        }*/
        tituloRepositorio.save(titulo);
        return "redirect:/";
    }
    
}
