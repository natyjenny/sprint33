package com.recreu.recreu.utilities;

import android.util.Log;
import com.recreu.recreu.Modelos.Actividad;
import com.recreu.recreu.Modelos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonHandler {


    // Recibo un JSONArray en forma de String y devuelve un array Actividades
    public Actividad[] getActividades(String actividades) {
        try {
            JSONArray ja = new JSONArray(actividades);
            Actividad[] arrayActividades = new Actividad[ja.length()];
            Actividad actividad;


            for (int i = 0; i < ja.length(); i++) {
                JSONObject jsonActividad = ja.getJSONObject(i);
                String dato=jsonActividad.getString("ubicacionActividadX");
                float x=Float.parseFloat(dato);

                dato=jsonActividad.getString("ubicacionActividadY");
                float y=Float.parseFloat(dato);

                JSONObject jsoncategoria = new JSONObject(jsonActividad.getString("tipo"));
                dato= jsoncategoria.getString("tipoId");
                int id_tipo=Integer.parseInt(dato);

                dato= jsonActividad.getString("actividadId");
                int ide_actividad=Integer.parseInt(dato);

                //dato= jsonActividad.getString("personasMaximas");
                // int cupos=Integer.parseInt(dato);

                actividad = new Actividad(jsonActividad.getString("tituloActividad"),jsonActividad.getString("cuerpoActividad"),jsonActividad.getString("requerimientosActividad"),jsonActividad.getString("fechaInicio"),jsonActividad.getString("duracionEstimada"),x,y,id_tipo,ide_actividad,666);
                //actividad = new Actividad(row.getString("tituloActividad"),row.getString("cuerpoActividad"),row.getString("requerimientosActividad"),null,null,x,y,null,ide_actividad,cupos);
                arrayActividades[i] = actividad;

            }
            return arrayActividades;


        } catch (JSONException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
            //   } catch (ParseException e) {
            //     e.printStackTrace();
        }
        return null;
    }


    public Usuario[] getIdesUsuariosEnAct(String usuarios) {
        try {
            JSONArray ja = new JSONArray(usuarios);
            Usuario[] arrayIdes = new Usuario[ja.length()];
            Usuario usuario;

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jsonUsuario = ja.getJSONObject(i);
                String dato= jsonUsuario.getString("usuarioId");
                int ide_usuario=Integer.parseInt(dato);
                jsonUsuario.getString("primerNombre");
                usuario = new Usuario(ide_usuario, jsonUsuario.getString("primerNombre"),jsonUsuario.getString("apellidoPaterno"));
                arrayIdes[i] = usuario;
            }
            return arrayIdes;

        } catch (JSONException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
            //   } catch (ParseException e) {
            //     e.printStackTrace();
        }
        return null;
    }
    public Usuario[] getUsuarios(String actividades) {
        try {
            JSONArray ja = new JSONArray(actividades);
            Usuario[] arrayUsuario = new Usuario[ja.length()];
            Usuario usuario;


            for (int i = 0; i < ja.length(); i++) {
                JSONObject jsonUsuario = ja.getJSONObject(i);
                usuario = new Usuario(jsonUsuario.getInt("usuarioId"),jsonUsuario.getString("primerNombre"),jsonUsuario.getString("apellidoPaterno"));
                arrayUsuario[i] = usuario;

            }
            System.out.println("Creadala lista de usuarios");
            return arrayUsuario;


        } catch (JSONException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        }
        return null;
    }

}