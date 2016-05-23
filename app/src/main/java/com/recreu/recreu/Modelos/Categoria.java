package com.recreu.recreu.Modelos;

/**
 * Created by alamatita on 22-05-16.
 */
public class Categoria {

    private String nombreCategoria;
    private int categoriaId;


    public Categoria(String nombre,int ide){
        this.nombreCategoria=nombre;
        this.categoriaId=ide;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }
}
