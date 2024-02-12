package com.example.packito;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder>{
    private String sharedPreferencesKey;
    private Context context;
    private String[] items;
    private boolean[] checkedState;
    private String newitemList;
    private SharedPreferences sharedPreferences;

    public ChecklistAdapter(Context context, String[] items, String sharedPreferencesKey) {
        this.items = items;
        this.context = context;
        this.checkedState = new boolean[items.length];
        Arrays.fill(checkedState, false);
        this.sharedPreferencesKey = sharedPreferencesKey;
        this.sharedPreferences = context.getSharedPreferences("ChecklistPrefs_" + sharedPreferencesKey, Context.MODE_PRIVATE);
        loadCheckedState();
    }

    public void uncheckAllItems() {
        Arrays.fill(checkedState, false);
        saveCheckedState();
    }

    @Override
    public ChecklistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout, parent, false);
        return new ChecklistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChecklistAdapter.ViewHolder holder, int position) {
        String item = items[position];
        holder.itemText.setText(item);

        holder.checkBox.setTag(position);

        holder.checkBox.setChecked(checkedState[position]);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = (int) holder.checkBox.getTag();
                checkedState[pos] = isChecked;
                saveCheckedState();
            }
        });
    }

    public void saveCheckedState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < items.length; i++) {
            editor.putBoolean("item_" + i, checkedState[i]);
        }
        editor.apply();
    }

    public void loadCheckedState() {
        for (int i = 0; i < items.length; i++) {
            checkedState[i] = sharedPreferences.getBoolean("item_" + i, false);
        }
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public void addItem(String newItemText) {
        String[] updatedItems = Arrays.copyOf(items, items.length + 1);
        updatedItems[items.length] = newItemText;

        items = updatedItems;

        saveNewItem(newItemText);

        boolean[] updatedCheckedState = Arrays.copyOf(checkedState, checkedState.length + 1);
        updatedCheckedState[checkedState.length] = false;

        checkedState = updatedCheckedState;

        notifyItemInserted(items.length - 1);
    }

    private void saveNewItem(String newItem) {  Set<String> existingItems = new HashSet<>(Arrays.asList(items));
        existingItems.add(newItem);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(sharedPreferencesKey, existingItems);
        editor.apply();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView itemText;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            itemText = itemView.findViewById(R.id.ListofItems);
        }
    }
}