package uk.ac.le.co2013.parttwo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.le.co2013.parttwo.R;
import uk.ac.le.co2013.parttwo.ShoppingDatabase;
import uk.ac.le.co2013.parttwo.Product;
import uk.ac.le.co2013.parttwo.ShoppingListActivity;
import uk.ac.le.co2013.parttwo.ShoppingRepository;

public class UpdateProductActivity extends AppCompatActivity {
    private ShoppingRepository repository;
    private TextView productNameTextView;
    private TextView productQuantityTextView;
    private TextView productUnitTextView;
    private Button plusButton;
    private Button minusButton;
    private Button saveButton;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_product_activity);

        repository = new ShoppingRepository(getApplication());

        productNameTextView = findViewById(R.id.updateproductname);
        productQuantityTextView = findViewById(R.id.updateproductquantity);
        productUnitTextView = findViewById(R.id.updateproductunit);
        plusButton = findViewById(R.id.btnPlus);
        minusButton = findViewById(R.id.btnMinus);
        saveButton = findViewById(R.id.btnSave);

        long productId = getIntent().getLongExtra("productId", 0);

        ShoppingDatabase.databaseWriteExecutor.execute(() -> {
            product = repository.getProductById(productId);
            runOnUiThread(() -> {
                bindProductData();
                setupClickListeners();
            });
        });
    }

    private void bindProductData() {
        productNameTextView.setText(product.getName());
        productQuantityTextView.setText(String.valueOf(product.getQuantity()));
        productUnitTextView.setText(product.getUnit());
    }

    private void setupClickListeners() {
        plusButton.setOnClickListener(v -> increaseQuantity());
        minusButton.setOnClickListener(v -> decreaseQuantity());
        saveButton.setOnClickListener(v -> saveProduct());
    }

    private void increaseQuantity() {
        int currentQuantity = product.getQuantity();
        product.setQuantity(currentQuantity + 1);
        productQuantityTextView.setText(String.valueOf(product.getQuantity()));
    }

    private void decreaseQuantity() {
        int currentQuantity = product.getQuantity();
        if (currentQuantity > 0) {
            product.setQuantity(currentQuantity - 1);
            productQuantityTextView.setText(String.valueOf(product.getQuantity()));
        }
    }

    private void saveProduct() {
        ShoppingDatabase.databaseWriteExecutor.execute(() -> {
            repository.updateProduct(product);
            runOnUiThread(this::navigateToShoppingList);
        });
    }

    private void navigateToShoppingList() {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        long shoppingListId = getIntent().getLongExtra("shoppingListId", -1);
        intent.putExtra("shoppingListId", shoppingListId);
        startActivity(intent);
        finish();
    }

}










