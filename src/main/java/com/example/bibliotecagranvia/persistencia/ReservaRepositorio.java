package com.example.bibliotecagranvia.persistencia;

import com.example.bibliotecagranvia.entidades.Reserva;
import org.springframework.data.repository.CrudRepository;

public interface ReservaRepositorio extends CrudRepository<Reserva, Long> {
}
