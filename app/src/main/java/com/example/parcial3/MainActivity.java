package com.example.parcial3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.parcial3.Ayudas.AdmSQLiteOpenHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    EditText txtNom, txtApell, txtnum, txtacorr;
    Button btninsert, btnactu,btnborrar;
    AdmSQLiteOpenHelper admin = new AdmSQLiteOpenHelper(getApplicationContext(), "parcial",null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNom = findViewById(R.id.txN);
        txtApell = findViewById(R.id.txap);
        txtnum = findViewById(R.id.txte);
        txtacorr = findViewById(R.id.txcor);

        btninsert=findViewById(R.id.btinsertar);
        btnactu=findViewById(R.id.btactualizar);
        btnborrar=findViewById(R.id.btborrar);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btinsertar:
                        btninsert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SQLiteDatabase db = admin.getWritableDatabase();
                                String no=txtNom.getText().toString();
                                String ap=txtApell.getText().toString();
                                String nu=txtnum.getText().toString();
                                String co=txtacorr.getText().toString();

                                ContentValues info = new ContentValues();
                                info.put("Nombre", no);
                                info.put("Apellido", ap);
                                info.put("Telefono", nu);
                                info.put("Correo", co);

                                db.insert("Contactos", null, info);
                            }
                        });
                        return true;
                    case R.id.btactualizar:
                        btnactu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SQLiteDatabase db = admin.getWritableDatabase();
                                String no=txtNom.getText().toString();
                                String ap=txtApell.getText().toString();
                                String nu=txtnum.getText().toString();
                                String co=txtacorr.getText().toString();

                                ContentValues info = new ContentValues();
                                info.put("Nombre", no);
                                info.put("Apellido", ap);
                                info.put("Telefono", nu);
                                info.put("Correo", co);

                                db.update("Contactos", info, "idContactos = ?", new String[]{String.valueOf(1)});
                            }
                        });
                        return true;
                    case R.id.btborrar:
                        btnborrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SQLiteDatabase db = admin.getWritableDatabase();
                                db.delete("Contactos", "idContactos = ?", new String[]{String.valueOf(1)});
                            }
                        });
                        return true;
                        case R.id.
                                btnBuscar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String searchTerm = etBuscar.getText().toString();

                                SQLiteDatabase db = admin.getReadableDatabase();

                                String[] projection = {
                                        "Nombre",
                                        "Apellido",
                                        "Telefono",
                                        "CorreoElec"
                                };

                                String selection = "Nombre LIKE ? OR Apellido LIKE ? OR Telefono LIKE ? OR CorreoElec LIKE ?";
                                String[] selectionArgs = { "%" + searchTerm + "%", "%" + searchTerm + "%", "%" + searchTerm + "%", "%" + searchTerm + "%" };

                                Cursor cursor = db.query(
                                        "Contactos",
                                        projection,
                                        selection,
                                        selectionArgs,
                                        null,
                                        null,
                                        null
                                );


                                if (cursor.moveToFirst()) {
                                    do {
                                        String nombre = cursor.getString(cursor.getColumnIndex("Nombre"));
                                        String apellido = cursor.getString(cursor.getColumnIndex("Apellido"));
                                        int telefono = cursor.getInt(cursor.getColumnIndex("Telefono"));
                                        String correo = cursor.getString(cursor.getColumnIndex("CorreoElec"));


                                        Log.d("BUSCAR", "Nombre: " + nombre + ", Apellido: " + apellido + ", Teléfono: " + telefono + ", Correo: " + correo);
                                    } while (cursor.moveToNext());
                                } else {

                                    Log.d("BUSCAR", "No se encontraron resultados para el término de búsqueda: " + searchTerm);
                                }

                                cursor.close();
                                db.close();
                            }
                        });
                    default:
                        return false;
                }
            }
        });
    }
}
