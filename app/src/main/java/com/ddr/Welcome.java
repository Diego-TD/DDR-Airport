package com.ddr;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Welcome extends AppCompatActivity {

    Button loginButton, signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);


        loginButton = findViewById(R.id.activity_welcome_btn_login);
        signUpButton = findViewById(R.id.activity_welcome_btn_sign_up);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(Welcome.this, Login.class);
            startActivity(intent);
        });

        signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(Welcome.this, SignUp.class);
            startActivity(intent);
        });
    }
}