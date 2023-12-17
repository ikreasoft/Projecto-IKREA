package com.example.bibliotecagranvia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.bibliotecagranvia.entidades.Usuario;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private ServicioUsuario service;

    // GET method for showing the list of users
    @GetMapping("/users")
    public String showUserList(Model model) {
        List<Usuario> listUsuario = service.listAll();
        model.addAttribute("listUsers", listUsuario);
        return "user/users";
    }

    @GetMapping("/addUser")
    public String showNewForm(Model model) {
        model.addAttribute("usuario", new Usuario()); 
        model.addAttribute("pageTitle", "Añadir nuevo usuario");
        return "user/addUser";
    }

    @PostMapping("/addUser")
    public String saveUser(Usuario usuario, RedirectAttributes ra) {
        service.save(usuario);
        ra.addFlashAttribute("message", "El usuario ha sido guardado correctamente.");
        return "user/users";
    }

    @GetMapping("/updateUser/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Usuario usuario = service.get(id);
            model.addAttribute("usuario", usuario);  // Cambiado "user" a "usuario"
            model.addAttribute("pageTitle", "Editar Usuario (ID: " + id + ")");
            return "addUser";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/user/users";
        }
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "El usuario con ID " + id + " ha sido eliminado.");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/user/users";
    }
}

