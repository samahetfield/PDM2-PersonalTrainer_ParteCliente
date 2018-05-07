package com.example.sergi.partecliente.db.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.sergi.partecliente.db.AppDatabase;
import com.example.sergi.partecliente.db.Ejercicios;
import com.example.sergi.partecliente.db.Rutina;
import com.example.sergi.partecliente.db.User;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sergi on 07/05/2018.
 */

public class DatabaseInitializer {
    // Simulate a blocking operation delaying each Loan insertion with a delay:
    private static final int DELAY_MILLIS = 500;

    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static void addRut(final AppDatabase db,final User user, final Ejercicios ejer, final String ser, final String repe) {
        Rutina rutina = new Rutina();
        rutina.id_ejercicio = ejer.id_ejercicio ;
        rutina.usuario = user.usuario;
        rutina.repeticiones = repe;
        rutina.series = ser;
        db.rutModel().insertRutina(rutina);
    }

    private static Ejercicios addEjer(final AppDatabase db, final String id, final String name, final String description, final String img) {
        Ejercicios ejer = new Ejercicios();
        ejer.id_ejercicio = id;
        ejer.nombre = name;
        ejer.descripcion = description;
        ejer.imagen = img;
        db.ejerModel().insertEjer(ejer);
        return ejer;
    }

    private static User addUser(final AppDatabase db, final String id, final String name, final String surname, final String user, final String pass, final String email,
                                final String phone, final String father) {
        User usuario = new User();
        usuario.nombre = name;
        usuario.apellidos = surname;
        usuario.usuario = user;
        usuario.password = pass;
        usuario.correo = email;
        usuario.telefono = phone;
        usuario.padre = father;
        db.userModel().insertUser(usuario);
        return usuario;
    }

    private static void populateWithTestData(AppDatabase db) {
        db.rutModel().deleteAll();
        db.userModel().deleteAll();
        db.ejerModel().deleteAll();

        User user1 = addUser(db, "1", "Marcos", "Olvera", "marcol", "prueba", "marcol@correo.es", "123123123", "sersam");
        User user2 = addUser(db, "2", "Sergio", "Sama", "sersam", "prueba", "serseam@correo.es", "123123123", "0");

        Ejercicios ejer1 = addEjer(db, "1", "Abductor", "asdfasdfasdfasdfasdfa", "abductor");
        Ejercicios ejer2 = addEjer(db, "2", "Abductor", "asdfasdfasdfasdfasdfa", "abductor");
        try {
            addRut(db, user1, ejer1, "25", "3");
            Thread.sleep(DELAY_MILLIS);
            addRut(db, user2, ejer2, "30", "2");
            Thread.sleep(DELAY_MILLIS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
