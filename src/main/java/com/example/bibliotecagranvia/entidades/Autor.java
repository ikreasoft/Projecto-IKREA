package com.example.bibliotecagranvia.autor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
@Entity
@Table(name = "autor")
public class Autor {

    @Id  
    @SequenceGenerator(name="yourSequenceGenerator", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "yourTableGenerator")
    private Long id;
    @Column(name = "nombre")
    private String nombreAutor;
    private String apellido;

    public Autor(){}

    public Autor(String nombreAutor, String apellido){
        this.nombreAutor=nombreAutor;
        this.apellido=apellido;
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
        return apellido;
    }
    public void setApellido(String apellido){
        this.apellido=apellido;
    }
}
