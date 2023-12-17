package com.example.bibliotecagranvia.controladores;

import com.example.bibliotecagranvia.entidades.Prestamo;
import com.example.bibliotecagranvia.entidades.Titulo;
import com.example.bibliotecagranvia.entidades.Usuario;
import com.example.bibliotecagranvia.persistencia.PrestamoRepositorio;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;
import com.example.bibliotecagranvia.persistencia.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class PrestamoController {
    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private TituloRepositorio tituloRepositorio;

    // Constructor para inyectar el repositorio en el controlador (para obtener todos los préstamos)
    @GetMapping("checkLend")
    public String mostrarFormularioPrestamo() {
        return "lend/checkLend";
    }

    // GET method for showing the list of loans enabled by the librarian
    @GetMapping("prestamoLista")
    public String mostrarPrestamos(Model model) {
        List<Prestamo> listaPrestamo = (List<Prestamo>) prestamoRepositorio.findAll(); // Obtener todas los prestamos
        model.addAttribute("listaPrestamo", listaPrestamo);
        return "lend/lends";
    }

    // GET method for showing the list of loans enabled by the librarian
    @GetMapping("/indexLend")
    public String gestion_prestamos() {
        return "lend/indexLend"; // HTML con el menú de opciones
    }

    @GetMapping("/confirmacion_prestamo")
    public String confirmacionPrestamo() {
        return "lend/confirmationLend"; // HTML con el menú de opciones
    }
    @GetMapping("/titulo_ya_prestado")
    public String titulo_ya_prestado() {
        return "lend/lendedTitle"; // HTML con el menú de opciones
    }

    // GET method for manage 'prestamo' option
    @PostMapping("/prestamo")
    public String confirmarPrestamo(@RequestParam String nombreUsuario, @RequestParam String tituloNombre, Model model) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findByNombre(nombreUsuario);
        Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Titulo titulo = tituloOptional.get();

            // Obtener la fecha actual como fecha de préstamo
            Date fechaPrestamo = new Date();

            // Calcular la fecha de devolución sumando 15 días a la fecha de préstamo
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaPrestamo);
            calendar.add(Calendar.DATE, 15);
            Date fechaDevolucion = calendar.getTime();

            // Agregar usuario, título, fecha de préstamo y fecha de devolución al modelo
            model.addAttribute("usuario", usuario);
            model.addAttribute("titulo", titulo);
            model.addAttribute("fechaPrestamo", fechaPrestamo);
            model.addAttribute("fechaDevolucion", fechaDevolucion);

            return "lend/confirmLend"; // Redirigir a la página de confirmación
        } else {
            return "miscelaneous/userTitleNotFound"; // Página de error si no se encuentran usuario o título
        }
    }

    // POST method for manage 'prestamo' option
    @PostMapping("/prestar")
    public String prestarLibro(@RequestParam String nombreUsuario, @RequestParam String tituloNombre, Model model) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findByNombre(nombreUsuario);
        Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
            Titulo titulo = tituloOptional.get();

            // Verificar si el título está disponible
            if (titulo.getNumReservas() > 0) {
                Usuario usuario = usuarioOptional.get();

                // Reducir la cantidad disponible en 1
                int cantidadDisponible = titulo.getNumReservas();
                titulo.setNumReservas(cantidadDisponible - 1);
                // Crear un nuevo préstamo

                Prestamo prestamo = new Prestamo();
                prestamo.setUsuario(usuario); // Asignar el usuario al préstamo
                prestamo.setTitulo(titulo); // Asignar el título al préstamo
                prestamo.setNombreUsuario(usuario.getNombre()); // Obtener el nombre del usuario
                prestamo.setNombreTitulo(titulo.getNombre()); // Obtener el nombre del título
                prestamo.setIsbn(titulo.getIsbn()); // Establecer el ISBN del libro

                // Obtener la fecha actual como fecha de inicio del préstamo
                Date fechaInicioPrestamo = new Date();
                prestamo.setFechaPrestamo(fechaInicioPrestamo); // Establecer la fecha de inicio del préstamo

                // Calcular la fecha de devolución (15 días después del préstamo)
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR, 15);
                Date fechaDevolucion = calendar.getTime();
                prestamo.setFechaDevolucion(fechaDevolucion); // Establecer la fecha de devolución

                tituloRepositorio.save(titulo);
                prestamoRepositorio.save(prestamo);

                return confirmacionPrestamo();
            } else {
                return titulo_ya_prestado();
            }
        } else {
            return "redirect:/miscelaneous/userTitleNotFound";
        }
    }

    // POST method for showing the list of loans enabled by the librarian and the user can return it
    @PostMapping("/devolver")
    public String devolverLibro(@RequestParam Long prestamoId) {
        Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(prestamoId);

        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();
            Titulo titulo = prestamo.getTitulo();

            // Incrementar la cantidad disponible en 1
            int cantidadDisponible = titulo.getNumReservas();
            titulo.setNumReservas(cantidadDisponible + 1);

            tituloRepositorio.save(titulo);

            prestamoRepositorio.delete(prestamo); // Eliminar el préstamo de la base de datos
            return "redirect:/lend/prestamoLista"; // Redirigir a la lista de préstamos actualizada
        } else {
            return "redirect:/lend/lendNotFound";
        }
    }

    // POST method for user can renew a loan
    @PostMapping("/renovar")
    public String actualizarPrestamo(@RequestParam Long prestamoId, Model model) {
        Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(prestamoId);

        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();

            // Actualizar la fecha de préstamo (a la fecha actual)
            Date nuevaFechaPrestamo = new Date();
            prestamo.setFechaPrestamo(nuevaFechaPrestamo);

            // Calcular y actualizar la nueva fecha de devolución (por ejemplo, sumando 15 días a la fecha actual)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nuevaFechaPrestamo);
            calendar.add(Calendar.DAY_OF_YEAR, 15);
            Date nuevaFechaDevolucion = calendar.getTime();
            prestamo.setFechaDevolucion(nuevaFechaDevolucion);

            // Guardar los cambios en el préstamo en la base de datos
            prestamoRepositorio.save(prestamo);

            // Agregar el préstamo actualizado al modelo si es necesario mostrarlo en la vista
            model.addAttribute("prestamo", prestamo);

            return renovacionExitosa(); // Página de confirmación de actualización
        } else {
            return "redirect:/lendNotFound";
        }
    }

    // GET method for renew a loan successfully
    @GetMapping("/renovacion_exitosa")
    public String renovacionExitosa() {
        return "lend/renovationSuccesful"; // HTML con el menú de opciones
    }

}