package com.example.bibliotecagranvia.entidades;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name = "autor")
public class Autor {

    @Id  
    @SequenceGenerator(name="yourSequenceGenerator", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "yourTableGenerator")
    private Long id;
    @Column(name = "nombre")
    @NotBlank(message = "Nombre obligatorio")
    private String nombreAutor;
    @NotBlank(message = "Apellido obligatorio")
    private String apellidos;
    private Date fechaNacimiento;

    public Autor(){}

    public Autor(String nombreAutor, String apellidos){
        this.nombreAutor=nombreAutor;
        this.apellidos=apellidos;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }

    public String getNombreAutor(){
        return nombreAutor;
    }
    public void setNombreAutor(String nombreAutor){
        this.nombreAutor=nombreAutor;
    }

    public String getApellido(){
        return apellidos;
    }
    public void setApellido(String apellidos){
        this.apellidos=apellidos;
    }
    public Date getFechaNacimiento(){
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento){
        this.fechaNacimiento=fechaNacimiento;
    }
}
