package com.recreu.recreu.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * @author:
 */
public class HttpPost extends AsyncTask<String, Void, String>{
    private String json;
    private String url;
    private HttpURLConnection httpCon;
    private Context context;
    public HttpPost(Context context) {
        this.context = context;
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
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setUseCaches(false);
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestMethod("POST");
            httpCon.setReadTimeout(10000);
            httpCon.setConnectTimeout(15000);
            httpCon.connect();

            OutputStream os = httpCon.getOutputStream();
            DataOutputStream osw = new DataOutputStream(os);

            osw.writeBytes(json);
            osw.flush();
            osw.close();
            int statusCode = httpCon.getResponseCode();
            System.out.println("JSON: "+json);
            System.out.println("URL : "+ url);
            System.out.println("STATUS : "+statusCode);

            InputStream is = null;
            if (statusCode >= 400) {
                return "Error interno del servidor";

            } else {
                if (statusCode==204 || statusCode==200) return "Consulta realizada con exito";
                else return "Lo sentimos no se ha podido realizar la consulta ";

            }

        } catch (MalformedURLException e) {
            return "Error al crear la consulta: "+e.toString();
        } catch (IOException e) {
            return "Error IO: "+ e.toString();
        }catch (Exception e){
            return "Error Exception: "+e.toString();
        }
    }




    @Override
    protected void onPostExecute(String requestresult) {
        super.onPostExecute(requestresult);
        System.out.println(requestresult );
        String result;
            try {
                result =  new Scanner(httpCon.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            } catch (Exception e) {
                e.printStackTrace();
                result="{\"mensaje\":\""+requestresult+"\"}";
            }
            System.out.println("RESPUESTA desde httpPost: "+result);
            Intent intent = new Intent("httpPost").putExtra("jsonRespuesta", result);
            context.sendBroadcast(intent);
           httpCon.disconnect();
    }
}