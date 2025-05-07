package com.montoya181;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
}
