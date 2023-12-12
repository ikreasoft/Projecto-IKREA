package com.example.bibliotecagranvia.controladores;
import com.example.bibliotecagranvia.entidades.Usuario;
import com.example.bibliotecagranvia.persistencia.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.bibliotecagranvia.entidades.Autor;
import com.example.bibliotecagranvia.entidades.Titulo;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;

import java.util.List;

@Controller
public class MainController {
    private TituloRepositorio repo;
    public List<Titulo> listAll() {
        return (List<Titulo>) repo.findAll();
    }
    @Autowired
    TituloRepositorio tituloRepositorio;
    @GetMapping("/")
    public String showHomePage(Model model){
        model.addAttribute("laberinto", "/img/labirinto1.jpg");
        return "index";
    }

    @GetMapping("/Catalog")
    public String showTitleList(Model model) {
        List<Titulo> listTitle = (List<Titulo>) tituloRepositorio.findAll();
        model.addAttribute("listTitles", listTitle);
        return "catalogo"; // Retornar el nombre de la vista a mostrar
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
