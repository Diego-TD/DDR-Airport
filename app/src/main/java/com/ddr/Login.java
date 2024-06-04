package com.ddr;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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

import com.ddr.logic.DDRAPI;
import com.ddr.logic.DDRS;
import com.ddr.logic.RetrofitClient;
import com.ddr.logic.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
    private EditText editTextLogInEmail,editTextLogInPassword;
    private ImageButton imageButton;
    private Button loginButton;
    private boolean eye;

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
        editTextLogInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        eye = false;

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        editTextLogInEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && editTextLogInEmail.getText().toString().equals("Email")) {
                editTextLogInEmail.setText("");
            }
            else if (!hasFocus && editTextLogInEmail.getText().toString().isEmpty()) {
                editTextLogInEmail.setText("Email");
            }
        });

        editTextLogInPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && editTextLogInPassword.getText().toString().equals("Password")) {
                editTextLogInPassword.setText("");
                editTextLogInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            else if (!hasFocus && editTextLogInPassword.getText().toString().isEmpty()) {
                editTextLogInPassword.setText("Password");
                editTextLogInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
        });

        imageButton.setOnClickListener(v -> {
            System.out.println(editTextLogInPassword.getInputType());
            if (!editTextLogInPassword.getText().toString().equals("Password") && !eye){
                editTextLogInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                eye = true;
            }
            else if (!editTextLogInPassword.getText().toString().equals("Password") && eye){
                editTextLogInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                eye = false;
                }
            });

        loginButton.setOnClickListener(v ->{
            if (!verifyLogin()){
                return;
            }
            User user = new User();
            user.setPassword(editTextLogInPassword.getText().toString());
            user.setUsername(editTextLogInEmail.getText().toString());


            Retrofit retrofit = RetrofitClient.getClient();
            DDRAPI api = retrofit.create(DDRAPI.class);

            Call<Long> call = api.loginUser(user);
            call.enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    if (response.isSuccessful()) {
                        Long token = response.body();
                        Log.d("LoginUser", "User logged in successfully, token: " + token);
                        DDRS ddrSINGLETON = DDRS.getDDRSINGLETON(getApplicationContext());
                        ddrSINGLETON.setUserId(token);

                        Intent in = new Intent(Login.this, MainUserMenu.class);
                        startActivity(in);
                    } else {
                        Log.d("LoginUser", "Failed to login user: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {
                    Log.d("LoginUser", "Error: " + t.getMessage());
                }
            });

        });

        TextView signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(v -> {
            Intent in = new Intent(Login.this, SignUp.class);
            startActivity(in);
        });
    }

    private boolean verifyLogin(){
        String emailLogin = editTextLogInEmail.getText().toString();
        String password = editTextLogInPassword.getText().toString();
        if (password.isEmpty() || emailLogin.isEmpty()) {
            new AlertDialog.Builder(Login.this)
                    .setTitle("Empty fields")
                    .setMessage("Please, fill the blanks")
                    .setPositiveButton("OK", null)
                    .show();
            return false;
        }
        return true;
    }
}