package com.registro;

import com.registro.usuario.Usuario;
import com.registro.usuario.RepositorioUsuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RepositorioUsuarioTest {
    @Autowired private RepositorioUsuario repo;

    @Test
    public void testAddNew() {
        Usuario usuario = new Usuario();
        usuario.setEmail("alex.stevenson@gmail.com");
        usuario.setcontraseña("alex123456");
        usuario.setprimerNombre("Alex");
        usuario.setApellidos("Stevenson");

        Usuario savedUsuario = repo.save(usuario);

        Assertions.assertThat(savedUsuario).isNotNull();
        Assertions.assertThat(savedUsuario.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<Usuario> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for (Usuario usuario : users) {
            System.out.println(usuario);
        }
    }

    @Test
    public void testUpdate() {
        Integer userId = 1;
        Optional<Usuario> optionalUser = repo.findById(userId);
        Usuario usuario = optionalUser.get();
        usuario.setcontraseña("hello2000");
        repo.save(usuario);

        Usuario updatedUsuario = repo.findById(userId).get();
        Assertions.assertThat(updatedUsuario.getcontraseña()).isEqualTo("hello2000");
    }

    @Test
    public void testGet() {
        Integer userId = 2;
        Optional<Usuario> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Integer userId = 2;
        repo.deleteById(userId);

        Optional<Usuario> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}
