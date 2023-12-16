package com.example.bibliotecagranvia.entidades;


import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "titulo")
public class Titulo {

    @Id
    @SequenceGenerator(name="yourSequenceGenerator", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "yourTableGenerator")
    private Long id;
    private String nombre;
    private String isbn;
    private String numReservas;
    @ManyToMany
    private List<Autor> autores;
    public Titulo(){
        autores= new ArrayList<Autor>();
    }
    public Titulo(String nombre, String isbn, String numReserva, List<Autor> autor){
        this.nombre=nombre;
        this.isbn=isbn;
        this.numReservas=numReserva;
        this.autores=autor;

    }
    private boolean disponible;

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    private int cantidadDisponible;

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNumReservas() {
        return numReservas;
    }

    public void setNumReservas(String numReserva) {
        this.numReservas = numReserva;
    }

    public List<Autor> getAutor(){
        return autores;
    }
    public void setAutor(List<Autor> autor){
        this.autores=autor;
    }
    public void addAutor(Autor autor){
        this.autores.add(autor);
    }
}
