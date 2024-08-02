package uk.ac.le.co2013.parttwo;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;

public class CreateListActivityViewModel extends AndroidViewModel {
    private ShoppingRepository repository;

    private int[] imageResourceIds = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image3,
            R.drawable.image5,
    };

    private int currentImageIndex = 0;




    public CreateListActivityViewModel(Application application) {
        super(application);
        repository = new ShoppingRepository(application);
    }

    public void insertShoppingList(String name) {
        int image = imageResourceIds[currentImageIndex];
        ShoppingList shoppingList = new ShoppingList(name, image);
        repository.insertShoppingList(shoppingList);
        currentImageIndex = (currentImageIndex + 1) % imageResourceIds.length;
    }
}
