package com.example.bibliotecagranvia;

import com.example.bibliotecagranvia.titulo.Autor;
import com.example.bibliotecagranvia.titulo.AutorRepositorio;
import com.example.bibliotecagranvia.titulo.Titulo;
import com.example.bibliotecagranvia.titulo.TituloRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TituloRepositorioTests {
    @Autowired
    private TituloRepositorio tituloRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorior;

    @Test
    public void testGuardarTitulo() {
        Autor autor = new Autor("TTT", "RRRR");
        autorRepositorior.save(autor);

        Titulo titulo = new Titulo("Día", "ISBN09", "Reserva2", autor);
        tituloRepositorio.save(titulo);
        assertNotNull(titulo.getId());
    }

    @Test
    public void testBuscarPorTitulo(){
        Autor autor = new Autor("José Carlos","Gonz.");
        autorRepositorior.save(autor);

        Titulo titulo = new Titulo("El bos","ISBN1","R23",autor);
        tituloRepositorio.save(titulo);

        Titulo encontrado = tituloRepositorio.findByTitulo("El bos");
        assertNotNull(encontrado);
        assertEquals("ISBN1",encontrado.getIsbn());
        assertEquals("R23",encontrado.getNumReserva());
        assertEquals("José Carlos", encontrado.getAutor().getNombre());
        assertEquals("Gonz.",encontrado.getAutor().getApellido());

    }

    }
