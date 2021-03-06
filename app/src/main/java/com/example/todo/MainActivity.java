package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int  EDIT_TEXT_CODE = 1;

    private List<String> items;

    Button addBtn;
    EditText editItem;
    RecyclerView recView1;
    ItemsAdapter itemsAdapter;

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
        loadItems();


        //Adapter
        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent editIntent = new Intent(MainActivity.this, EditActivity.class);

                //Passing data to the intent
                editIntent.putExtra(KEY_ITEM_TEXT, items.get(position));
                editIntent.putExtra(KEY_ITEM_POSITION, position);

                startActivityForResult(editIntent, EDIT_TEXT_CODE);
            }
        };

        ItemsAdapter.OnLongClickListener onLongClickListener =  new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClick(int position) {
                deleteItem(position, itemsAdapter);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
            }
        };

        itemsAdapter = new ItemsAdapter(items, onClickListener, onLongClickListener);
        recView1.setAdapter(itemsAdapter);
        recView1.setLayoutManager(new LinearLayoutManager(this));


        //Listeners
        addBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Listener for button tap. Adds item to list.
             */

            @Override
            public void onClick(View view) {
               addItem(editItem.getText().toString(), itemsAdapter);
               editItem.setText("");
                Toast.makeText(getApplicationContext(), "New item added", Toast.LENGTH_SHORT).show();

            }
        });

        editItem.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            /**
             * Listener for 'DONE' press on keyboard from the editText. Adds items to list.
             */

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    addItem(editItem.getText().toString(), itemsAdapter);
                    editItem.setText("");
                    Toast.makeText(getApplicationContext(), "New item added", Toast.LENGTH_SHORT).show();

                    return true;
                }
                return false;
            }
        });

    }

    //CRUD Operations
    protected void addItem(String newItem, ItemsAdapter adapter){
        if(newItem == null || newItem.trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Item needs description", Toast.LENGTH_SHORT).show();
        }
        else {
            items.add(newItem);
            saveItems();
        }
    }

    protected void deleteItem(int position, ItemsAdapter adapter){
        items.remove(position);
        saveItems();
        adapter.notifyItemRemoved(position);
    }


    //Activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode ==EDIT_TEXT_CODE){
            String updatedText = data.getStringExtra(KEY_ITEM_TEXT);
            int updatedItemPosition = data.getExtras().getInt(KEY_ITEM_POSITION);

            items.set(updatedItemPosition, updatedText);
            itemsAdapter.notifyItemChanged(updatedItemPosition);

            saveItems();
            Toast.makeText(getApplicationContext(), "Item updated succesfully", Toast.LENGTH_SHORT);
        }
        else{
            Log.w("MainActivity", "Unknown call from EditActivity Result");
        }
    }

    //IO
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try {
            this.items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivty", "Error reading items,", e);
            this.items = new ArrayList<>(); //start from scratch if no file is found.
        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), this.items);
        } catch (IOException e) {
            Log.e("MainActivty", "Error saving items,", e);
            Toast.makeText(getApplicationContext(), "Could not save items properly", Toast.LENGTH_SHORT).show();
        }
    }
}
