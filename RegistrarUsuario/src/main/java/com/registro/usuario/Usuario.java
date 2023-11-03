package com.registro.usuario;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(length = 15, nullable = false)
    private String contraseña;

    @Column(length = 45, nullable = false, name = "primer_nombre")
    private String primerNombre;

    @Column(length = 45, nullable = false, name = "apellidos")
    private String Apellidos;

    @Column(length = 45, nullable = false, name = "direccion")
    private String Direccion;

    @Column(length = 45, nullable = false, name = "telefono")
    private String telefono;

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }



    private boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getcontraseña() {
        return contraseña;
    }

    public void setcontraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getprimerNombre() {
        return primerNombre;
    }

    public void setprimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", primerNombre='" + primerNombre + '\'' +
                ", Apellidos='" + Apellidos + '\'' +
                ", Direccion='" + Direccion + '\'' +
                ", Telefono='" + telefono + '\'' +
                '}';
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
