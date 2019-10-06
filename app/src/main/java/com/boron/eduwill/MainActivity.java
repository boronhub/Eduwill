package com.boron.eduwill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerPage;
    Button loginButton;
    EditText email;
    EditText pswd;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email_login);
        pswd = findViewById(R.id.password_login);
        loginButton = findViewById(R.id.login_button);
        registerPage = findViewById(R.id.register_page);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(MainActivity.this, DashboardActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        registerPage.setOnClickListener(this);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,DashboardActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this,"Login unuccessful",Toast.LENGTH_SHORT).show();
                    pswd.getText().clear();
                    email.getText().clear();

                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_login = email.getText().toString();
                String pswd_login = pswd.getText().toString();


                if(email_login.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }

                if(pswd_login.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email_login).matches()) {
                    Toast.makeText(MainActivity.this,"Please enter valid email",Toast.LENGTH_LONG).show();
                    return;
                }

                if(pswd_login.length() < 8){
                    Toast.makeText(MainActivity.this,"Please enter a longer password",Toast.LENGTH_LONG).show();
                    return;
                }

                else{

                    mFirebaseAuth.signInWithEmailAndPassword(email_login,pswd_login).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent toDash = new Intent(MainActivity.this,DashboardActivity.class);
                                startActivity(toDash);
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Try Again",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }


    @Override
    public void onClick(View v) {

        if (v == registerPage) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }



}