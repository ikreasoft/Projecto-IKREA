package com.example.bibliotecagranvia.titulo;

import org.springframework.data.repository.CrudRepository;
public interface TituloRepositorio extends CrudRepository<Titulo, Long> {
    Titulo findByNombre(String nombe);

}
