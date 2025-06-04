package com.montoya181;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class NewContactActivity extends AppCompatActivity {

    private TextInputEditText etFirst, etLast, etPhone, etAddress;
    private RadioGroup rgGender;
    private MaterialButton btnSave;

    // Guarda el ID si estamos en modo edición
    private long editingContactId = -1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        // 1) Referencias a vistas
        etFirst   = findViewById(R.id.etFirstName);
        etLast    = findViewById(R.id.etLastName);
        etPhone   = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        rgGender  = findViewById(R.id.rgGender);
        btnSave   = findViewById(R.id.btnSave);

        // 2) Verifico si recibo extras de ContactDetailActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("contact_id")) {
            editingContactId = intent.getLongExtra("contact_id", -1L);

            // 3) Si editingContactId != -1, estamos en edición: precargo los campos
            if (editingContactId != -1L) {
                btnSave.setText("Actualizar"); // opcional: cambias el texto

                // Los valores individuales ya llegaron por extras:
                String first  = intent.getStringExtra("first_name");
                String last   = intent.getStringExtra("last_name");
                String phone  = intent.getStringExtra("phone");
                String address= intent.getStringExtra("address");
                String gender = intent.getStringExtra("gender");

                // Relleno los EditText
                if (first  != null) etFirst.setText(first);
                if (last   != null) etLast.setText(last);
                if (phone  != null) etPhone.setText(phone);
                if (address!= null) etAddress.setText(address);

                // Marco el RadioButton según el género
                if ("Masculino".equalsIgnoreCase(gender)) {
                    rgGender.check(R.id.rbMale);
                } else if ("Femenino".equalsIgnoreCase(gender)) {
                    rgGender.check(R.id.rbFemale);
                } else {
                    rgGender.check(R.id.rbOther);
                }
            }
        }

        // listener de boton guardar/actualizar
        btnSave.setOnClickListener(v -> {

            String first   = etFirst.getText().toString().trim();
            String last    = etLast.getText().toString().trim();
            String phone   = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (first.isEmpty()){
                etFirst.setError("Requerido");
                return;
            }
            if (last.isEmpty()) {
                etLast.setError("Requerido");
                return;
            }
            if (phone.isEmpty()) {
                etPhone.setError("Requerido");
                return;
            }


            // genero
            int idSelec  = rgGender.getCheckedRadioButtonId();
            String gender = idSelec == R.id.rbMale   ? "Masculino"
                    : idSelec == R.id.rbFemale ? "Femenino"
                    : "Otro";

            Intent data = new Intent();
            data.putExtra("first_name", first);
            data.putExtra("last_name",  last);
            data.putExtra("phone",      phone);
            data.putExtra("address",    address);
            data.putExtra("gender",     gender);

            setResult(RESULT_OK, data);
            finish();
        });
    }
}
