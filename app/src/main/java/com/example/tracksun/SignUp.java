package com.example.tracksun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    Button signup;
    EditText email,password;
    FirebaseAuth auth;
    ProgressBar prgbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup=findViewById(R.id.button);
        email=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        auth=FirebaseAuth.getInstance();
        prgbar=findViewById(R.id.progressBar2);
    }
    public void goHome(View view){
        String mail=email.getText().toString().trim();
        String psw=password.getText().toString().trim();
        if(mail.isEmpty()){
            email.setError("Email required");
            email.requestFocus();
            return;
        }
        if(psw.isEmpty()){
            password.setError("Password required");
            password.requestFocus();
            return;
        }
        prgbar.setVisibility(ProgressBar.VISIBLE);
        auth.createUserWithEmailAndPassword(mail,psw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                prgbar.setVisibility(ProgressBar.GONE);
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUp.this,"A verification email has been sent.\nCheck your inbox.",Toast.LENGTH_LONG).show();
                    }
                });
                startActivity(new Intent(SignUp.this,Home.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                prgbar.setVisibility(ProgressBar.GONE);
                Toast.makeText(SignUp.this,e.toString(),Toast.LENGTH_LONG).show();
            }
        });


    /*addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                prgbar.setVisibility(ProgressBar.GONE);
                if(task.isSuccessful()){
                    startActivity(new Intent(SignUp.this,Home.class));
                    finish();
                }else {
                    Toast.makeText(SignUp.this,task.getException().toString(),Toast.LENGTH_SHORT);
                }


            }
        });*/
    }
}