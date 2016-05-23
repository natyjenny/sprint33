package com.recreu.recreu.controllers;


import android.content.Context;
import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ginebra on 21-05-16.
 */
public class HttpPut extends AsyncTask<String, Void, String> {
    private String url;
    private String json;
    private HttpURLConnection httpCon;

    public HttpPut(Context context) {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        this.json=params[0];
        this.url= params[1];
        URL url2;
        try {
            url2 = new URL(url);
            httpCon = (HttpURLConnection) url2.openConnection();
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("PUT");
            httpCon.setReadTimeout(10000);
            httpCon.setConnectTimeout(15000);
            httpCon.connect();

            OutputStream os = httpCon.getOutputStream();
            DataOutputStream osw = new DataOutputStream(os);
            osw.writeBytes(json);
            osw.flush();
            osw.close();

            int statusCode = httpCon.getResponseCode();
            if (statusCode >= 400) {
                return "Error interno del servidor";
            } else {
                if (statusCode==204 || statusCode==200) return "Se Modidico Actividad correctamente ;)";
                else return "Ocurrió un problema al Modificar ";
            }

        } catch (MalformedURLException e) {
            return "Error MalformedURLException: "+e.toString();
        } catch (IOException e) {
            return "Error IO: "+ e.toString();
        }catch (Exception e){
            return "Error Exception: "+e.toString();
        }
    }

    @Override
    protected void onPostExecute(String requestresult) {
        super.onPostExecute(requestresult);
        httpCon.disconnect();
    }




}
