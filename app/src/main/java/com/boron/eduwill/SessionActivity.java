package com.boron.eduwill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class SessionActivity extends AppCompatActivity {

    CardView past;
    CardView book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        past = findViewById(R.id.past_sessions);
        book = findViewById(R.id.book_sessions);

        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent past = new Intent(SessionActivity.this, PastSessionsActivity.class);
                startActivity(past);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent book = new Intent(SessionActivity.this, BookActivity.class);
                startActivity(book);
            }
        });

    }

}
