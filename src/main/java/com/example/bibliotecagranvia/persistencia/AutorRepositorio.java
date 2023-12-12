package com.example.bibliotecagranvia.persistencia;
import com.example.bibliotecagranvia.entidades.Autor;

import org.springframework.data.repository.CrudRepository;

public interface AutorRepositorio extends CrudRepository<Autor, Long> {
}
