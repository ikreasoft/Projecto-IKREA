package bibliotecagranvia;

import com.example.bibliotecagranvia.controladores.AutorController;
import com.example.bibliotecagranvia.entidades.Autor;
import com.example.bibliotecagranvia.persistencia.AutorRepositorio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AutorControllerTest {

    // Anotaciones Mock y InjectMocks de Mockito para inicializar los objetos simulados
    @Mock
    private AutorRepositorio authorRepository;

    @Mock
    private Model model;

    @InjectMocks
    private AutorController autorController;

    // Método de inicialización a ejecutar antes de cada prueba
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Prueba para verificar el método showHomePage del controlador
    @Test
    void testShowHomePage() {
        // Mock de la lista de autores
        List<Autor> autores = new ArrayList<>();
        autores.add(new Autor());

        // Configuración del comportamiento simulado del Repositorio de Autores
        Mockito.when(authorRepository.findAll()).thenReturn(autores);

        // Invocación del método del controlador que se está probando
        String result = autorController.showHomePage(model);

        // Verificación de la vista retornada
        Assertions.assertEquals("author/authorsA", result);

        // Verificación de que el modelo tenga el atributo "autores" con la lista de autores mockeada
        Mockito.verify(model, Mockito.times(1)).addAttribute(ArgumentMatchers.eq("autores"), ArgumentMatchers.anyList());
    }
}
