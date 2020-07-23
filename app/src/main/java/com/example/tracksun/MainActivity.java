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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText email,password;
    FirebaseAuth auth;
    ProgressBar prgbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.button);
        email=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        auth=FirebaseAuth.getInstance();
        prgbar=findViewById(R.id.progressBar2);
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(this,Home.class));
            finish();
        }

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
        auth.signInWithEmailAndPassword(mail,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                prgbar.setVisibility(ProgressBar.GONE);
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,Home.class));
                    finish();
                }else {
                    Toast.makeText(MainActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    public void signup(View view){
        startActivity(new Intent(MainActivity.this,SignUp.class));
    }

}