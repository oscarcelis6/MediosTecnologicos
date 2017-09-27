package principal.com.mediostecnologicos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private TextView textViewEmailUsuario;
    private Button botonCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            //Se invoca la Activity LoginActivity
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewEmailUsuario = (TextView) findViewById(R.id.textViewEmailusuarioProfile);
        textViewEmailUsuario.setText("Bienvenido"+" "+ user.getEmail());

        botonCerrarSesion = (Button) findViewById(R.id.botonCerrarSesion);

        botonCerrarSesion.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == botonCerrarSesion){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
