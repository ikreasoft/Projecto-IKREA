package com.example.bibliotecagranvia.controladores;
import com.example.bibliotecagranvia.entidades.Usuario;
import com.example.bibliotecagranvia.persistencia.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

import com.example.bibliotecagranvia.entidades.Autor;
import com.example.bibliotecagranvia.entidades.Titulo;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        model.addAttribute("laberinto", "/img/labirinto1.jpg");
        return "index";
    }

    @PostMapping("/actualizarDisponibilidad")
    public String actualizarDisponibilidad(@RequestParam Long id, @RequestParam int nuevaCantidad) {
        Optional<Titulo> tituloOptional = tituloRepositorio.findById(id);

        if (tituloOptional.isPresent()) {
            Titulo titulo = tituloOptional.get();
            titulo.setCantidadDisponible(nuevaCantidad);
            tituloRepositorio.save(titulo);
            return "redirect:/verDisponibilidad";
        } else {
            return "redirect:/"; // Manejo de error si no se encuentra el título
        }
    }
    @GetMapping("/Catalog")
    public String showTitleList(Model model) {
        List<Titulo> listTitle = (List<Titulo>) tituloRepositorio.findAll();
        model.addAttribute("listTitles", listTitle);

        // Agregar información de disponibilidad para cada título
        Map<Long, Integer> disponibilidadTitulos = new HashMap<>();
        for (Titulo titulo : listTitle) {
            disponibilidadTitulos.put(titulo.getId(), titulo.getCantidadDisponible());
        }
        model.addAttribute("disponibilidadTitulos", disponibilidadTitulos);

        return "catalogo"; // Retornar el nombre de la vista a mostrar
    }
    @GetMapping("/addTitle")
    public String addTitle(Model model){
        model.addAttribute("titulo", new Titulo());
        //model.addAttribute("autores", autorRepositorio.findAll());
        int cantidadDisponible = tituloRepositorio.countByDisponible(true); // Cambia 'countByDisponible' por el método real en tu repositorio
        model.addAttribute("cantidadDisponible", cantidadDisponible);

        return "addTitle";
    }
    @PostMapping("/addTitle")
    public String addTitlePost(@ModelAttribute Titulo titulo, Model model) {
        // Verificar si el título ya existe por su nombre o ISBN
        Optional<Titulo> tituloExistentePorNombre = tituloRepositorio.findByNombre(titulo.getNombre());
        Optional<Titulo> tituloExistentePorISBN = tituloRepositorio.findByIsbn(titulo.getIsbn());

        if (tituloExistentePorNombre.isPresent() || tituloExistentePorISBN.isPresent()) {
            // Si el título ya existe, puedes redirigir a una página de error o mostrar un mensaje
            model.addAttribute("error", "El título ya existe en la base de datos");
            return titulo_ya_existe(); // Nombre de la vista para mostrar el mensaje de error
        } else {
            // Si el título no existe, guárdalo en la base de datos
            tituloRepositorio.save(titulo);
            return titulo_guardado_exito();
        }
    }

    @GetMapping("/titulo_ya_existe")
    public String titulo_ya_existe() {
        return "titulo_ya_existe"; // HTML con el menú de opciones
    }
    @GetMapping("/titulo_guardado_exito")
    public String titulo_guardado_exito() {
        return "titulo_guardado_exito"; // HTML con el menú de opciones
    }




    @PostMapping("/quitarTitulo")
    public String quitarTitulo(@RequestParam Long id) {
        Optional<Titulo> tituloOptional = tituloRepositorio.findById(id);

        if (tituloOptional.isPresent()) {
            Titulo titulo = tituloOptional.get();
            tituloRepositorio.delete(titulo);
            return "redirect:/Catalog"; // Redirige a la lista de títulos después de eliminar uno
        } else {
            return "redirect:/"; // Manejo de error si no se encuentra el título
        }
    }

    @PostMapping("/modificarCantidad")
    public String modificarCantidad(@RequestParam Long id, @RequestParam int nuevaCantidad) {
        Optional<Titulo> tituloOptional = tituloRepositorio.findById(id);

        if (tituloOptional.isPresent()) {
            Titulo titulo = tituloOptional.get();
            titulo.setCantidadDisponible(nuevaCantidad);
            tituloRepositorio.save(titulo);
            return "redirect:/Catalog"; // Redirige a la lista de títulos después de modificar la cantidad
        } else {
            return "redirect:/"; // Manejo de error si no se encuentra el título
        }
    }
}
