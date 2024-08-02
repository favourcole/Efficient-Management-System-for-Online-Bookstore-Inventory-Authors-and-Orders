package uk.ac.le.co2013.parttwo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShoppingListActivity extends AppCompatActivity {
    private ShoppingListActivityViewModel viewModel;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    private FloatingActionButton fabAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        recyclerView = findViewById(R.id.recyclerViewProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabAddProduct = findViewById(R.id.fab_add_product);
        fabAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingListActivity.this, AddProductActivity.class);
            long shoppingListId = getIntent().getLongExtra("shoppingListId", -1);
            intent.putExtra("shoppingListId", shoppingListId);
            startActivity(intent);
        });

        long shoppingListId = getIntent().getLongExtra("shoppingListId", -1);
        if (shoppingListId != -1) {
            ShoppingListViewModelFactory factory = new ShoppingListViewModelFactory(getApplication(), shoppingListId);
            viewModel = new ViewModelProvider(this, factory).get(ShoppingListActivityViewModel.class);

            viewModel.getProducts().observe(this, products -> {
                adapter = new ProductAdapter(this, products);
                adapter.setOnProductClickListener(product -> {
                    showProductOptionsDialog(product, shoppingListId);
                });
                recyclerView.setAdapter(adapter);
            });
        }
    }

    private void showProductOptionsDialog(Product product, long shoppingListId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Product Options")
                .setItems(new CharSequence[]{"Edit", "Delete"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            Intent intent = new Intent(ShoppingListActivity.this, UpdateProductActivity.class);
                            intent.putExtra("productId", product.getProductId());
                            intent.putExtra("shoppingListId", shoppingListId);
                            startActivity(intent);
                            break;
                        case 1:
                            viewModel.deleteProduct(product);
                            break;
                    }
                });
        builder.create().show();
    }
}










