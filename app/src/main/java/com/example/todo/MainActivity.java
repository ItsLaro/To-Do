package com.example.todo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> items;

    Button addBtn;
    EditText editItem;
    RecyclerView recView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        * Behavior on startup
        * */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View references
        addBtn = findViewById(R.id.addBtn);
        editItem = findViewById(R.id.editItem);
        recView1 = findViewById(R.id.recView1);

    }
}
