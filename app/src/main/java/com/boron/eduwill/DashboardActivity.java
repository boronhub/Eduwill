package com.boron.eduwill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class DashboardActivity extends AppCompatActivity {

    CardView profile;
    CardView session;
    CardView social;
    CardView chat;
    CardView about;
    CardView settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        profile = findViewById(R.id.profile_dash);
        session = findViewById(R.id.session_dash);
        social = findViewById(R.id.social_dash);
        chat = findViewById(R.id.chat_dash);
        about = findViewById(R.id.about_dash);
        settings = findViewById(R.id.settings_dash);

        session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent session = new Intent(DashboardActivity.this, SessionActivity.class);
                startActivity(session);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(DashboardActivity.this, ProfileActivity.class);
                    startActivity(profile);
                }
        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
