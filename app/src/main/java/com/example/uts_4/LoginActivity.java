package com.example.uts_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email,pass;
    private Button submit;
    private FirebaseAuth fAuth;
    private TextView goReg;
    private ImageView showPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final int[] i = {0};

        email = findViewById(R.id.idLoginEmail);
        pass = findViewById(R.id.idLoginPass);
        goReg = findViewById(R.id.idNoLogin);
        fAuth = FirebaseAuth.getInstance();
        submit = findViewById(R.id.idSubmitLogin);
        showPass = findViewById(R.id.idShowPass);

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i[0] == 0){
                    pass.setTransformationMethod(new HideReturnsTransformationMethod());
                    i[0] = 1;
                }else{
                    pass.setTransformationMethod(new PasswordTransformationMethod());
                    i[0] = 0;
                }

            }
        });

        goReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginemail = email.getText().toString();
                String loginpass = pass.getText().toString();
                validation(loginemail,loginpass);
            }
        });
    }

    public void validation(String email, String pass){
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Please fill in the required fields!", Toast.LENGTH_SHORT).show();
            return;
        }else{
            fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = fAuth.getCurrentUser();
        if(user!=null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}