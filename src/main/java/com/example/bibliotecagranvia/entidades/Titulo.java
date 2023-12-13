package com.example.bibliotecagranvia.entidades;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "titulo")
public class Titulo {

        @Id
        @SequenceGenerator(name="yourSequenceGenerator", allocationSize=1)
        @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "yourTableGenerator")
        @Column(name = "tituloId")
        private Long id;
        private String nombre;
        private String isbn;
        private int numReservas;
        @ManyToMany
        @JoinTable(
                name = "TituloAutor",
                joinColumns = @JoinColumn(name = "tituloId"),
                inverseJoinColumns = @JoinColumn(name = "autor_id"))
        private List<Autor> autores;
        public Titulo(){
            autores= new ArrayList<Autor>();
        }
        public Titulo(String nombre, String isbn, int numReserva, List<Autor> autor){
            this.nombre=nombre;
            this.isbn=isbn;
            this.numReservas=numReserva;
            this.autores=autor;

        }

        public Long getId() {
            return id;
        }

        public void setId(Long tituloId) {
            this.id = tituloId;
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

        public int getNumReservas() {
            return numReservas;
        }

        public void setNumReservas(int numReserva) {
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