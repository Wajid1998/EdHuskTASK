package com.example.edhusktask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity {

    EditText rEmail;
    TextView msg;
    Button forgot;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        getSupportActionBar().hide();

        rEmail = findViewById(R.id.rEmail);
        forgot= findViewById(R.id.forgot);
        msg = findViewById(R.id.message);

        auth = FirebaseAuth.getInstance();


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rEmail.getText().toString().length()<0){
                    rEmail.setError("Please enter Email");
                    return;
                }
                auth.sendPasswordResetEmail(rEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                msg.setText("Please check your Email "+rEmail.getText().toString() +
                                        " we sent a link");
                            }
                        });
            }
        });
    }
}