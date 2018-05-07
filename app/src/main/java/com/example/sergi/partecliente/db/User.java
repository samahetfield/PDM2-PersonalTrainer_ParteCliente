package com.example.sergi.partecliente.db;

/**
 * Created by sergi on 07/05/2018.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String nombre;

    public String apellidos;

    public String usuario;

    public String password;

    public String correo;

    public String telefono;

    public String padre;
}
