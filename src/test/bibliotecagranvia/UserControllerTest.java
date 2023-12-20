package bibliotecagranvia;

import com.example.bibliotecagranvia.controladores.ServicioUsuario;
import com.example.bibliotecagranvia.controladores.UserController;
import com.example.bibliotecagranvia.entidades.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private ServicioUsuario servicioUsuario;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testShowUserList() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        // Agrega usuarios de prueba a la lista
        listaUsuarios.add(new Usuario());
        listaUsuarios.add(new Usuario());

        // Simula el comportamiento del servicio al listar usuarios
        Mockito.when(servicioUsuario.listAll()).thenReturn(listaUsuarios);

        // Llama al método del controlador
        String resultado = userController.showUserList(model);

        // Verifica que la vista devuelta sea la esperada
        Assertions.assertEquals("user/usersA", resultado);

        // Verifica que el modelo tenga el atributo "listUsers" con la lista de usuarios
        Mockito.verify(model, Mockito.times(1)).addAttribute(ArgumentMatchers.eq("listUsers"), ArgumentMatchers.anyList());
    }

}
