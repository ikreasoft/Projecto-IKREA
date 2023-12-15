package com.example.bibliotecagranvia.persistencia;

import org.springframework.data.repository.CrudRepository;

import com.example.bibliotecagranvia.entidades.Titulo;

import java.util.Optional;

public interface TituloRepositorio extends CrudRepository<Titulo, Long> {
    Optional<Titulo> findByNombre(String nombe);

    Optional<Titulo> findByIsbn(String isbn);
}
