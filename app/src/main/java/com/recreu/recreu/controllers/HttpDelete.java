package com.recreu.recreu.controllers;

/**
 * Created by alamatita on 22-05-16.
 */
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class HttpDelete extends AsyncTask<String, Void, String> {

    private Context context;

    /**
     * Constructor
     */
    public HttpDelete(Context context) {
        this.context = context;
    }// HttpGet(Context context)

    /*** Método que realiza la petición al servidor */
    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("DELETE");
            connection.setDoInput(true);
            connection.connect();
            int statusCode = connection.getResponseCode();
            if (statusCode==200) return "OK";
            return "ERROR";
        } catch (MalformedURLException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (ProtocolException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (IOException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        }
        return null;
    }// doInBackground(String... urls)

    /**
     * Método que manipula la respuesta del servidor
     */
    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent("httpData").putExtra("data", result);
        context.sendBroadcast(intent);
        System.out.println("ESTADO DE LA CONSULTA DELETE : "+result);
    }// onPostExecute(String result)

}