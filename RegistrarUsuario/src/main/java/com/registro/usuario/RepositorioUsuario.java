package com.registro.usuario;

import org.springframework.data.repository.CrudRepository;

public interface RepositorioUsuario extends CrudRepository<Usuario, Integer> {
    public Long countById(Integer id);
}
