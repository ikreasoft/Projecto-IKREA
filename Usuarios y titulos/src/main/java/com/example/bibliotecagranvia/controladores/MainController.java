package com.example.bibliotecagranvia.controladores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.bibliotecagranvia.entidades.Ejemplar;

import com.example.bibliotecagranvia.entidades.Autor;
import com.example.bibliotecagranvia.entidades.Titulo;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class MainController {
    @Autowired
    TituloRepositorio tituloRepositorio;

    @Autowired
    private ServicioEjemplar servicioEjemplar;

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

    @GetMapping("/listaEjemplares")
    public String mostrarListaEjemplares(Model model) {
        List<Ejemplar> listaEjemplares = servicioEjemplar.listarTodos();
        model.addAttribute("listaEjemplares", listaEjemplares);
        return "listaEjemplares";
    }

    @GetMapping("/ejemplares/{id}")
    public String mostrarDetallesEjemplar(@PathVariable Long id, Model model) {
        Ejemplar ejemplar = servicioEjemplar.obtenerPorId(id);
        model.addAttribute("ejemplar", ejemplar);
        return "detallesEjemplar";
    }
}
