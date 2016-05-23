package com.recreu.recreu.Modelos;

/**
 * Created by alamatita on 22-05-16.
 */
public class Tipo {
    private int tipoId;
    private int categoriaId;
    private String nombreTipo;

    public Tipo(String nombre,int tipoId,int categoriaId){
        this.categoriaId=categoriaId;
        this.tipoId=tipoId;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public int getTipoId() {
        return tipoId;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }


}