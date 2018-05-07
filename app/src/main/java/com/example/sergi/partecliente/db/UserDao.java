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
public interface UserDao {
    @Query("SELECT * FROM User WHERE padre == '0'") // TODO: Fix this!
    List<User> findUsersTrainers();

    @Insert(onConflict = IGNORE)
    void insertUser(User user);

    @Query("DELETE FROM User")
    void deleteAll();
}
