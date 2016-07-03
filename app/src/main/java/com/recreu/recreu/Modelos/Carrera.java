package com.recreu.recreu.Modelos;


import java.io.Serializable;

public class Carrera implements Serializable{
    private int carreraId;
    private String nombreCarrera;

    public Carrera(String nombre, int id){
        this.carreraId=id;
        this.nombreCarrera=nombre;

    }
    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public int getCarreraId() {
        return carreraId;
    }
}