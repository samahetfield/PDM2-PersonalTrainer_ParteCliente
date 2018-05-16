package com.example.sergi.partecliente;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergi.partecliente.db.AppDatabase;
import com.example.sergi.partecliente.db.Ejercicios;
import com.example.sergi.partecliente.db.Rutina;
import com.example.sergi.partecliente.db.User;
import com.example.sergi.partecliente.db.utils.DatabaseInitializer;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private AppDatabase mDb;
    private static int contador_ejercicio = 0;
    private String user_connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getDatabase(getApplicationContext());

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_connected = extras.getString("USER");

        if (extras.getString("action") == "PasaEjer"){
            pasaEjer(new View(getApplicationContext()));
        }
        else if (extras.getString("action") == "AnteriorEjer"){
            anteriorEjer(new View(getApplicationContext()));
        }
        else{
            List<Rutina> rutina = mDb.rutModel().getRutinasFromUser(user_connected);

            Rutina rut = rutina.get(contador_ejercicio%rutina.size());
            String ejer = rut.id_ejercicio;

            String imagename = mDb.ejerModel().getImageName(ejer);

            ImageView image = (ImageView) findViewById(R.id.image_ej);

            int id = getResources().getIdentifier(imagename, "mipmap", getPackageName());
            image.setImageResource(id);

            TextView series = (TextView) findViewById(R.id.num_series);
            series.setText(rut.series);
            TextView repes = (TextView) findViewById(R.id.num_repeticiones);
            repes.setText(rut.repeticiones);

            createNotification(contador_ejercicio, rut);
        }

    }

    protected void pasaEjer(View v){
        contador_ejercicio++;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("USER", user_connected);
        intent.putExtras(extras);

        startActivity(intent);
    }

    protected  void anteriorEjer(View v){
        contador_ejercicio--;

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("USER", user_connected);
        intent.putExtras(extras);

        startActivity(intent);
    }

    public void createNotification(int contador, Rutina rut){
        String data = "Series: " + rut.series +" Repeticiones: " + rut.repeticiones;
        int notificationID = 0;
        Intent nextExe = new Intent(getApplicationContext(), MainActivity.class);
        Bundle extras_next = new Bundle();
        extras_next.putString("action", "PasaEjer");
        extras_next.putString("USER", user_connected);
        nextExe.putExtras(extras_next);

        Intent antEjer = new Intent(getApplicationContext(), MainActivity.class);
        Bundle extras_ant = new Bundle();
        extras_ant.putString("action", "AnteriorEjer");
        extras_ant.putString("USER", user_connected);
        antEjer.putExtras(extras_ant);

        String nombre_ejer = mDb.ejerModel().getNombreEjer(rut.id_ejercicio);

        PendingIntent pasaEjercicio = PendingIntent.getBroadcast(this, 0,
                nextExe, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent vuelveEje = PendingIntent.getBroadcast(this, 0,
                antEjer, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action pasarEjercicio =
                new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Siguiente ejercicio",
                        pasaEjercicio)
                        .build();

        NotificationCompat.Action volverEjercicio =
                new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Ejercicio anterior",
                        vuelveEje)
                        .build();

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(nombre_ejer)
                        .setContentText(data)
                        .addAction(pasarEjercicio)
                        .addAction(volverEjercicio);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, builder.build());
    }
}