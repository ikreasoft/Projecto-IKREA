package com.example.bibliotecagranvia.controladores;

import com.example.bibliotecagranvia.entidades.Prestamo;
import com.example.bibliotecagranvia.entidades.Reserva;
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
    public String mostrarReservas(Model model) {
        List<Prestamo> listaPrestamo = (List<Prestamo>) prestamoRepositorio.findAll(); // Obtener todas los prestamos
        model.addAttribute("listaPrestamo", listaPrestamo);
        return "ver_prestamos";
    }

    @GetMapping("/Gestion_prestamos")
    public String gestion_prestamos() {
        return "gestion_prestamo"; // HTML con el menú de opciones
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
            Usuario usuario = usuarioOptional.get();
            Titulo titulo = tituloOptional.get();

            // Lógica para préstamo
            Prestamo prestamo = new Prestamo();
            prestamo.setUsuario(usuario);
            prestamo.setTitulo(titulo);
            prestamo.setFechaPrestamo(new Date()); // Fecha actual como fecha de préstamo

            // Calcular la fecha de devolución (15 días después del préstamo)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, 15);
            Date fechaDevolucion = calendar.getTime();
            prestamo.setFechaDevolucion(fechaDevolucion); // Establecer la fecha de devolución

            prestamoRepositorio.save(prestamo);

            return "redirect:/confirmacion_prestamo";
        } else {
            return "redirect:/confirmacion_prestamo"; // Página de error o redirigir a algún lugar correspondiente
        }
    }
    @PostMapping("/renovar")
    public String renovarPrestamo(@RequestParam Long prestamoId) {
        Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(prestamoId);

        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();
            Date fechaActual = new Date();

            if (fechaActual.before(prestamo.getFechaDevolucion())) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaActual);
                calendar.add(Calendar.DAY_OF_YEAR, 15); // Ejemplo: renovar por 15 días más
                Date nuevaFechaDevolucion = calendar.getTime();

                // Actualizar la fecha de devolución del préstamo
                prestamo.setFechaDevolucion(nuevaFechaDevolucion);
                prestamoRepositorio.save(prestamo);

                return "redirect:/confirmacion_renovacion";
            } else {
                // Si ha excedido el tiempo límite, muestra un mensaje de error o redirige a una página correspondiente
                return "redirect:/tiempo_limite_excedido";
            }
        } else {
            return "redirect:/prestamo_no_encontrado";
        }
    }
}