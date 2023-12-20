package bibliotecagranvia;


import com.example.bibliotecagranvia.controladores.PrestamoController;
import com.example.bibliotecagranvia.entidades.Prestamo;
import com.example.bibliotecagranvia.persistencia.PrestamoRepositorio;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;
import com.example.bibliotecagranvia.persistencia.RepositorioUsuario;
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

class PrestatesControllerTest {

    @Mock
    private PrestamoRepositorio prestamoRepositorio;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private TituloRepositorio tituloRepositorio;

    @Mock
    private Model model;

    @InjectMocks
    private PrestamoController prestamoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMostrarPrestamosBiblio() {
        // Simular lista de préstamos
        List<Prestamo> listaPrestamos = new ArrayList<>();
        listaPrestamos.add(new Prestamo());
        listaPrestamos.add(new Prestamo());

        Mockito.when(prestamoRepositorio.findAll()).thenReturn(listaPrestamos);

        String resultado = prestamoController.mostrarPrestamosBiblio(model);

        Assertions.assertEquals("lend/lendsB", resultado);

        Mockito.verify(model, Mockito.times(1)).addAttribute(ArgumentMatchers.eq("listaPrestamo"), ArgumentMatchers.anyList());
    }

    // Puedes agregar más pruebas para los demás métodos del controlador siguiendo un enfoque similar

    // ... Resto de las pruebas para los demás métodos del controlador
}
