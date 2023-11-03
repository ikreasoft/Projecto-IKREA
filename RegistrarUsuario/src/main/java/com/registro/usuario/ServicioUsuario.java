package com.registro.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuario {
    @Autowired private RepositorioUsuario repo;

    public List<Usuario> listAll() {
        return (List<Usuario>) repo.findAll();
    }

    public void save(Usuario usuario) {
        repo.save(usuario);
    }

    public Usuario get(Integer id) throws UserNotFoundException {
        Optional<Usuario> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find any users with ID " + id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any users with ID " + id);
        }
        repo.deleteById(id);
    }
}
