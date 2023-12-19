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

    private final ReservaRepositorio reservaRepositorio;
    private final RepositorioUsuario repositorioUsuario;
    private final TituloRepositorio tituloRepositorio;

    @Autowired
    public ReservaController(ReservaRepositorio reservaRepositorio, RepositorioUsuario repositorioUsuario, TituloRepositorio tituloRepositorio) {
        this.reservaRepositorio = reservaRepositorio;
        this.repositorioUsuario = repositorioUsuario;
        this.tituloRepositorio = tituloRepositorio;
    }

    @GetMapping("/addBooking")
    public String mostrarFormularioBusqueda() {
        return "booking/addBooking"; // Nombre de la página HTML para buscar usuario y título
    }

    // @GetMapping("/reserva")
    // public String buscarUsuarioYTitulo(@RequestParam(required = false) String nombreUsuario, @RequestParam(required = false) String tituloNombre, Model model) {
    //     if (nombreUsuario == null || tituloNombre == null) {
    //         return "miscelaneous/error"; // Página de error si los parámetros son nulos
    //     }

    //     Optional<Usuario> usuarioOptional = repositorioUsuario.findByNombre(nombreUsuario);
    //     Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);

    //     if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
    //         model.addAttribute("usuario", usuarioOptional.get());
    //         model.addAttribute("titulo", tituloOptional.get());
    //         return "booking/confirmBooking"; // Página para confirmar la reserva
    //     } else {
    //         return "miscelaneous/userTitleNotFound"; // Página de error si no se encuentran usuario o título
    //     }
    // }

    @GetMapping("/bookings")
    public String mostrarReservas(Model model) {
        List<Reserva> listaReservas = (List<Reserva>) reservaRepositorio.findAll(); // Obtener todas las reservas
        model.addAttribute("listaReservas", listaReservas);
        return "booking/bookings";
    }

    @GetMapping("/bookingsUser")
    public String mostrarReservasUsuario(Model model) {
        List<Reserva> listaReservas = ((List<Reserva>) reservaRepositorio.findAll()).stream().filter(r->r.getUsuario().getId()==1).collect(Collectors.toList()); // Obtener todas las reservas del usuario con id1
        model.addAttribute("listaReservas", listaReservas);
        return "booking/bookingsUser";
    }

    @PostMapping("/reservar")
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
    @GetMapping("/confirmationBooking")
    public String confirmacion_reserva() {
        return "booking/confirmationBooking"; // HTML con el menú de opciones
    }
    @GetMapping("/indexBooking")
    public String gestion_reservas() {
        return "booking/indexBooking"; // HTML con el menú de opciones
    }
    @PostMapping("/quitarReserva")
    public String quitarReserva(@RequestParam Long reservaId) {
        Optional<Reserva> reservaOptional = reservaRepositorio.findById(reservaId);

        if (reservaOptional.isPresent()) {
            Reserva reserva = reservaOptional.get();
            reservaRepositorio.delete(reserva); // Eliminar la reserva de la base de datos
            return "redirect:/bookings"; // Redirigir a la lista de reservas actualizada
        } else {
            return "redirect:/booking/bookingNotFound"; // Manejo de error si la reserva no se encuentra
        }
    }

}