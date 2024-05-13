package com.ddr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {
    private TextView loginBackText;
    private boolean eyeConfirmPassword;
    private boolean eyePassword;
    private ImageButton imbSignUpEyeConfirmPassword;
    private ImageButton imbSignUpEyePassword;
    private EditText editTextSignUpPassword;
    private EditText editTextSignUpConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            loginBackText = findViewById(R.id.loginBackText);
            editTextSignUpConfirmPassword = findViewById(R.id.editTextSignUpConfirmPassword);
            imbSignUpEyeConfirmPassword = findViewById(R.id.imbSignUpEyeConfirmPassword);
            imbSignUpEyePassword = findViewById(R.id.imbSignUpEyePassword);
            editTextSignUpPassword = findViewById(R.id.editTextSignUpPassword);

            loginBackText.setOnClickListener(v1 -> {
                Intent in = new Intent(SignUp.this, Login.class);
                startActivity(in);
            });

            imbSignUpEyeConfirmPassword.setOnClickListener(v1 -> {
                if (!editTextSignUpConfirmPassword.getText().toString().equals("Password") && !eyeConfirmPassword) {
                    editTextSignUpConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eyeConfirmPassword = true;
                } else if (!editTextSignUpConfirmPassword.getText().toString().equals("Password") && eyeConfirmPassword) {
                    editTextSignUpConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eyeConfirmPassword = false;
                }
            });

            imbSignUpEyePassword.setOnClickListener(v1 -> {
                if (!editTextSignUpPassword.getText().toString().equals("Password") && !eyePassword) {
                    editTextSignUpPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eyePassword = true;
                } else if (!editTextSignUpPassword.getText().toString().equals("Password") && eyePassword) {
                    editTextSignUpPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eyePassword = false;
                }
            });

            return insets;
        });


    }


}
