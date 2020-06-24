package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    EditText updateText;
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        updateText = findViewById(R.id.updateText);
        updateBtn = findViewById(R.id.updateBtn);

        getSupportActionBar().setTitle("Edit Item");

        updateText.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReturn = new Intent();
                //Return updated text
                intentReturn.putExtra(MainActivity.KEY_ITEM_TEXT, updateText.getText().toString());
                //Returns position of affected item
                intentReturn.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                setResult(RESULT_OK, intentReturn);
                finish();
            }
        });

    }
}