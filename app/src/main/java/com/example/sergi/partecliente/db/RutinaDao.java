package com.example.sergi.partecliente.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by sergi on 07/05/2018.
 */
@Dao
public interface RutinaDao {
    @Insert()
    void insertRutina(Rutina rutina);

    @Query("DELETE FROM Rutina")
    void deleteAll();
}
