package com.example.bibliotecagranvia.controladores;

import com.example.bibliotecagranvia.entidades.Prestamo;
import com.example.bibliotecagranvia.entidades.Titulo;
import com.example.bibliotecagranvia.entidades.Usuario;
import com.example.bibliotecagranvia.persistencia.PrestamoRepositorio;
import com.example.bibliotecagranvia.persistencia.TituloRepositorio;
import com.example.bibliotecagranvia.persistencia.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PrestamoController {
    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private TituloRepositorio tituloRepositorio;

    // Constructor para inyectar el repositorio en el controlador (para obtener todos los préstamos)
    @GetMapping("checkLendBiblio")
    public String mostrarFormularioPrestamoBiblioteca() {
        return "lend/checkLendB";
    }
    @GetMapping("checkLendUser")
    public String mostrarFormularioPrestamoUsuario() {
        return "lend/checkLendU";
    }

    // GET method for showing the list of loans enabled by the librarian
    @GetMapping("prestamoListaBiblio")
    public String mostrarPrestamosBiblio(Model model) {
        List<Prestamo> listaPrestamo = (List<Prestamo>) prestamoRepositorio.findAll(); // Obtener todas los prestamos
        model.addAttribute("listaPrestamo", listaPrestamo);
        return "lend/lendsB";
    }
     @GetMapping("prestamoListaUser")
    public String mostrarPrestamosUser(Model model) {
        List<Prestamo> listaPrestamo = ((List<Prestamo>) prestamoRepositorio.findAll()).stream().filter(p->p.getUsuario().getId()==2).collect(Collectors.toList()); // Obtener todos los prestamos del usuario con id1
        model.addAttribute("listaPrestamo", listaPrestamo);
        return "lend/lendsU";
    }

    // GET method for showing the list of loans enabled by the librarian
    @GetMapping("/indexLendBiblio")
    public String gestion_prestamos_Biblio() {
        return "lend/indexLendB"; // HTML con el menú de opciones
    }
    @GetMapping("/indexLendUser")
    public String gestion_prestamos_User() {
        return "lend/indexLendU"; // HTML con el menú de opciones
    }

    @GetMapping("/confirmacion_prestamoBiblio")
    public String confirmacionPrestamoBiblio() {
        return "lend/confirmationLendB"; // HTML con el menú de opciones
    }
    @GetMapping("/confirmacion_prestamoUser")
    public String confirmacionPrestamoUser() {
        return "lend/confirmationLendU"; // HTML con el menú de opciones
    }
    @GetMapping("/titulo_ya_prestadoBiblio")
    public String titulo_ya_prestadoBiblio() {
        return "lend/lendedTitleB"; // HTML con el menú de opciones
    }
    @GetMapping("/titulo_ya_prestadoUser")
    public String titulo_ya_prestadoUser() {
        return "lend/lendedTitleU"; // HTML con el menú de opciones
    }

    // GET method for manage 'prestamo' option
    @PostMapping("/prestamoBiblio")
    public String confirmarPrestamo(@RequestParam String nombreUsuario, @RequestParam String tituloNombre, Model model) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findByNombre(nombreUsuario);
        Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Titulo titulo = tituloOptional.get();

            // Obtener la fecha actual como fecha de préstamo
            Date fechaPrestamo = new Date();

            // Calcular la fecha de devolución sumando 15 días a la fecha de préstamo
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaPrestamo);
            calendar.add(Calendar.DATE, 15);
            Date fechaDevolucion = calendar.getTime();

            // Agregar usuario, título, fecha de préstamo y fecha de devolución al modelo
            model.addAttribute("usuario", usuario);
            model.addAttribute("titulo", titulo);
            model.addAttribute("fechaPrestamo", fechaPrestamo);
            model.addAttribute("fechaDevolucion", fechaDevolucion);

            return "lend/confirmLend"; // Redirigir a la página de confirmación
        } else {
            return "redirect:/userTitleNotFoundBiblio"; // Página de error si no se encuentran usuario o título
        }
    }
    @PostMapping("/prestamoUser")
    public String confirmarPrestamoUser(@RequestParam String tituloNombre, Model model) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findById(2);
        Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Titulo titulo = tituloOptional.get();

            // Obtener la fecha actual como fecha de préstamo
            Date fechaPrestamo = new Date();

            // Calcular la fecha de devolución sumando 15 días a la fecha de préstamo
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaPrestamo);
            calendar.add(Calendar.DATE, 15);
            Date fechaDevolucion = calendar.getTime();

            // Agregar usuario, título, fecha de préstamo y fecha de devolución al modelo
            model.addAttribute("usuario", usuario);
            model.addAttribute("titulo", titulo);
            model.addAttribute("fechaPrestamo", fechaPrestamo);
            model.addAttribute("fechaDevolucion", fechaDevolucion);

            return "lend/confirmLendU"; // Redirigir a la página de confirmación
        } else {
            return "redirect:/userTitleNotFoundUser"; // Página de error si no se encuentran usuario o título
        }
    }

    // POST method for manage 'prestamo' option
    @PostMapping("/prestarBiblio")
    public String prestarLibro(@RequestParam String nombreUsuario, @RequestParam String tituloNombre, Model model) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findByNombre(nombreUsuario);
        Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
            Titulo titulo = tituloOptional.get();

            // Verificar si el título está disponible
            if (titulo.getNumReservas() > 0) {
                Usuario usuario = usuarioOptional.get();

                // Reducir la cantidad disponible en 1
                int cantidadDisponible = titulo.getNumReservas();
                titulo.setNumReservas(cantidadDisponible - 1);
                // Crear un nuevo préstamo

                Prestamo prestamo = new Prestamo();
                prestamo.setUsuario(usuario); // Asignar el usuario al préstamo
                prestamo.setTitulo(titulo); // Asignar el título al préstamo
                prestamo.setNombreUsuario(usuario.getNombre()); // Obtener el nombre del usuario
                prestamo.setNombreTitulo(titulo.getNombre()); // Obtener el nombre del título
                prestamo.setIsbn(titulo.getIsbn()); // Establecer el ISBN del libro

                // Obtener la fecha actual como fecha de inicio del préstamo
                Date fechaInicioPrestamo = new Date();
                prestamo.setFechaPrestamo(fechaInicioPrestamo); // Establecer la fecha de inicio del préstamo

                // Calcular la fecha de devolución (15 días después del préstamo)
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR, 15);
                Date fechaDevolucion = calendar.getTime();
                prestamo.setFechaDevolucion(fechaDevolucion); // Establecer la fecha de devolución

                tituloRepositorio.save(titulo);
                prestamoRepositorio.save(prestamo);

                return confirmacionPrestamoBiblio();
            } else {
                return titulo_ya_prestadoBiblio();
            }
        } else {
            return "redirect:/userTitleNotFoundBiblio";
        }
    }

    @PostMapping("/prestarUser")
    public String prestarLibroUser(@RequestParam String tituloNombre, Model model) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findById(2);
        Optional<Titulo> tituloOptional = tituloRepositorio.findByNombre(tituloNombre);

        if (usuarioOptional.isPresent() && tituloOptional.isPresent()) {
            Titulo titulo = tituloOptional.get();

            // Verificar si el título está disponible
            if (titulo.getNumReservas() > 0) {
                Usuario usuario = usuarioOptional.get();
                // Reducir la cantidad disponible en 1
                int cantidadDisponible = titulo.getNumReservas();
                titulo.setNumReservas(cantidadDisponible - 1);
                // Crear un nuevo préstamo

                Prestamo prestamo = new Prestamo();
                prestamo.setUsuario(usuario); // Asignar el usuario al préstamo
                prestamo.setTitulo(titulo); // Asignar el título al préstamo
                prestamo.setNombreUsuario(usuario.getNombre()); // Obtener el nombre del usuario
                prestamo.setNombreTitulo(titulo.getNombre()); // Obtener el nombre del título
                prestamo.setIsbn(titulo.getIsbn()); // Establecer el ISBN del libro

                // Obtener la fecha actual como fecha de inicio del préstamo
                Date fechaInicioPrestamo = new Date();
                prestamo.setFechaPrestamo(fechaInicioPrestamo); // Establecer la fecha de inicio del préstamo

                // Calcular la fecha de devolución (15 días después del préstamo)
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR, 15);
                Date fechaDevolucion = calendar.getTime();
                prestamo.setFechaDevolucion(fechaDevolucion); // Establecer la fecha de devolución

                tituloRepositorio.save(titulo);
                prestamoRepositorio.save(prestamo);

                return confirmacionPrestamoUser();
            } else {
                return titulo_ya_prestadoUser();
            }
        } else {
            return "redirect:/userTitleNotFound";
        }
    }

    // POST method for showing the list of loans enabled by the librarian and the user can return it
    @PostMapping("/devolverBiblio")
    public String devolverLibroBiblio(@RequestParam Long prestamoId) {
        Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(prestamoId);

        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();
            Titulo titulo = prestamo.getTitulo();

            // Incrementar la cantidad disponible en 1
            int cantidadDisponible = titulo.getNumReservas();
            titulo.setNumReservas(cantidadDisponible + 1);

            tituloRepositorio.save(titulo);

            prestamoRepositorio.delete(prestamo); // Eliminar el préstamo de la base de datos
            return "redirect:/prestamoListaBiblio"; // Redirigir a la lista de préstamos actualizada
        } else {
            return "redirect:/prestamo_no_encontrado";
        }
    }

    @PostMapping("/devolverUser")
    public String devolverLibroUser(@RequestParam Long prestamoId) {
        Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(prestamoId);

        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();
            Titulo titulo = prestamo.getTitulo();

            // Incrementar la cantidad disponible en 1
            int cantidadDisponible = titulo.getNumReservas();
            titulo.setNumReservas(cantidadDisponible + 1);

            tituloRepositorio.save(titulo);

            prestamoRepositorio.delete(prestamo); // Eliminar el préstamo de la base de datos
            return "redirect:/prestamoListaUser"; // Redirigir a la lista de préstamos actualizada
        } else {
            return "redirect:/prestamo_no_encontrado";
        }
    }

    @GetMapping("/prestamo_no_encontrado")
    public String prestamo_no_encontrado() {
        return "lend/lendNotFound"; // HTML con el menú de opciones
    }

    // POST method for user can renew a loan
    @PostMapping("/renovarBiblio")
    public String actualizarPrestamo(@RequestParam Long prestamoId, Model model) {
        Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(prestamoId);

        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();

            // Actualizar la fecha de préstamo (a la fecha actual)
            Date nuevaFechaPrestamo = new Date();
            prestamo.setFechaPrestamo(nuevaFechaPrestamo);

            // Calcular y actualizar la nueva fecha de devolución (por ejemplo, sumando 15 días a la fecha actual)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nuevaFechaPrestamo);
            calendar.add(Calendar.DAY_OF_YEAR, 15);
            Date nuevaFechaDevolucion = calendar.getTime();
            prestamo.setFechaDevolucion(nuevaFechaDevolucion);

            // Guardar los cambios en el préstamo en la base de datos
            prestamoRepositorio.save(prestamo);

            // Agregar el préstamo actualizado al modelo si es necesario mostrarlo en la vista
            model.addAttribute("prestamo", prestamo);

            return renovacionExitosaBiblio(); // Página de confirmación de actualización
        } else {
            return "redirect:/lendNotFound";
        }
    }
    @PostMapping("/renovarUser")
    public String actualizarPrestamoUser(@RequestParam Long prestamoId, Model model) {
        Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(prestamoId);

        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();

            // Actualizar la fecha de préstamo (a la fecha actual)
            Date nuevaFechaPrestamo = new Date();
            prestamo.setFechaPrestamo(nuevaFechaPrestamo);

            // Calcular y actualizar la nueva fecha de devolución (por ejemplo, sumando 15 días a la fecha actual)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nuevaFechaPrestamo);
            calendar.add(Calendar.DAY_OF_YEAR, 15);
            Date nuevaFechaDevolucion = calendar.getTime();
            prestamo.setFechaDevolucion(nuevaFechaDevolucion);

            // Guardar los cambios en el préstamo en la base de datos
            prestamoRepositorio.save(prestamo);

            // Agregar el préstamo actualizado al modelo si es necesario mostrarlo en la vista
            model.addAttribute("prestamo", prestamo);

            return renovacionExitosaUser(); // Página de confirmación de actualización
        } else {
            return "redirect:/lendNotFound";
        }
    }

    // GET method for renew a loan successfully
    @GetMapping("/renovacion_exitosaBiblio")
    public String renovacionExitosaBiblio() {
        return "lend/renovationSuccesfulB"; // HTML con el menú de opciones
    }
     @GetMapping("/renovacion_exitosaUser")
    public String renovacionExitosaUser() {
        return "lend/renovationSuccesfulU"; // HTML con el menú de opciones
    }

}