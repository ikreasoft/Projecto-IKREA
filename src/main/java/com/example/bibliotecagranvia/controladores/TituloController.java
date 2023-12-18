package com.example.bibliotecagranvia.controladores;

import org.apache.el.stream.Optional;
import org.hibernate.mapping.List;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.bibliotecagranvia.entidades.Titulo;
import com.example.bibliotecagranvia.persistencia.AutorRepositorio;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;
@Controller
public class TituloController {
   @Autowired
    TituloRepositorio tituloRepositorio;
    @Autowired
    AutorRepositorio autorRepositorio;
    @GetMapping("/titles")
    public String showTitulos(Model model){
        model.addAttribute("titulos", tituloRepositorio.findAll());
        return "title/titles";
    }
    @GetMapping("/addTitle")
    public String addTitle(Model model){
        model.addAttribute("titulo", new Titulo());
        model.addAttribute("autoresTodos", autorRepositorio.findAll());
        return "title/addTitle";
    }
    @PostMapping("/addTitle")
    public String addTitlePost(@ModelAttribute Titulo titulo, Model model){
        java.util.Optional<Titulo> tituloExistentePorNombre = tituloRepositorio.findByNombre(titulo.getNombre());
        java.util.Optional<Titulo> tituloExistentePorISBN = tituloRepositorio.findByIsbn(titulo.getIsbn());

        if (tituloExistentePorNombre.isPresent() || tituloExistentePorISBN.isPresent()) {
            model.addAttribute("error", "El título ya existe en la base de datos");
            return "title/existsTitle"; // Página de error o mensaje indicando que el título ya existe
        } else {
            // El título no existe, así que se guarda en la base de datos
            tituloRepositorio.save(titulo);
            return "title/titleSaved"; // Página de confirmación de que el título se ha guardado
        }
    }
    @GetMapping("/editTitle/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Titulo titulo = tituloRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id de titulo no valido:" + id));

        model.addAttribute("titulo", titulo);
        model.addAttribute("autoresTodos", autorRepositorio.findAll());
        return "title/updateTitle";
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
        return "title/titleSaved";
    }
     @GetMapping("/deleteTitle/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Titulo titulo = tituloRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id de titulo no valido:" + id));
        tituloRepositorio.delete(titulo);
        return "title/titles";
    }

}
