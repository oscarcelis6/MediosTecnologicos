package principal.com.mediostecnologicos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button botonIngresar;
    private EditText editTextLogin;
    private EditText editTextPasswordIngresar;
    private TextView textViewSignUp;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=null){
            //Perfil, creación de servicio, etc, en esta parte
            finish();
            //Ingresamos a la nueva Activity (Perfil, creación de servicios, etc)
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        botonIngresar = (Button) findViewById(R.id.botonIngresar);
        editTextLogin = (EditText) findViewById(R.id.editTextEmailIngresar);
        editTextPasswordIngresar = (EditText) findViewById(R.id.editTextPasswordIngresar);
        textViewSignUp = (TextView) findViewById(R.id.textViewRegistrarse);

        botonIngresar.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);

    }


    private void IngresoUsuario(){
        final String emailIngreso = editTextLogin.getText().toString().trim();
        String passwordIngreso = editTextPasswordIngresar.getText().toString().trim();

        if(TextUtils.isEmpty(emailIngreso)){
            //Valida si el campo email está vacío
            //Cuadro de diálogo centrado en la parte superior de la pantalla del dispositivo
            Toast toast = Toast.makeText(LoginActivity.this, "Digite un Email válido", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            editTextLogin.setText("");
            toast.show();
            return;
        }

        if(TextUtils.isEmpty(passwordIngreso)){
            //Valida si el campo password está vacío
            //Cuadro de diálogo centrado en la parte superior de la pantalla del dispositivo
            Toast toast = Toast.makeText(LoginActivity.this, "Digite una contraseña válida", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            editTextPasswordIngresar.setText("");
            toast.show();
            return;
        }

        progressDialog.setMessage("Ingresando");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(emailIngreso, passwordIngreso)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            finish();
                            //Ingresamos a la nueva Activity (Perfil, creación de servicios, etc)
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }else{

                            //Cuadro de diálogo centrado en el centro de la pantalla del dispositivo
                            Toast toast = Toast.makeText(LoginActivity.this, "El usuario o contraseña son incorrectos", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            editTextLogin.setText("");
                            editTextPasswordIngresar.setText("");
                            toast.show();

                        }


                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view==botonIngresar){
            IngresoUsuario();
        }

        if(view==textViewSignUp){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
