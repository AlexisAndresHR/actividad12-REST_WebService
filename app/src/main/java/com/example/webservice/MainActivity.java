package com.example.webservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ListView lv_contacts_list;
    private ArrayAdapter adapter;
    private String getAllClientsURL = "http://192.168.100.3:8080/api_clientes?user_hash=dc243fdf1a24cbced74db81708b30788&action=get";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        lv_contacts_list = (ListView)findViewById(R.id.listview_clientes);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv_contacts_list.setAdapter(adapter);
        webServiceRest(getAllClientsURL);
    }

    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_cliente;
        String nombre;
        String apellido_pat;
        String apellido_mat;
        String telefono;
        String email;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_cliente = jsonObject.getString("id_cliente");
                nombre = jsonObject.getString("nombre");
                apellido_pat = jsonObject.getString("apellido_paterno");
                apellido_mat = jsonObject.getString("apellido_materno");
                telefono = jsonObject.getString("telefono");
                email = jsonObject.getString("email");
                adapter.add(id_cliente + "] " + nombre + " " + apellido_pat + " " + apellido_mat + "\n    " + telefono + " - " + email);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}
