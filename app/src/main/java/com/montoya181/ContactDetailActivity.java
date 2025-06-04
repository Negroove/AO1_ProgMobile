package com.montoya181;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import com.montoya181.data.ContactRepository;
import com.montoya181.domain.Contact;

public class ContactDetailActivity extends AppCompatActivity {

    private ContactRepository repo;
    private long contactId;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        repo = new ContactRepository(this);

        // obtengo el id del contacto
        contactId = getIntent().getLongExtra("contact_id", -1L);
        if (contactId == -1L) {
            Toast.makeText(this, "Error: contacto no encontrado.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // leo el contacto edsde la base
        contact = repo.getById(contactId);
        if (contact == null) {
            Toast.makeText(this, "Error: contacto no existe en BD.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // referencio la vista
        MaterialTextView tvName    = findViewById(R.id.tvDetailName);
        MaterialTextView tvPhone   = findViewById(R.id.tvDetailPhone);
        MaterialTextView tvAddress = findViewById(R.id.tvDetailAddress);
        MaterialTextView tvGender  = findViewById(R.id.tvDetailGender);
        MaterialButton btnEdit     = findViewById(R.id.btnEdit);
        MaterialButton btnDelete   = findViewById(R.id.btnDelete);

        // muestro datos
        tvName.setText(contact.getFirstName() + " " + contact.getLastName());
        tvPhone.setText("Teléfono: " + contact.getPhone());
        tvAddress.setText("Dirección: " + contact.getAddress());
        tvGender.setText("Género: " + contact.getGender());

        //
        btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(this, NewContactActivity.class);
            i.putExtra("contact_id",    contact.getId());
            i.putExtra("first_name",    contact.getFirstName());
            i.putExtra("last_name",     contact.getLastName());
            i.putExtra("phone",         contact.getPhone());
            i.putExtra("address",       contact.getAddress());
            i.putExtra("gender",        contact.getGender());
            startActivityForResult(i, 1234); // código 1234 para edición
        });

        // eliminar de la base de datos y cerrar
        btnDelete.setOnClickListener(v -> {
            int rows = repo.delete(contactId);
            if (rows > 0) {
                Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show();
                finish(); // vuelve a la lista
            } else {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // capturo el momento de la edicion
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == RESULT_OK && data != null) {
            String fn      = data.getStringExtra("first_name");
            String ln      = data.getStringExtra("last_name");
            String phone   = data.getStringExtra("phone");
            String address = data.getStringExtra("address");
            String gender  = data.getStringExtra("gender");

            contact.setFirstName(fn);
            contact.setLastName(ln);
            contact.setPhone(phone);
            contact.setAddress(address);
            contact.setGender(gender);

            int rows = repo.update(contactId, contact);
            if (rows > 0) {
                Toast.makeText(this, "Contacto actualizado", Toast.LENGTH_SHORT).show();
                // cierro el activity para volver a la lista
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
