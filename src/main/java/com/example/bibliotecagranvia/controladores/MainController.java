package com.example.bibliotecagranvia.controladores;
import com.example.bibliotecagranvia.entidades.Usuario;
import com.example.bibliotecagranvia.persistencia.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String showHomePage(Model model) {
        // model.addAttribute("laberinto", "/img/labirinto1.jpg");
        return "main/index";
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

    @GetMapping("/indexBibliotecario")
    public String showIndexBibliotecario(Model model) {
        return "main/indexB";
    }

    @GetMapping("/indexUsuario")
    public String showIndexUsuario(Model model) {
        return "main/indexU";
    }
}
