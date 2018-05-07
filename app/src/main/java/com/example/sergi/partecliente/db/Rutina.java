package com.example.sergi.partecliente.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by sergi on 07/05/2018.
 */

@Entity
public class Rutina {
    @PrimaryKey
    @NonNull
    public String usuario;

    public String id_ejercicio;

    public String series;

    public String repeticiones;

}
