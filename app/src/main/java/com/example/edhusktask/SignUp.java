package com.example.edhusktask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText name,email,password;
    Button signUP;
    TextView alreadyAc;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();


        dialog = new ProgressDialog(this);
        dialog.setTitle("Account creating");
        dialog.setMessage("Please wait....");

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUP = findViewById(R.id.signUp);
        alreadyAc = findViewById(R.id.alreadyAc);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if (auth.getCurrentUser()!=null){
            Intent intent =new Intent(SignUp.this,CreateProfile.class);
            startActivity(intent);
            finishAffinity();
        }

        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()){
                    name.setError("Please enter your name");
                    return;
                }
                if (email.getText().toString().isEmpty()){
                    email.setError("Please enter your email");
                    return;
                }
                if (password.getText().toString().isEmpty()){
                    password.setError("Please enter a password");
                    return;
                }
                dialog.show();

                createAc(name.getText().toString(),email.getText().toString(),password.getText().toString());
            }
        });

        alreadyAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
                finishAffinity();
            }
        });

    }

    public void createAc(String name,String email,String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        dialog.dismiss();
                        if (task.isSuccessful()){
                            Users user = new Users(name,email,password);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                            Intent intent = new Intent(SignUp.this,CreateProfile.class);
                            intent.putExtra("name",name);
                            startActivity(intent);
                            finishAffinity();

                        }else
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




    }
}