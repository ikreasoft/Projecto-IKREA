package com.example.bibliotecagranvia.persistencia;

import com.example.bibliotecagranvia.entidades.Prestamo;
import org.springframework.data.repository.CrudRepository;

public interface PrestamoRepositorio extends CrudRepository<Prestamo, Long> {
}
