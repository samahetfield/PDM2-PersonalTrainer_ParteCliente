package com.example.sergi.partecliente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.sergi.partecliente.db.AppDatabase;
import com.example.sergi.partecliente.db.User;
import com.example.sergi.partecliente.db.utils.DatabaseInitializer;

import java.util.List;

public class Login extends AppCompatActivity {
    private static final String PREFS_KEY = "LOGIN";
    String us_save, pass_save, bd_create="";
    EditText login, password;
    CheckBox sesion_activa;
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        us_save = leerValor(getApplicationContext(),"Usuario");
        pass_save = leerValor(getApplicationContext(), "Password");
        bd_create = leerValor(getApplicationContext(), "Database");

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        login = (EditText) findViewById(R.id.user_in);
        password = (EditText) findViewById(R.id.pass_in);
        sesion_activa = (CheckBox) findViewById(R.id.sesion_activa);

        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());

        DatabaseInitializer.populateSync(mDb);

        if(us_save != ""){
            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            Bundle extras = new Bundle();
            extras.putString("USER", us_save);
            in.putExtras(extras);
            startActivity(in);
        }
    }


    public static void guardarValor(Context context, String keyPref, String valor) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString(keyPref, valor);
        editor.commit();
    }

    public static String leerValor(Context context, String keyPref) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return  preferences.getString(keyPref, "");
    }

    public void entrar(View v){
        String us = login.getText().toString();
        List<User> usuario = mDb.userModel().getLogin(login.getText().toString(), password.getText().toString());

        if (sesion_activa.isChecked()){
            guardarValor(getApplicationContext(), "Usuario", login.getText().toString());
            guardarValor(getApplicationContext(), "Password", password.getText().toString());
        }

        if(usuario.size() > 0){
            guardarValor(getApplicationContext(), "Database", "bdcreada");
            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            Bundle extras = new Bundle();
            extras.putString("USER", us);
            in.putExtras(extras);
            startActivity(in);
        }
    }
}