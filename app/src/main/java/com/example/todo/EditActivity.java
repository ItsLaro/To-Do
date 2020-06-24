package com.example.todo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    EditText updateText;
    Button UpdateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        updateText = findViewById(R.id.updateText);
        UpdateBtn = findViewById(R.id.updateBtn);
    }
}