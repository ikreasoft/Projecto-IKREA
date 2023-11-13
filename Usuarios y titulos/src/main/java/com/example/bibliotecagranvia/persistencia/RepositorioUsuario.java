package com.example.bibliotecagranvia.persistencia;


import com.example.bibliotecagranvia.entidades.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioUsuario extends CrudRepository<Usuario, Integer> {
    public Long countById(Integer id);
}
