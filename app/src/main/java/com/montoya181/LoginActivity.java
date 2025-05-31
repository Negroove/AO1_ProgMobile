package com.montoya181;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    // constantes para sharedpreferences
    private static final String PREFS_NAME = "ao1_prefs";
    private static final String KEY_AUTHENTICATED = "key_authenticated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //antes de inflar la ui compruebo que la sesion persiste
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (prefs.getBoolean(KEY_AUTHENTICATED, false)) {
            // Si ya estaba autenticado, saltamos el login
            startActivity(new Intent(this, ContactsActivity.class));
            finish();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin   = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (user.equals("admin") && pass.equals("1234")) {

                prefs.edit()
                        .putBoolean(KEY_AUTHENTICATED, true)
                        .apply();

                startActivity(new Intent(LoginActivity.this, ContactsActivity.class));
                finish();  // evita volver con “Back”
            }else{
                    Toast.makeText(
                            LoginActivity.this,
                            "Usuario o contraseña inválidos",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
    }
}