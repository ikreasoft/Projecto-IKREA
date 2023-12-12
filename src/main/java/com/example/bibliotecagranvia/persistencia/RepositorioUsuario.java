package com.example.bibliotecagranvia.persistencia;


import org.springframework.data.repository.CrudRepository;
import com.example.bibliotecagranvia.entidades.Usuario;

import java.util.Optional;

public interface RepositorioUsuario extends CrudRepository<Usuario, Integer> {
    public Long countById(Integer id);

    Optional<Usuario> findByNombre(String nombreUsuario);
}
