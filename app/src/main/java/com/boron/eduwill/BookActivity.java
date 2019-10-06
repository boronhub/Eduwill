package com.boron.eduwill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Calendar;

public class BookActivity extends AppCompatActivity implements
        DialogLocationBookFragment.SingleChoiceListener,
        DialogTimingsBookFragment.SingleChoiceListener,
        DialogAgeBookFragment.SingleChoiceListener,
        DialogSubjectBookFragment.SingleChoiceListener{

    DatePickerDialog picker;
    Button buttonTimingsPick;
    Button buttonAgePick;
    Button buttonSubjectPick;
    Button bookButton;
    TextView txtLocation;
    TextView txtDate;
    TextView txtTimeStart;
    TextView txtTimeEnd;
    TextView txtAge;
    TextView txtSubjects;
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        buttonTimingsPick = findViewById(R.id.timeSelectButton_book);
        buttonAgePick = findViewById(R.id.ageSelectButton_book);
        buttonSubjectPick = findViewById(R.id.subjectSelectButton_book);
        bookButton = findViewById(R.id.book_button);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        txtLocation = findViewById(R.id.book_location);
        txtDate = findViewById(R.id.book_date);
        txtTimeStart = findViewById(R.id.book_timings_start);
        txtTimeEnd = findViewById(R.id.book_timings_end);
        txtAge = findViewById(R.id.age_book);
        txtSubjects = findViewById(R.id.subject_book);

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(BookActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }

        });

        txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment singleChoiceDialogLocation = new DialogLocationBookFragment();
                singleChoiceDialogLocation.setCancelable(false);
                singleChoiceDialogLocation.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });

        buttonTimingsPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment singleChoiceDialogTimings = new DialogTimingsBookFragment();
                singleChoiceDialogTimings.setCancelable(false);
                singleChoiceDialogTimings.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });

        buttonAgePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment singleChoiceDialogAge = new DialogAgeBookFragment();
                singleChoiceDialogAge.setCancelable(false);
                singleChoiceDialogAge.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });

        buttonSubjectPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment singleChoiceDialogSubjects = new DialogSubjectBookFragment();
                singleChoiceDialogSubjects.setCancelable(false);
                singleChoiceDialogSubjects.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booking();
            }
        });

    }

    private void booking() {
        final String location = txtLocation.getText().toString().trim();
        final String date = txtDate.getText().toString().trim();
        final String timeStart = txtTimeStart.getText().toString().trim();
        final String timeEnd = txtTimeEnd.getText().toString().trim();
        final String age = txtAge.getText().toString().trim();
        final String subject = txtSubjects.getText().toString().trim();
        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter Location", Toast.LENGTH_LONG).show();
            return;
        }
        if (date.isEmpty()) {
            Toast.makeText(this, "Please enter Date", Toast.LENGTH_LONG).show();
            return;
        }
        if (timeStart.isEmpty()) {
            Toast.makeText(this, "Please enter Time Start", Toast.LENGTH_LONG).show();
            return;
        }
        if (timeEnd.isEmpty()) {
            Toast.makeText(this, "Please enter Time End", Toast.LENGTH_LONG).show();
            return;
        }
        if (age.isEmpty()) {
            Toast.makeText(this, "Please enter Age", Toast.LENGTH_LONG).show();
            return;
        }

        if (subject.isEmpty()) {
            Toast.makeText(this, "Please enter Subject", Toast.LENGTH_LONG).show();
            return;
        }
        final Booking booking = new Booking(
                location,
                date,
                timeStart,
                timeEnd,
                age,
                subject
        );

        firebaseDatabase.child(firebaseAuth.getCurrentUser().getUid()).child("Bookings").setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(BookActivity.this, "Booking Confirmed", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(BookActivity.this, "Booking unsuccessful", Toast.LENGTH_LONG).show();
                }

            }
        });

            }

    @Override
    public void onPositiveButtonClickedLocation(String[] list, int position) {
        txtLocation.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClickedLocation() {

    }

    @Override
    public void onPositiveButtonClickedTimings(String[] list, int position) {
        if(position == 0){
            txtTimeStart.setText("9:00 AM");
            txtTimeEnd.setText("11:00 AM");
        }
        if(position == 1){
            txtTimeStart.setText("12:00 PM");
            txtTimeEnd.setText("2:00 PM");
        }
        if(position == 2){
            txtTimeStart.setText("4:00 PM");
            txtTimeEnd.setText("6:00 PM");
        }
    }

    @Override
    public void onNegativeButtonClickedTimings() {

    }

    @Override
    public void onPositiveButtonClickedAge(String[] list, int position) {
        txtAge.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClickedSubject(String[] list, int position) {
        txtSubjects.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClickedSubject() {

    }
}
