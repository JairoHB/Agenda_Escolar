package com.jairohb.agenda_escolar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jairohb.agenda_escolar.utils.PreferenceUtils;

public class login extends AppCompatActivity {
    DatabaseHelper db;
    EditText username, password;
    Button btninicio;
    private Context mContext;

    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initViews();

        db= new DatabaseHelper(this);
        username=(EditText)findViewById(R.id.txtuser);
        password=(EditText)findViewById(R.id.txtpass);
        btninicio=(Button)findViewById(R.id.btn_inicio);
        session = new Session(this);

        btninicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String usn = username.getText().toString();
                String pas = password.getText().toString();
                String login1 = db.login_name(usn, pas);
                //username=jairo27 pass=123
                    if(!login1.equals("0")){
                        Toast.makeText(getApplicationContext(),"Incio Sesión Con Exito", Toast.LENGTH_SHORT).show();
                        session.setid(login1);
                        session.setusername(usn);
                        PreferenceUtils.saveuser(usn, getApplicationContext());
                        PreferenceUtils.savePassword(pas, getApplicationContext());
                        Intent intent = new Intent(login.this, menu.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Contraseña o Usuario Incorrecto", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    public void registeractivity(View v){
        TextView register = (TextView) findViewById(R.id.txtregister);
        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
    }

    private void initViews(){
        PreferenceUtils utils = new PreferenceUtils();
        if (utils.getuser(this) != null ){
            Intent intent = new Intent(login.this, menu.class);
            startActivity(intent);
        }else{

        }
    }
}
