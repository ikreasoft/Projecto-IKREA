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

    // Constructor para inyectar el repositorio en el controlador
    public List<Titulo> listAll() {
        return (List<Titulo>) repo.findAll();
    }

    @Autowired
    TituloRepositorio tituloRepositorio;

    // GET method for showing the list of authors
    @GetMapping("/")
    public String showHomePage(Model model) {
        return "main/index";
    }

    // GET method for showing new dates for renew a book enabled by the librarian
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

    // GET method for showing the catalog of books and magazines/jorunals
    @GetMapping("/catalog")
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

    // GET and POST methods for adding a new title
    @GetMapping("/addTitle")
    public String addTitle(Model model){
        model.addAttribute("titulo", new Titulo());
        //model.addAttribute("autores", autorRepositorio.findAll());
        int cantidadDisponible = tituloRepositorio.countByDisponible(true); // Cambia 'countByDisponible' por el método real en tu repositorio
        model.addAttribute("cantidadDisponible", cantidadDisponible);

        return "addTitle";
    }

    @GetMapping("/indexBibliotecario")
    public String showIndexBibliotecario(Model model) {
        return "main/indexB";
    }

    @GetMapping("/indexUsuario")
    public String showIndexUsuario(Model model) {
        return "main/indexU";}
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

    // GET method for updating a title (exists and new)
    @GetMapping("/titulo_ya_existe")
    public String titulo_ya_existe() {
        return "titulo_ya_existe"; // HTML con el menú de opciones
    }
    @GetMapping("/titulo_guardado_exito")
    public String titulo_guardado_exito() {
        return "titulo_guardado_exito"; // HTML con el menú de opciones
    }

    // POST method for delete a title
    @PostMapping("/quitarTitulo")
    public String quitarTitulo(@RequestParam Long id) {
        Optional<Titulo> tituloOptional = tituloRepositorio.findById(id);
        if (tituloOptional.isPresent()) {
            Titulo titulo = tituloOptional.get();
            tituloRepositorio.delete(titulo);
            return "redirect:/catalog"; // Redirige a la lista de títulos después de eliminar uno
        } else {
            return "redirect:/"; // Manejo de error si no se encuentra el título
        }
    }

    // POST method for modify the quantity of a title
    @PostMapping("/modificarCantidad")
    public String modificarCantidad(@RequestParam Long id, @RequestParam int nuevaCantidad) {
        Optional<Titulo> tituloOptional = tituloRepositorio.findById(id);

        if (tituloOptional.isPresent()) {
            Titulo titulo = tituloOptional.get();
            titulo.setCantidadDisponible(nuevaCantidad);
            tituloRepositorio.save(titulo);
            return "redirect:/catalog"; // Redirige a la lista de títulos después de modificar la cantidad
        } else {
            return "redirect:/"; // Manejo de error si no se encuentra el título
        }
    }
}
