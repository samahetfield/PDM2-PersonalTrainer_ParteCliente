package com.example.sergi.partecliente.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by sergi on 07/05/2018.
 */
@Dao
public interface EjerciciosDao {

    @Insert(onConflict = IGNORE)
    void insertEjer(Ejercicios ejer);

    @Query("DELETE FROM Ejercicios")
    void deleteAll();

    @Query("SELECT imagen from EJERCICIOS WHERE id_ejercicio == :ejercicio")
    String getImageName(String ejercicio);

    @Query("SELECT nombre FROM Ejercicios WHERE id_ejercicio == :ejercicio")
    String getNombreEjer(String ejercicio);
}
