package com.example.bibliotecagranvia.persistencia;

import org.springframework.data.repository.CrudRepository;

import com.example.bibliotecagranvia.entidades.Titulo;
public interface TituloRepositorio extends CrudRepository<Titulo, Long> {
    Titulo findByNombre(String nombe);

}
