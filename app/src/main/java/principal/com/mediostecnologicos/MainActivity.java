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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button  botonRegistrar;
    private EditText editTextEmailRegistro;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=null){
            //Perfil, creación de servicio, etc, en esta parte
            finish();
            //Ingresamos a la nueva Activity (Perfil, creación de servicios, etc)
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        botonRegistrar = (Button) findViewById(R.id.botonRegistrar);
        editTextEmailRegistro = (EditText) findViewById(R.id.editTextEmailLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextEmailPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewIngresar);

        textViewSignin.setOnClickListener(this);
        botonRegistrar.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    private void registrarUsuario(){
        String emailLogin = editTextEmailRegistro.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(emailLogin)){
        //Valida si el campo email está vacío
            Toast.makeText(this,"Ingrese un email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //Valida si el campo password está vacío
            Toast.makeText(this,"Ingrese una contraseña",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registrando usuario");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(emailLogin, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //progressDialog.hide();
                                finish();
                                //Ingresamos a la nueva Activity (Perfil, creación de servicios, etc)
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                            Toast.makeText(MainActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.hide();

                            //Cuadro de diálogo centrado en el centro de la pantalla del dispositivo
                            Toast toast = Toast.makeText(MainActivity.this, "No se pudo registrar el usuario, verifique los datos e intente de nuevo", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            editTextEmailRegistro.setText("");
                            editTextPassword.setText("");
                            toast.show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private void iniciarSesion(){
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onClick(View view) {
        if(view==botonRegistrar){
            registrarUsuario();
        }

        if(view==textViewSignin){
            //Se deberá configurar una nueva activity para abrirla con éste método
           iniciarSesion();
        }


    }
}
