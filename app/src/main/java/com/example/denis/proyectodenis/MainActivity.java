package com.example.denis.proyectodenis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> datos ;
    private ListView lv1;
    private  ArrayAdapter <String> adapter;
    private SharedPreferences prefe1;
    private Date fechayhora;
    private static String fecha, hora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mostrarLista();
        TextView footerView = (TextView) getLayoutInflater().inflate(R.layout.footer_view, null,true);
        lv1.addFooterView(footerView);
        lv1.setFooterDividersEnabled(true);
        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final int posicion=i;
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Eliminar este elemento ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        String elemento = datos.get(posicion);
                        String [] arrayElementos = elemento.split(" -- ");
                        prefe1=getSharedPreferences("Datos", Context.MODE_PRIVATE);
                        SharedPreferences.Editor elePrefe=prefe1.edit();
                        elePrefe.remove(arrayElementos[0]);
                        elePrefe.commit();
                        adapter.notifyDataSetChanged();
                        adapter.clear();
                        mostrarLista();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
                return true;
            }
        });
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int i, long l) {
                final int posicion2 = i;
                String elemento = datos.get(posicion2);
                Intent a =new Intent(MainActivity.this,editActivity.class);
                a.putExtra("elementoClicado", elemento.toString());
                startActivity(a);

            }
        });
    }

    protected void onRestart() {
        super.onRestart();
        adapter.clear();
        mostrarLista();
    }

    public void openAdd(View v){
        //Creamos un intent para convocar la activity
        Intent addI = new Intent(this,addActivity.class);
        //Iniciamos la activity
        startActivity(addI);
    }

    private void leerSharedPreferences() {
        prefe1=getSharedPreferences("Datos", Context.MODE_PRIVATE);
        Map<String,?> claves = prefe1.getAll();
        for(Map.Entry<String,?> ele : claves.entrySet()){
            Gson gson = new Gson(); //Instancia Gson.
            String nameKey = String.valueOf(ele.getKey());
            String json = prefe1.getString(nameKey,"");
            ToDoItem obj1 = gson.fromJson(json, ToDoItem.class);
            fecha  = obj1.getmDate();
            hora = obj1.getmTime();
            datos.add(ele.getKey()+" -- \n" +obj1.getmPriority().toString()+" -- "+obj1.getmStatus().toString()+" -- "+fecha + " -- " + hora);
        }
    }

    private void mostrarLista(){
        datos=new ArrayList<String>();
        leerSharedPreferences();
        lv1 = (ListView) findViewById(R.id.lv1);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);
        lv1.setAdapter(adapter);


    }

    private static void setFecha(int year, int monthOfYear, int dayOfMonth) {
        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;
        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        fecha = day + "/" + mon + "/" + year;
    }

    private static void setHora(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;
        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;
        hora = hour + ":" + min;
    }
}
