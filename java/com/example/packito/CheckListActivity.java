package com.example.packito;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public abstract class CheckListActivity extends AppCompatActivity {
    private CheckBox checkBox;
    private boolean[] checkedState;
    protected ChecklistAdapter adapter;
    private FloatingActionButton addCheckBox;
    private Button end;
    private ImageButton back;
    private RecyclerView checklistRecyclerView;

    protected abstract String[] getListOfItems();
    protected abstract String getActivityType();

    @Override
    protected void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.saveCheckedState();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);

        checklistRecyclerView = findViewById(R.id.ListofItems);
        String[] items = getListOfItems();

        adapter = new ChecklistAdapter(this, items, getActivityType());
        adapter.loadCheckedState();
        checklistRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        checklistRecyclerView.setLayoutManager(layoutManager);

        back = findViewById(R.id.backtostart);
        end = findViewById(R.id.end);
        addCheckBox = findViewById(R.id.addCheckBox);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    adapter.uncheckAllItems();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        addCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckListActivity.this);
                builder.setTitle("Γράψε ένα καινούριο αντικείμενο.");

                final EditText input = new EditText(CheckListActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newItemText = input.getText().toString().trim();
                        if (!newItemText.isEmpty()) {
                            adapter.addItem(newItemText); // Add the new item
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }
}
