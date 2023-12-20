package bibliotecagranvia;

import com.example.bibliotecagranvia.controladores.TituloController;
import com.example.bibliotecagranvia.entidades.Titulo;
import com.example.bibliotecagranvia.persistencia.AutorRepositorio;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TituloControllerTest {

    @Mock
    private TituloRepositorio tituloRepositorio;

    @Mock
    private AutorRepositorio autorRepositorio;

    @Mock
    private Model model;

    @InjectMocks
    private TituloController tituloController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testShowTitulos() {
        List<Titulo> listaTitulos = new ArrayList<>();
        // Agrega títulos de prueba a la lista
        listaTitulos.add(new Titulo());
        listaTitulos.add(new Titulo());

        // Simula el comportamiento del repositorio al listar títulos
        Mockito.when(tituloRepositorio.findAll()).thenReturn(listaTitulos);

        // Llama al método del controlador
        String resultado = tituloController.showTitulos(model);

        // Verifica que la vista devuelta sea la esperada
        Assertions.assertEquals("title/titlesA", resultado);

        // Verifica que el modelo tenga el atributo "titulos" con la lista de títulos
        Mockito.verify(model, Mockito.times(1)).addAttribute(ArgumentMatchers.eq("titulos"), ArgumentMatchers.anyList());
    }

}
