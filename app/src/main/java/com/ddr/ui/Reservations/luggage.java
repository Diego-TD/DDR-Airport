package com.ddr.ui.Reservations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ddr.R;

import android.app.Activity;

public class luggage extends AppCompatActivity {

    private CardView zero;
    private CardView basic;
    private CardView plus;
    private Button accept;
    private TextView luggageText;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luggage);
        context = this;

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        zero = findViewById(R.id.zero);
        basic = findViewById(R.id.basic);
        plus = findViewById(R.id.plus);
        accept = findViewById(R.id.accept);
        luggageText = findViewById(R.id.luggage);

        String luggage = getIntent().getStringExtra("maleta");
        luggageText.setText(luggage);

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zero.setBackgroundResource(R.drawable.border);
                basic.setBackgroundResource(R.color.grey);
                plus.setBackgroundResource(R.color.grey);
                luggageText.setText("Zero");
            }
        });

        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basic.setBackgroundResource(R.drawable.border);
                zero.setBackgroundResource(R.color.grey);
                plus.setBackgroundResource(R.color.grey);
                luggageText.setText("Basic");
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus.setBackgroundResource(R.drawable.border);
                basic.setBackgroundResource(R.color.grey);
                zero.setBackgroundResource(R.color.grey);
                luggageText.setText("Plus");
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("luggage", luggageText.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}

