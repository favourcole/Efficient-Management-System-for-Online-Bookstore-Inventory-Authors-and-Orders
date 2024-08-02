package uk.ac.le.co2013.parttwo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel viewModel;
    private FloatingActionButton fabCreateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getAllShoppingLists().observe(this, shoppingLists -> {
            ShoppingListAdapter adapter = new ShoppingListAdapter(getApplicationContext(), shoppingLists);
            adapter.setOnItemClickListener(shoppingList -> {
                Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                intent.putExtra("shoppingListId", shoppingList.getListId());
                startActivity(intent);
            });
            adapter.setOnItemLongClickListener(shoppingList -> {
                showDeleteConfirmationDialog(shoppingList);
                return true;
            });
            recyclerView.setAdapter(adapter);
        });

        fabCreateList = findViewById(R.id.fab_create_list);
        fabCreateList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateListActivity.class);
            startActivity(intent);
        });
    }

    private void showDeleteConfirmationDialog(ShoppingList shoppingList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Shopping List");
        builder.setMessage("Are you sure you want to delete this shopping list?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            viewModel.deleteShoppingList(shoppingList);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}




