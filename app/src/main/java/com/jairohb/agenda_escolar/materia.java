package com.jairohb.agenda_escolar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

public class materia extends Activity {
    private Session session;
    DatabaseHelper db;
    EditText tarea, materia1, desc, fecha;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "materia";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_materia);

        session = new Session(this);
        String usn = session.getusername();
        db= new DatabaseHelper(this);

        tarea=(EditText)findViewById(R.id.txttitulo);
        materia1=(EditText)findViewById(R.id.txtmateria);
        desc=(EditText)findViewById(R.id.txtdesc);
        fecha=(EditText)findViewById(R.id.txtfecha);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        materia.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                fecha.setText(date);
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_addtar_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tar = tarea.getText().toString();
                String mat = materia1.getText().toString();
                String des = desc.getText().toString();
                String fec = fecha.getText().toString();
                Integer userid = Integer.valueOf(session.getid());
                if(tar.equals("") || mat.equals("") || des.equals("") || fec.equals("")){
                    Toast.makeText(getApplicationContext(),"No dejes ningun campo vacio", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean insert1 = db.insertmat(userid,mat,tar,des,fec);
                    if(insert1==false){
                        Toast.makeText(getApplicationContext(),"Materia Registrada Con Exito", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        ImageButton btnback = (ImageButton) findViewById(R.id.imgbtnbackmat);
        btnback.setImageResource(R.drawable.ic_action_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(materia.this, menu.class);
                startActivity(intent);
            }
        });
    }
}
