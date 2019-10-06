package com.boron.eduwill;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;

import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements DialogEducationRegisterFragment.SingleChoiceListener {

    DatePickerDialog picker;
    Button buttonEducationPick;
    Button registerButton;
    EditText txtEmailAddress;
    EditText txtName;
    EditText txtPassword;
    EditText txtDate;
    TextView txtEducation;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtEmailAddress = findViewById(R.id.email_register);
        txtPassword = findViewById(R.id.password_register);
        txtName = findViewById(R.id.name_register);
        txtEducation = findViewById(R.id.education_register);
        firebaseAuth = FirebaseAuth.getInstance();
        registerButton = findViewById(R.id.register_button);
        buttonEducationPick = findViewById(R.id.educationSelectButton_register);
        txtDate = findViewById(R.id.date_et);
        txtDate.setInputType(InputType.TYPE_NULL);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }

        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    registerUser();
            }
        });


        buttonEducationPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment singleChoiceDialog = new DialogEducationRegisterFragment();
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });
    }

    private void registerUser(){
        final String name = txtName.getText().toString().trim();
        final String date = txtDate.getText().toString().trim();
        final String education = txtEducation.getText().toString().trim();
        final String email = txtEmailAddress.getText().toString().trim();
        String password  = txtPassword.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this,"Please enter Name",Toast.LENGTH_LONG).show();
            return;
        }
        if (date.isEmpty()) {
            Toast.makeText(this,"Please enter Date",Toast.LENGTH_LONG).show();
            return;
        }
        if (education.isEmpty()) {
            Toast.makeText(this,"Please enter Education",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this,"Please enter valid email",Toast.LENGTH_LONG).show();
            return;
        }

        if(password.length() < 8){
            Toast.makeText(this,"Please enter a longer password",Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            User user = new User (
                                    name,
                                    email,
                                    date,
                                    education


                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(firebaseAuth.getCurrentUser().getUid())
                                    .setValue(user);
                            Toast.makeText(RegisterActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                            startActivity(intent);

                        }
                    }
                });
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        txtEducation.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {
    }

}