package com.example.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> items;

    Button addBtn;
    EditText editItem;
    RecyclerView recView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        * Runs on startup.
        * */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //sets current layout to main layout

        //View references
        addBtn = findViewById(R.id.addBtn);
        editItem = findViewById(R.id.editItem);
        recView1 = findViewById(R.id.recView1);

        //Data
        items = new ArrayList<>();
        items.add("Go to the theater");
        items.add("Visit my grandmother");
        items.add("Vacation to Punta Cana");

        //Adapter
        final ItemsAdapter itemsAdapter = new ItemsAdapter(items);
        recView1.setAdapter(itemsAdapter);
        recView1.setLayoutManager(new LinearLayoutManager(this));

        //Listeners
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addItem(editItem.getText().toString(), itemsAdapter);
               editItem.setText("");

            }
        });
    }

    //CRUD Operations
    protected void addItem(String newItem, ItemsAdapter adapter){
        items.add(newItem);
        adapter.notifyItemInserted(items.size()-1);

    }
}
