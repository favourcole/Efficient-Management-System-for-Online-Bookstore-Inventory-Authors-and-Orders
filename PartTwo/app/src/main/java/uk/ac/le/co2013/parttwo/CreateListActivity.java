package uk.ac.le.co2013.parttwo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class CreateListActivity extends AppCompatActivity {

    private EditText etListName;
    private Button btnCreateList;
    private CreateListActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        etListName = findViewById(R.id.etListName);
        btnCreateList = findViewById(R.id.btnCreateList);

        viewModel = new ViewModelProvider(this).get(CreateListActivityViewModel.class);

        btnCreateList.setOnClickListener(v -> {
            String listName = etListName.getText().toString().trim();
            if (!listName.isEmpty()) {
                viewModel.insertShoppingList(listName);
                navigateToMainActivity();
            } else {
                Toast.makeText(this, "Please enter a list name", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

