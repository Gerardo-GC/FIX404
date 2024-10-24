package sv.edu.itca.fix404;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PantallaLogin extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);

        // Manejar el inicio de sesión
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Manejar el registro de usuario
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inicio de sesión exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(PantallaLogin.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        // Redirigir a la actividad principal
                        startActivity(new Intent(PantallaLogin.this, PantallaPrincipal.class));
                        finish();
                    } else {
                        // Si el inicio de sesión falla, mostrar un mensaje
                        Toast.makeText(PantallaLogin.this, "Usuario no encontrado. Regístrate.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validar que el correo y la contraseña no estén vacíos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(PantallaLogin.this, "Por favor, ingresa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear el usuario con el correo y la contraseña
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registro exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(PantallaLogin.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        // Redirigir a la actividad principal
                        startActivity(new Intent(PantallaLogin.this, PantallaPrincipal.class));
                        finish();
                    } else {
                        // Si el registro falla, mostrar un mensaje
                        Toast.makeText(PantallaLogin.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
