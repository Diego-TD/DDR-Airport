package com.ddr;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    private EditText editTextLogInEmail,editTextLogInPassword;
    private ImageButton imageButton;
    private TextView textView3;
    private Button loginButton;
    private boolean eye;





    //private boolean

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        editTextLogInEmail = findViewById(R.id.editTextLogInEmail);
        loginButton = findViewById(R.id.loginButton);
        editTextLogInPassword = findViewById(R.id.editTextLogInPassword);
        imageButton = findViewById(R.id.imageButton);
        textView3 = findViewById(R.id.textView3);
        editTextLogInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        eye = false;

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        editTextLogInEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && editTextLogInEmail.getText().toString().equals("Email")) {
                    editTextLogInEmail.setText("");
                }
                else if (!hasFocus && editTextLogInEmail.getText().toString().isEmpty()) {
                    editTextLogInEmail.setText("Email");
                }
            }
        });

        editTextLogInPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && editTextLogInPassword.getText().toString().equals("Password")) {
                    editTextLogInPassword.setText("");
                    editTextLogInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                else if (!hasFocus && editTextLogInPassword.getText().toString().isEmpty()) {
                    editTextLogInPassword.setText("Password");
                    editTextLogInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(editTextLogInPassword.getInputType());
                if (!editTextLogInPassword.getText().toString().equals("Password") && !eye){
                    editTextLogInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eye = true;
                }
                else if (!editTextLogInPassword.getText().toString().equals("Password") && eye){
                    editTextLogInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eye = false;
                }
            }
        });

    //        textView3.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                System.out.println("Boton sign up");
    //            }
    //        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            String emailLogin = editTextLogInEmail.getText().toString();
            String password = editTextLogInPassword.getText().toString();
            if (!emailLogin.contains("@") || !emailLogin.contains(".") || emailLogin.contains(" ")){
                new AlertDialog.Builder(Login.this)
                        .setTitle("Email Invalido")
                        .setMessage("Por favor, ingresa un email valido")
                        .setPositiveButton("OK", null)
                        .show();

        } else {

            if (!password.isEmpty()) {
                Intent in = new Intent(Login.this, MainUserMenu.class);
                startActivity(in);
            }
            else {


                new AlertDialog.Builder(Login.this)
                        .setTitle("Campos Vacíos")
                        .setMessage("Por favor, completa todos los campos")
                        .setPositiveButton("OK", null)
                        .show();}}

        }});

        TextView signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Login.this, SignUp.class);
                startActivity(in);
            }


        });

    }
}