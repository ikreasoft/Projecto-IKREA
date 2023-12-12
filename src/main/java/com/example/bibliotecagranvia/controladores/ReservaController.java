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

    @GetMapping("/buscar")
    public String mostrarFormularioBusqueda() {
        return "buscar"; // Nombre de la página HTML para buscar usuario y título
    }

    @GetMapping("/reserva")
    public String buscarUsuarioYTitulo(@RequestParam(required = false) String nombreUsuario, @RequestParam(required = false) String tituloNombre, Model model) {
        if (nombreUsuario == null || tituloNombre == null) {
            return "error"; // Página de error si los parámetros son nulos
        }

        Optional<Usuario> usuarioOptional = repositorioUsuario.findByNombre(nombreUsuario);
        Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
            model.addAttribute("usuario", usuarioOptional.get());
            model.addAttribute("titulo", tituloOptional.get());
            return "confirmar_reserva"; // Página para confirmar la reserva
        } else {
            return "usuario_o_titulo_no_encontrado"; // Página de error si no se encuentran usuario o título
        }
    }
        @GetMapping("/reservaslista")
        public String mostrarReservas(Model model) {
            List<Reserva> listaReservas = (List<Reserva>) reservaRepositorio.findAll(); // Obtener todas las reservas
            model.addAttribute("listaReservas", listaReservas);
            return "ver_reservas";
    }

    @PostMapping("/reservar")
    public String reservarLibro(@RequestParam String nombreUsuario, @RequestParam String tituloNombre) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findByNombre(nombreUsuario);
        Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Titulo titulo = tituloOptional.get();

            // Lógica para reserva
            Reserva reserva = new Reserva();
            reserva.setUsuario(usuario);
            reserva.setTitulo(titulo);
            reserva.setFechaReserva(new Date()); // Fecha actual como fecha de reserva

            reservaRepositorio.save(reserva);

            return "redirect:/buscar";
        } else {
            return "redirect:/error"; // Página de error o redirigir a algún lugar correspondiente
        }
    }
}