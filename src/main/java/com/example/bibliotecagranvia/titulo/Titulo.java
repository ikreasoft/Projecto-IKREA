package com.example.bibliotecagranvia.titulo;


import jakarta.persistence.*;

@Entity
@Table(name = "titulo")
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titulo;
    private String isbn;
    private String numReserva;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Titulo(){}
    public Titulo(String titulo, String isbn, String numReserva, Autor autor){
        this.titulo=titulo;
        this.isbn=isbn;
        this.numReserva=numReserva;
        this.autor=autor;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo(){
        return titulo;
    }
    public void setTitulo(String titulo){
        this.titulo=titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNumReserva() {
        return numReserva;
    }

    public void setNumReserva(String numReserva) {
        this.numReserva = numReserva;
    }

    public Autor getAutor(){
        return autor;
    }
    public void setAutor(Autor autor){
        this.autor=autor;
    }
}
