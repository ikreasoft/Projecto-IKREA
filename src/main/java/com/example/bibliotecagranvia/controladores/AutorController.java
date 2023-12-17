package com.example.bibliotecagranvia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.bibliotecagranvia.entidades.Autor;
import com.example.bibliotecagranvia.persistencia.AutorRepositorio;
import org.springframework.ui.Model;
import jakarta.validation.Valid;

@Controller
public class AutorController {
    @Autowired
    private AutorRepositorio authorRepository;
    @GetMapping("/authors")
    public String showHomePage(Model model){
        model.addAttribute("autores", authorRepository.findAll());
        return "author/authors";
    }
    @GetMapping("/addAuthor")
    public String addAuthor(Autor autor){
        return "author/addAuthor";
    }
    @PostMapping("/addAuthor")
    public String addAuthorPost(@Valid Autor autor, BeanPropertyBindingResult result, Model model){
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "author/addAuthor";
        }

        authorRepository.save(autor);
        return "redirect:author/authors";
    }
    @GetMapping("/editAuthor/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Autor autor = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id de autor no valido:" + id));

        model.addAttribute("autor", autor);
        return "author/updateAuthor";
    }
    @PostMapping("/editAuthor/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Autor autor,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            autor.setId(id);
            return "author/updateAuthor";
        }
        authorRepository.save(autor);
        return "redirect:/authors";
    }

    @GetMapping("/deleteAuthor/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Autor autor = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id de autor no valido:" + id));
        authorRepository.delete(autor);
        return "redirect:author/authors";
    }

}
