package uk.ac.le.co2013.parttwo;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ShoppingListActivityViewModel extends AndroidViewModel {
    private ShoppingRepository repository;
    private LiveData<List<Product>> products;

    public ShoppingListActivityViewModel(Application application, long shoppingListId) {
        super(application);
        repository = new ShoppingRepository(application);
        products = repository.getProductsByShoppingListId(shoppingListId);
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public void deleteProduct(Product product) {
        repository.deleteProduct(product);
    }
}

