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

    @GetMapping("identificar")

    public String mostrarFormularioPrestamo() {
        return "identificar";
    }

    @GetMapping("prestamolista")
    public String mostrarPrestamos(Model model) {
        List<Prestamo> listaPrestamo = (List<Prestamo>) prestamoRepositorio.findAll(); // Obtener todas los prestamos
        model.addAttribute("listaPrestamo", listaPrestamo);
        return "ver_prestamos";
    }

    @GetMapping("/Gestion_prestamos")
    public String gestion_prestamos() {
        return "gestion_prestamo"; // HTML con el menú de opciones
    }

    @GetMapping("/confirmacion_prestamos")
    public String confirmacionPrestamo() {
        return "confirmacion_prestamo"; // HTML con el menú de opciones
    }
    @GetMapping("/titulo_ya_prestado")
    public String titulo_ya_prestado() {
        return "titulo_ya_prestado"; // HTML con el menú de opciones
    }

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

            return "confirmar_prestamo"; // Redirigir a la página de confirmación
        } else {
            return "usuario_o_titulo_no_encontrado"; // Página de error si no se encuentran usuario o título
        }
    }

    @PostMapping("/prestar")
    public String prestarLibro(@RequestParam String nombreUsuario, @RequestParam String tituloNombre, Model model) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findByNombre(nombreUsuario);
        Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
            Titulo titulo = tituloOptional.get();

            // Verificar si el título está disponible
            if (titulo.getCantidadDisponible() > 0) {
                Usuario usuario = usuarioOptional.get();

                // Reducir la cantidad disponible en 1
                int cantidadDisponible = titulo.getCantidadDisponible();
                titulo.setCantidadDisponible(cantidadDisponible - 1);
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

                // Actualizar la disponibilidad del título
                if (cantidadDisponible == 1) {
                    titulo.setDisponible(false); // Marcar como no disponible si era el último ejemplar
                }

                tituloRepositorio.save(titulo);
                prestamoRepositorio.save(prestamo);

                return confirmacionPrestamo();
            } else {
                return titulo_ya_prestado();
            }
        } else {
            return "redirect:/usuario_o_titulo_no_encontrado";
        }
    }

    @PostMapping("/devolver")
    public String devolverLibro(@RequestParam Long prestamoId) {
        Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(prestamoId);

        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();
            Titulo titulo = prestamo.getTitulo();

            // Incrementar la cantidad disponible en 1
            int cantidadDisponible = titulo.getCantidadDisponible();
            titulo.setCantidadDisponible(cantidadDisponible + 1);

            // Actualizar la disponibilidad del título
            if (!titulo.isDisponible()) {
                titulo.setDisponible(true); // Marcar como disponible si se estaba agotado
            }

            tituloRepositorio.save(titulo);

            prestamoRepositorio.delete(prestamo); // Eliminar el préstamo de la base de datos
            return "redirect:/prestamolista"; // Redirigir a la lista de préstamos actualizada
        } else {
            return "redirect:/prestamo_no_encontrado";
        }
    }

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
            return "redirect:/prestamo_no_encontrado";
        }
    }
    @GetMapping("/renovacion_exitosa")
    public String renovacionExitosa() {
        return "renovacion_exitosa"; // HTML con el menú de opciones
    }



}