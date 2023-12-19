package com.example.bibliotecagranvia.controladores;
import java.util.List;
import com.example.bibliotecagranvia.entidades.Reserva;
import com.example.bibliotecagranvia.entidades.Titulo;
import com.example.bibliotecagranvia.entidades.Usuario;
import com.example.bibliotecagranvia.persistencia.ReservaRepositorio;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;
import com.example.bibliotecagranvia.persistencia.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador para la gestión de reservas
 */

@Controller
public class ReservaController {
    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private TituloRepositorio tituloRepositorio;


    @GetMapping("/addBookingBiblio")
    public String mostrarFormularioBusqueda() {
        return "booking/addBookingB"; // Nombre de la página HTML para buscar usuario y título
    }
     @GetMapping("/addBookingUser")
    public String mostrarFormularioBusquedaUser() {
        return "booking/addBookingU"; // Nombre de la página HTML para buscar título
    }

    @GetMapping("/bookingsBiblio")
    public String mostrarReservas(Model model) {
        List<Reserva> listaReservas = (List<Reserva>) reservaRepositorio.findAll(); // Obtener todas las reservas
        model.addAttribute("listaReservas", listaReservas);
        return "booking/bookingsB";
    }

    @GetMapping("/bookingsUser")
    public String mostrarReservasUsuario(Model model) {
        List<Reserva> listaReservas = ((List<Reserva>) reservaRepositorio.findAll()).stream().filter(r->r.getUsuario().getId()==1).collect(Collectors.toList()); // Obtener todas las reservas del usuario con id1
        model.addAttribute("listaReservas", listaReservas);
        return "booking/bookingsU";
    }

    @PostMapping("/reservarBiblio")
    public String reservarLibro(@RequestParam String nombreUsuario, @RequestParam String tituloNombre, @RequestParam String isbn) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findByNombre(nombreUsuario);
        Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);
        Optional<Titulo> tituloPorISBN = tituloRepositorio.findByIsbn(isbn);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent() && tituloPorISBN.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Titulo titulo = tituloOptional.get();

            // Comprobar si el ISBN coincide con el título encontrado por nombre
            Titulo tituloPorISBNEncontrado = tituloPorISBN.get();
            if (!titulo.getIsbn().equals(tituloPorISBNEncontrado.getIsbn())) {
                return "redirect:/booking/bookings"; // ISBN no coincide con el título
            }

            // Lógica para reserva
            Reserva reserva = new Reserva();
            reserva.setUsuario(usuario);
            reserva.setTitulo(titulo);
            reserva.setFechaReserva(new Date()); // Fecha actual como fecha de reserva

            reservaRepositorio.save(reserva);

            return confirmacion_reserva(); // Redirige a la página de confirmación
        } else {
            return "redirect:/booking/bookings"; // Página de error o redirigir a algún lugar correspondiente
        }
    }
    @PostMapping("/reservarUser")
    public String reservarLibroUser( @RequestParam String tituloNombre, @RequestParam String isbn) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findById(1);
        Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);
        Optional<Titulo> tituloPorISBN = tituloRepositorio.findByIsbn(isbn);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent() && tituloPorISBN.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Titulo titulo = tituloOptional.get();

            // Comprobar si el ISBN coincide con el título encontrado por nombre
            Titulo tituloPorISBNEncontrado = tituloPorISBN.get();
            if (!titulo.getIsbn().equals(tituloPorISBNEncontrado.getIsbn())) {
                return "redirect:/booking/bookingsU"; // ISBN no coincide con el título
            }

            // Lógica para reserva
            Reserva reserva = new Reserva();
            reserva.setUsuario(usuario);
            reserva.setTitulo(titulo);
            reserva.setFechaReserva(new Date()); // Fecha actual como fecha de reserva

            reservaRepositorio.save(reserva);

            return confirmacion_reserva(); // Redirige a la página de confirmación
        } else {
            return "redirect:/booking/bookingsU"; // Página de error o redirigir a algún lugar correspondiente
        }
    }
    @GetMapping("/confirmationBookingBiblio")
    public String confirmacion_reserva() {
        return "booking/confirmationBookingB"; // HTML con el menú de opciones
    }
    @GetMapping("/confirmationBookingUser")
    public String confirmacion_reserva_user() {
        return "booking/confirmationBookingU"; // HTML con el menú de opciones
    }
    @GetMapping("/indexBookingBiblio")
    public String gestion_reservas() {
        return "booking/indexBookingB"; // HTML con el menú de opciones
    }
    @GetMapping("/indexBookingUser")
    public String gestion_reservas_usuario() {
        return "booking/indexBookingU"; // HTML con el menú de opciones
    }
    @PostMapping("/quitarReservaBiblio")
    public String quitarReserva(@RequestParam Long reservaId) {
        Optional<Reserva> reservaOptional = reservaRepositorio.findById(reservaId);

        if (reservaOptional.isPresent()) {
            Reserva reserva = reservaOptional.get();
            reservaRepositorio.delete(reserva); // Eliminar la reserva de la base de datos
            return "redirect:/bookingsBiblio"; // Redirigir a la lista de reservas actualizada
        } else {
            return "redirect:/booking/bookingNotFound"; // Manejo de error si la reserva no se encuentra
        }
    }
    @PostMapping("/quitarReservaUser")
    public String quitarReservaUser(@RequestParam Long reservaId) {
        Optional<Reserva> reservaOptional = reservaRepositorio.findById(reservaId);

        if (reservaOptional.isPresent()) {
            Reserva reserva = reservaOptional.get();
            reservaRepositorio.delete(reserva); // Eliminar la reserva de la base de datos
            return "redirect:/bookingsUser"; // Redirigir a la lista de reservas actualizada
        } else {
            return "redirect:/booking/bookingNotFound"; // Manejo de error si la reserva no se encuentra
        }
    }

}