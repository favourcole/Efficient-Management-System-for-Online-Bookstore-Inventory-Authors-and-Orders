package uk.ac.le.co2013.parttwo;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;

public class AddProductActivityViewModel extends AndroidViewModel {
    private ShoppingRepository repository;

    public AddProductActivityViewModel(Application application) {
        super(application);
        repository = new ShoppingRepository(application);
    }

    public void insertProduct(Product product) {
        repository.insertProduct(product);
    }


}

