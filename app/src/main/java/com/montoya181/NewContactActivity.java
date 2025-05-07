package com.montoya181;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class NewContactActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        // Campos de texto
        TextInputEditText etFirst   = findViewById(R.id.etFirstName);
        TextInputEditText etLast    = findViewById(R.id.etLastName);
        TextInputEditText etPhone   = findViewById(R.id.etPhone);
        TextInputEditText etAddress = findViewById(R.id.etAddress);

        // RadioButton
        RadioGroup rgGender = findViewById(R.id.rgGender);

        // Boton Guardar

        MaterialButton btnSave = findViewById(R.id.btnSave);


        btnSave.setOnClickListener(v -> {

            // Lee y valida campos
            String first = etFirst.getText().toString().trim();
            String last  = etLast.getText().toString().trim();
            String phone   = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (first.isEmpty()){
                etFirst.setError("Requerido");
                return ;
            }

            if (last.isEmpty()) {
                etLast.setError("Requerido");
                return;
            }

            // Determino el genero
            int idSelec  = rgGender.getCheckedRadioButtonId();
            String gender = idSelec  == R.id.rbMale   ? "Masculino"
                    : idSelec  == R.id.rbFemale ? "Femenino"
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
