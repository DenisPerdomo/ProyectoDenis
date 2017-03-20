package com.example.denis.proyectodenis;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;


import java.util.Calendar;
import java.util.Date;

import static com.example.denis.proyectodenis.R.id.lv1;
import static com.example.denis.proyectodenis.R.id.radioDone;
import static com.example.denis.proyectodenis.R.id.radioStatus;

public class addActivity extends AppCompatActivity {
    private EditText etTitle;
    private RadioButton radioDone, radioNotDone, radioLow, radioMedium, radioHigh;
    private Date mDate;
    private TextView dateView, timeView;
    private static String dateString, timeString;
    private static final int SEVEN_DAYS = 604800000;
    DatePickerDialog datePickerDialog;
    TimePickerDialog mTimePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //Relacionamos las variables con los objetos del formulario.
        etTitle = (EditText) findViewById(R.id.etTitle);
        radioDone = (RadioButton) findViewById(R.id.radioDone);
        radioNotDone = (RadioButton) findViewById(R.id.radioNotDone);
        radioLow = (RadioButton) findViewById(R.id.radioLow);
        radioMedium = (RadioButton) findViewById(R.id.radioMedium);
        radioHigh = (RadioButton) findViewById(R.id.radioHigh);
        dateView = (TextView)  findViewById(R.id.dateView);
        timeView = (TextView) findViewById(R.id.timeView);
        //Seleccionamos los radiobutton por defecto.
        radioNotDone.setChecked(true);
        radioMedium.setChecked(true);
        //Escribimos la fecha y hora por defecto
        setDefaultDateTime();
        //Cuando clicas sobre el textview de la fecha.
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(addActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear++;
                                String mon = "" + monthOfYear;
                                String day = "" + dayOfMonth;
                                if (monthOfYear < 10)
                                    mon = "0" + monthOfYear;
                                if (dayOfMonth < 10)
                                    day = "0" + dayOfMonth;
                                // set day of month , month and year value in the edit text
                                dateView.setText(day + "/" + mon + "/" + year);
                                dateString = day + "/" + mon + "/" + year;

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //Cuando clicas sobre el textview de la hora.
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                mTimePicker = new TimePickerDialog(addActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hora;
                        String minuto;
                        if (selectedHour<10)
                            hora = "0" + selectedHour;
                        else{
                            hora = String.valueOf(selectedHour);
                        }
                        if (selectedMinute<10)
                            minuto = "0" + selectedMinute;
                        else{
                            minuto = String.valueOf(selectedMinute);
                        }
                        timeString =  hora + ":" + minuto;
                        timeView.setText( hora + ":" + minuto);
                    }
                }, hour, minute, true);
                mTimePicker.show();
            }
        });
    }


    public void agregar(View v) {
        //Guardamos el titulo en una variable de tipo String.
        String txtetTitle = etTitle.getText().toString();
        //Creamos una variable del tipo ToDoItem.Status para almacenar el estado.
        ToDoItem.Status Status = null;
        //Creamos una variable del tipo ToDoItem.Priority
        ToDoItem.Priority Priority = null;
        //Le damos el valor a la variable Status segun el radiobutton seleccionado.
        if (radioDone.isChecked() == true){
            Status = ToDoItem.Status.DONE;
        }else if (radioNotDone.isChecked() == true){
            Status = ToDoItem.Status.NOTDONE;
        }
        //Le damos el valor a la variable Priority segun el radiobutton que se haya seleccionado.
        if (radioLow.isChecked() == true){
            Priority = ToDoItem.Priority.LOW;
        }else if (radioMedium.isChecked() == true){
            Priority = ToDoItem.Priority.MED;
        }else if (radioHigh.isChecked() == true){
            Priority = ToDoItem.Priority.HIGH;
        }
        if (txtetTitle.isEmpty()){
            Toast.makeText(this, "Title is empty", Toast.LENGTH_SHORT).show();
        }else {
            //Creamos un objeto de tipo ToDoItem y le damos valor a sus atributos. Según lo que se haya elegido.
            ToDoItem obj1 = new ToDoItem(txtetTitle, Priority, Status,dateString, timeString);
            //Seleccionamos las preferencias en modo privado.
            SharedPreferences prefe1 = getSharedPreferences("Datos", Context.MODE_PRIVATE);
            //Editamos las preferencias
            SharedPreferences.Editor elemento = prefe1.edit();
            //Creamos un objeto de tipo Gson, para poder transformar los datos en una string con formato json.
            Gson gson = new Gson();
            //Transformamos el objeto obj1, a un string en formato json.
            String json = gson.toJson(obj1);
            //Lo añadimos a las preferencias.
            elemento.putString(txtetTitle, json);
            elemento.commit();
            //Ponermos el edittext en blanco.
            etTitle.setText("");
        }
    }

    public void back (View v){
        finish();
    }

    public void reset (View v){
        radioNotDone.setChecked(true);
        radioMedium.setChecked(true);
        setDefaultDateTime();
        etTitle.setText("");
    }
    private void setDefaultDateTime() {
        // Default is current time + 7 days
        mDate = new Date();
        mDate = new Date(mDate.getTime() + SEVEN_DAYS);
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        dateView.setText(dateString);
        setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                c.get(Calendar.MILLISECOND));
        timeView.setText(timeString);
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {
        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;
        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        dateString = day + "/" + mon + "/" + year;
    }

    private static void setTimeString(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;
        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;
        timeString = hour + ":" + min;
    }
}
