package com.example.bibliotecagranvia.controladores;

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
    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private TituloRepositorio tituloRepositorio;

    @GetMapping("/reserva/{usuarioId}/{tituloId}")
    public String reservarLibro(@PathVariable int usuarioId, @PathVariable Long tituloId, Model model) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findById(usuarioId);
        Optional<Titulo> tituloOptional = tituloRepositorio.findById(tituloId);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Titulo titulo = tituloOptional.get();

            // Lógica para reserva
            Reserva reserva = new Reserva();
            reserva.setUsuario(usuario);
            reserva.setTitulo(titulo);
            reserva.setFechaReserva(new Date()); // Fecha actual como fecha de reserva

            reservaRepositorio.save(reserva);

            return "redirect:/users"; // Redirigir a la página de usuarios (o donde sea necesario)
        } else {
            // Manejar el caso en el que no se encuentre el usuario o el título
            return "error"; // Página de error o redirigir a algún lugar correspondiente
        }
    }

}
