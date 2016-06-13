package com.recreu.recreu.Modelos;

import java.io.Serializable;

/**
 *
public class Usuario {
    private int id;
    public int getId() {
        return id;
    }
}
*/

import java.io.Serializable;

/**
 * Created by alamatita on 13-05-16.
 */
public class Usuario implements Serializable {

    private String apellidoMaterno;
    private String apellidoPaterno;
    private String correo;
    private String fechaNacimiento;
    private String password;
    private String primerNombre;
    private String segundoNombre;
    private boolean sexo;
    private String intereses; //separados por coma
    private float lastPositionX;
    private float lastPositionY;
    private String lastUpdate;
    private String numeroTelefono;
    private int usuarioId;
    private boolean esAdministrador;
    private Carrera carrera;

    private String createdAt;
    private boolean disponibilidad;
    private boolean esActivo;

    public Usuario(){
        apellidoMaterno="cespedes";
        apellidoPaterno="vilches";
        primerNombre="felipe";
        segundoNombre="chris";
        correo="correo.sin.arroba.usach";
        password="pass.minimo.6.caracteres";
        fechaNacimiento="1991-10-24";
        sexo= true;
        esAdministrador=false;
    }

    public void agregarDatos(String lastUpdate, int usuarioId, String createdAt, boolean disponibilidad, boolean esActivo){
        this.lastUpdate=lastUpdate;

        this.usuarioId=usuarioId;
        this.createdAt=createdAt;
        this.disponibilidad=disponibilidad;
        this.esActivo=esActivo;


    }

    public Usuario(String apellidoMaterno, String apellidoPaterno, String primerNombre, String segundoNombre, String correo, String password, String fechaNacimiento,boolean sexo, boolean isAd){
        this.apellidoMaterno=apellidoMaterno;
        this.apellidoPaterno=apellidoPaterno;
        this.primerNombre=primerNombre;
        this.segundoNombre=segundoNombre;
        this.correo=correo;
        this.password=password;
        this.fechaNacimiento=fechaNacimiento;
        this.sexo=sexo;
        this.esAdministrador=isAd;
    }
    public Usuario(int ide, String nomb, String apelli,String apelli2, String correo, String Nacimiento, String telefono, String intereses){
        this.usuarioId=ide;
        this.primerNombre=nomb;
        this.apellidoPaterno=apelli;
        this.numeroTelefono=telefono;
        this.intereses=intereses;
        this.correo=correo;
        this.fechaNacimiento=Nacimiento;
        this.apellidoMaterno=apelli2;
    }


    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public Carrera getCarrera() {
        return carrera;
    }



    public String getCorreo() {
        return correo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isEsAdministrador() {
        return esAdministrador;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public void setIntereses(String intereses) {
        this.intereses = intereses;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastPositionY(float lastPositionY) {
        this.lastPositionY = lastPositionY;
    }

    public void setLastPositionX(float lastPositionX) {
        this.lastPositionX = lastPositionX;
    }

    public boolean isEsActivo() {
        return esActivo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getIntereses() {
        return intereses;
    }

    public float getLastPositionX() {
        return lastPositionX;
    }

    public float getLastPositionY() {
        return lastPositionY;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getPassword() {
        return password;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public boolean isSexo() {
        return sexo;
    }

    public int getUsuarioId() {
        return usuarioId;
    }
    public int getId() {
        return usuarioId;
    }


}

