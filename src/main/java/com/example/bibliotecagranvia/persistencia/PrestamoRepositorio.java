package com.example.bibliotecagranvia.persistencia;

import com.example.bibliotecagranvia.entidades.Prestamo;
import com.example.bibliotecagranvia.entidades.Usuario;
import com.example.bibliotecagranvia.entidades.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long> {
    Optional<Prestamo> findByNombreUsuarioAndIsbn(String nombreUsuario, String isbn);

    Optional<Prestamo> findByIsbn(String isbn);
}

