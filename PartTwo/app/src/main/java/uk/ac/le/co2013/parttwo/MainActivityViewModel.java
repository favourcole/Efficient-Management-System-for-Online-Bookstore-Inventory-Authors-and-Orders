package uk.ac.le.co2013.parttwo;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private ShoppingRepository repository;
    private LiveData<List<ShoppingList>> allShoppingLists;

    public MainActivityViewModel(Application application) {
        super(application);
        repository = new ShoppingRepository(application);
        allShoppingLists = repository.getAllShoppingLists();
    }

    public LiveData<List<ShoppingList>> getAllShoppingLists() {
        return allShoppingLists;
    }

    public void deleteShoppingList(ShoppingList shoppingList) {
        repository.deleteShoppingList(shoppingList);
    }
}

