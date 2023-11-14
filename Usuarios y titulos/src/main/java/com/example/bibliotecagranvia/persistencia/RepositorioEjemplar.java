package com.example.bibliotecagranvia.persistencia;

import com.example.bibliotecagranvia.entidades.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioEjemplar extends JpaRepository<Ejemplar, Long> {
    List<Ejemplar> findByTituloId(Long idTitulo);
    // Puedes agregar más métodos según tus necesidades
}
