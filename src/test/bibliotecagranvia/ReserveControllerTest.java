package bibliotecagranvia;


import com.example.bibliotecagranvia.controladores.ReservaController;
import com.example.bibliotecagranvia.entidades.Reserva;
import com.example.bibliotecagranvia.persistencia.ReservaRepositorio;
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

class ReserveControllerTest {

    @Mock
    private ReservaRepositorio reservaRepositorio;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private TituloRepositorio tituloRepositorio;

    @Mock
    private Model model;

    @InjectMocks
    private ReservaController reservaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMostrarReservas() {
        // Simular lista de reservas
        List<Reserva> listaReservas = new ArrayList<>();
        listaReservas.add(new Reserva());
        listaReservas.add(new Reserva());

        Mockito.when(reservaRepositorio.findAll()).thenReturn(listaReservas);

        String resultado = reservaController.mostrarReservas(model);

        Assertions.assertEquals("booking/bookingsB", resultado);

        Mockito.verify(model, Mockito.times(1)).addAttribute(ArgumentMatchers.eq("listaReservas"), ArgumentMatchers.anyList());
    }

}
