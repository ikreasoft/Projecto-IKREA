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

import java.util.Date;
import java.util.Optional;

@Controller
public class PrestamoController {
    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private RepositorioUsuario usuarioRepositorio;

    @Autowired
    private TituloRepositorio tituloRepositorio;

    @GetMapping("/prestamo/{usuarioId}/{tituloId}")
    public String prestarLibro(@PathVariable int usuarioId, @PathVariable Long tituloId, Model model) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(usuarioId);
        Optional<Titulo> tituloOptional = tituloRepositorio.findById(tituloId);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Titulo titulo = tituloOptional.get();

            // Lógica para préstamo
            Prestamo prestamo = new Prestamo();
            prestamo.setUsuario(usuario);
            prestamo.setTitulo(titulo);
            prestamo.setFechaPrestamo(new Date()); // Fecha actual como fecha de préstamo

            prestamoRepositorio.save(prestamo);

            return "redirect:/users"; // Redirigir a la página de usuarios (o donde sea necesario)
        } else {
            // Manejar el caso en el que no se encuentre el usuario o el título
            return "error"; // Página de error o redirigir a algún lugar correspondiente
        }
    }
}
