package uk.ac.le.co2013.parttwo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ShoppingListViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final long shoppingListId;

    public ShoppingListViewModelFactory(Application application, long shoppingListId) {
        this.application = application;
        this.shoppingListId = shoppingListId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShoppingListActivityViewModel.class)) {
            return (T) new ShoppingListActivityViewModel(application, shoppingListId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

