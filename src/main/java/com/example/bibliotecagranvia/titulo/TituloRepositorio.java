package com.example.bibliotecagranvia.titulo;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface TituloRepositorio extends CrudRepository<Titulo, Long> {
    Titulo findByTitulo(String titulo);

}
