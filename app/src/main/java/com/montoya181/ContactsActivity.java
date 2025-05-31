package com.montoya181;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerView rvContacts;
    private ContactsAdapter adapter;
    private ActivityResultLauncher<Intent> newContactLauncher;  // launcher para recibir resultados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        newContactLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String fn    = data.getStringExtra("first_name");
                        String ln    = data.getStringExtra("last_name");
                        String phone = data.getStringExtra("phone");
                        // Construir el string que se mostrará en la lista
                        String display = fn + " " + ln + " • " + phone;
                        adapter.addContact(display);
                        rvContacts.scrollToPosition(adapter.getItemCount() - 1);
                    }
                }
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = findViewById(R.id.toolbarContacts);
        setSupportActionBar(toolbar);

        rvContacts = findViewById(R.id.rvContacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        List<String> dummy = new ArrayList<>(Arrays.asList(
                "Ana • 555-1234",
                "Bruno • 666-5678",
                "Carla • 777-9012",
                "David • 888-3456"
        ));
        adapter = new ContactsAdapter(dummy);
        rvContacts.setAdapter(adapter);

        EditText etFilter = findViewById(R.id.etFilter);
        etFilter.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void afterTextChanged(Editable s) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabAddContact);
        fab.setOnClickListener(v ->
                newContactLauncher.launch(
                        new Intent(this, NewContactActivity.class)
                )
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {

            new AlertDialog.Builder(this)
                    .setTitle("Cerrar sesión")
                    .setMessage("¿Estás seguro que queres cerrar sesión?")
                    .setPositiveButton("Si", (dialog, which) -> {

                            // toast para mostrar que esta cerrando sesion
                            Toast.makeText(this, "Cerrando sesión", Toast.LENGTH_SHORT).show();

                            // invalido la sesion
                            getSharedPreferences("ao1_prefs", MODE_PRIVATE)
                                    .edit()
                                    .putBoolean("key_authenticated", false)
                                    .apply();

                            // vuelvo a login
                            Intent i = new Intent(this, LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
        })
                    .setNegativeButton("No",(dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
