package uk.ac.le.co2013.parttwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity {

    private AddProductActivityViewModel viewModel;
    private EditText etProductName, etProductQuantity;
    private Spinner spinnerUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        etProductName = findViewById(R.id.etProductName);
        etProductQuantity = findViewById(R.id.etProductQuantity);
        spinnerUnit = findViewById(R.id.spinner_product);

        viewModel = new ViewModelProvider(this).get(AddProductActivityViewModel.class);

        ArrayList<String> units = new ArrayList<>();
        units.add("kg");
        units.add("litre");
        units.add("Unit");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerUnit.setAdapter(adapter);

        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedUnit = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(AddProductActivity.this, "Selected: " + selectedUnit, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        findViewById(R.id.btnAddProduct).setOnClickListener(v -> {
            String productName = etProductName.getText().toString().trim();
            int quantity = Integer.parseInt(etProductQuantity.getText().toString().trim());
            String unit = spinnerUnit.getSelectedItem().toString();


            long shoppingListId = getIntent().getLongExtra("shoppingListId", -1);

            if (!productName.isEmpty() && quantity > 0 && !unit.isEmpty()) {
                Product product = new Product(shoppingListId, productName, quantity, unit);
                viewModel.insertProduct(product);
                navigateToShoppingListActivity();
            } else {
                Toast.makeText(AddProductActivity.this, "Please enter valid product details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToShoppingListActivity() {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        long shoppingListId = getIntent().getLongExtra("shoppingListId", -1);
        intent.putExtra("shoppingListId", shoppingListId);
        startActivity(intent);
        finish();
    }
}



