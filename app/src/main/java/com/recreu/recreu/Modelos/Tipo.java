package com.recreu.recreu.Modelos;

/**
 * Created by alamatita on 22-05-16.
 */
public class Tipo {
    private int tipoId;
    private String nombre;
    private String nombreTipo;
    private Categoria categoria;

    public Tipo(String nombre,int tipoId){
        this.nombreTipo=nombre;
        this.tipoId=tipoId;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getTipoId() {
        return tipoId;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }


}