package com.ddr;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ddr.logic.DDRAPI;
import com.ddr.logic.RetrofitClient;
import com.ddr.logic.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUp extends AppCompatActivity {
    private TextView loginBackText;
    private boolean eyeConfirmPassword;
    private boolean eyePassword;
    private ImageButton imbSignUpEyeConfirmPassword;
    private ImageButton imbSignUpEyePassword;
    private EditText editTextSignUpPassword;
    private EditText editTextSignUpConfirmPassword;
    private EditText editTextLogInUsername;
    private EditText firstNameText;
    private EditText lastNameText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            loginBackText = findViewById(R.id.loginBackText);
            editTextSignUpConfirmPassword = findViewById(R.id.editTextSignUpConfirmPassword);
            imbSignUpEyeConfirmPassword = findViewById(R.id.imbSignUpEyeConfirmPassword);
            imbSignUpEyePassword = findViewById(R.id.imbSignUpEyePassword);
            editTextSignUpPassword = findViewById(R.id.editTextSignUpPassword);
            signUpButton = findViewById(R.id.letsFlyButton);
            editTextLogInUsername = findViewById(R.id.editTextLogInUsername);
            firstNameText = findViewById(R.id.firstNameText);
            lastNameText = findViewById(R.id.lastNameText);

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

            signUpButton.setOnClickListener(v1 -> {
                //TODO: add validation to fields
                User user = new User();
                user.setUsername(editTextLogInUsername.getText().toString());
                user.setPassword(editTextSignUpPassword.getText().toString());
                user.setFirstName(firstNameText.getText().toString());
                user.setLastName(lastNameText.getText().toString());

                Retrofit retrofit = RetrofitClient.getClient();
                DDRAPI api = retrofit.create(DDRAPI.class);

                Call<Void> call = api.registerUser(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("RegisterUser", "User registered successfully");
                            Intent in = new Intent(SignUp.this, Login.class);
                            startActivity(in);
                        } else {
                            Log.d("RegisterUser", "Failed to register user: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("RegisterUser", "Error: " + t.getMessage());
                    }
                });
            });

            return insets;
        });
    }
}
