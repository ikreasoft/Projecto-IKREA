package com.example.bibliotecagranvia.controladores;

import com.example.bibliotecagranvia.entidades.Ejemplar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bibliotecagranvia.persistencia.RepositorioEjemplar;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioEjemplar {

    @Autowired
    private RepositorioEjemplar repositorioEjemplar;

    public List<Ejemplar> listarTodos() {
        return repositorioEjemplar.findAll();
    }

    public Ejemplar obtenerPorId(Long id) {
        Optional<Ejemplar> optionalEjemplar = repositorioEjemplar.findById(id);
        return optionalEjemplar.orElse(null);
    }

    public List<Ejemplar> obtenerEjemplaresPorTitulo(Long idTitulo) {
        // Implementa la lógica para obtener ejemplares por el ID del título
        // Puedes utilizar el método del repositorio o implementar una consulta personalizada
        return repositorioEjemplar.findByTituloId(idTitulo);
    }

    // Puedes añadir más métodos según tus necesidades, como save, delete, etc.
}
