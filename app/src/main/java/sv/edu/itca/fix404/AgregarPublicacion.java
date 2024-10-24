package sv.edu.itca.fix404;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgregarPublicacion extends AppCompatActivity {

    private EditText etTitulo, etDetalle;
    private Button btnGuardar;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_publicacion);

        // Inicializar Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference("posts");

        etTitulo = findViewById(R.id.etTitulo);
        etDetalle = findViewById(R.id.etDetalle);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Manejar el evento del botón guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPost();
            }
        });
    }

    private void guardarPost() {
        String titulo = etTitulo.getText().toString().trim();
        String detalle = etDetalle.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (TextUtils.isEmpty(titulo)) {
            etTitulo.setError("El título es obligatorio");
            return;
        }

        if (TextUtils.isEmpty(detalle)) {
            etDetalle.setError("El detalle es obligatorio");
            return;
        }

        // Generar un ID único para cada post
        String postId = mDatabase.push().getKey();

        // Crear un objeto Post con los datos ingresados
        Post post = new Post(titulo, detalle);

        // Guardar el post en Firebase Database
        if (postId != null) {
            mDatabase.child(postId).setValue(post)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AgregarPublicacion.this, "Post guardado exitosamente", Toast.LENGTH_SHORT).show();
                            // Limpiar los campos después de guardar
                            etTitulo.setText("");
                            etDetalle.setText("");
                        } else {
                            Toast.makeText(AgregarPublicacion.this, "Error al guardar el post", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}

class Post {
    public String titulo;
    public String detalle;

    public Post() {
        // Constructor vacío requerido por Firebase
    }

    public Post(String titulo, String detalle) {
        this.titulo = titulo;
        this.detalle = detalle;
    }
}
