package com.example.examen_libro;

import com.example.examen_libro.enumerados.Pasillos;

import java.time.LocalDate;
import java.util.Objects;

public class Libro {
    private String autor, isbn;
    private String numero;
    private String lugarPublicacion;
    private String titulo;
    private Pasillos pasillo;
    private String editorial;
    private Integer paginas;
    private Integer stock;
    private Float pvp;
    private LocalDate anyoPublicacion;
    private LocalDate anyoEdicion;

    public Libro(String autor, String isbn, String numero, String lugarPublicacion, String titulo, Pasillos pasillo, String editorial, Integer paginas, Float pvp, Integer stock, LocalDate anyoPublicacion, LocalDate anyoEdicion) {
        this.autor = autor;
        this.isbn = isbn;
        this.numero = numero;
        this.lugarPublicacion = lugarPublicacion;
        this.titulo = titulo;
        this.pasillo = pasillo;
        this.editorial = editorial;
        this.paginas = paginas;
        this.pvp = pvp;
        this.stock = stock;
        this.anyoPublicacion = anyoPublicacion;
        this.anyoEdicion = anyoEdicion;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLugarPublicacion() {
        return lugarPublicacion;
    }

    public void setLugarPublicacion(String lugarPublicacion) {
        this.lugarPublicacion = lugarPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Pasillos getPasillo() {
        return pasillo;
    }

    public void setPasillo(Pasillos pasillo) {
        this.pasillo = pasillo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getPvp() {
        return pvp;
    }

    public void setPvp(Float pvp) {
        this.pvp = pvp;
    }

    public LocalDate getAnyoPublicacion() {
        return anyoPublicacion;
    }

    public void setAnyoPublicacion(LocalDate anyoPublicacion) {
        this.anyoPublicacion = anyoPublicacion;
    }

    public LocalDate getAnyoEdicion() {
        return anyoEdicion;
    }

    public void setAnyoEdicion(LocalDate anyoEdicion) {
        this.anyoEdicion = anyoEdicion;
    }

    public Libro() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(autor, libro.autor) && Objects.equals(isbn, libro.isbn) && Objects.equals(numero, libro.numero) && Objects.equals(lugarPublicacion, libro.lugarPublicacion) && Objects.equals(titulo, libro.titulo) && pasillo == libro.pasillo && Objects.equals(editorial, libro.editorial) && Objects.equals(paginas, libro.paginas) && Objects.equals(stock, libro.stock) && Objects.equals(pvp, libro.pvp) && Objects.equals(anyoPublicacion, libro.anyoPublicacion) && Objects.equals(anyoEdicion, libro.anyoEdicion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(autor, isbn, numero, lugarPublicacion, titulo, pasillo, editorial, paginas, stock, pvp, anyoPublicacion, anyoEdicion);
    }
}




