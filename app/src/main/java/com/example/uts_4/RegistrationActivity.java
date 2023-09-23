package com.example.uts_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email,pass,pass2;
    private TextView LoginIntent;
    private Button submit;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = findViewById(R.id.idRegEmail);
        pass = findViewById(R.id.idRegPass);
        pass2 = findViewById(R.id.idRegPass2);
        submit = findViewById(R.id.idSubmitReg);
        LoginIntent = findViewById(R.id.idAlrLogin);
        fAuth = FirebaseAuth.getInstance();

        LoginIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regemail = email.getText().toString();
                String regpass = pass.getText().toString();
                String regpass2 = pass2.getText().toString();
                validation(regemail,regpass,regpass2);
            }
        });
    }

    public void validation(String email, String pass, String pass2){
        if(!TextUtils.equals(pass, pass2)){
            Toast.makeText(RegistrationActivity.this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass2)){
            Toast.makeText(this, "Please fill in the required fields!", Toast.LENGTH_SHORT).show();
        }else{
            fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}