package com.example.sergi.partecliente;

import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergi.partecliente.db.AppDatabase;
import com.example.sergi.partecliente.db.User;
import com.example.sergi.partecliente.db.utils.DatabaseInitializer;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private AppDatabase mDb;

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView image = (ImageView) findViewById(R.id.image_ejer);

        //int id = getResources().getIdentifier("abductor", "drawable", getPackageName());
        //image.setImageResource(id);
        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());

        DatabaseInitializer.populateSync(mDb);

        text = findViewById(R.id.texto);
        StringBuilder sb = new StringBuilder();

        List<User> youngUsers = mDb.userModel().findUsersTrainers();
        for (User youngUser : youngUsers) {
            sb.append(String.format(Locale.US,
                    "%s, %s (%d)\n", youngUser.nombre, youngUser.apellidos, youngUser.usuario));
        }
        text.setText(sb);
    }
}