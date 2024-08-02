package uk.ac.le.co2013.parttwo;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ShoppingRepository {
    private ShoppingListDao shoppingListDao;
    private ProductDao productDao;
    private LiveData<List<ShoppingList>> allShoppingLists;
    private LiveData<List<Product>> allProducts;

    public ShoppingRepository(Application application) {
        ShoppingDatabase database = ShoppingDatabase.getDatabase(application);
        shoppingListDao = database.shoppingListDao();
        productDao = database.productDao();
        allShoppingLists = shoppingListDao.getAllShoppingLists();
    }

    public LiveData<List<ShoppingList>> getAllShoppingLists() {
        return allShoppingLists;
    }
    public LiveData<List<Product>> getProductsByShoppingListId(long shoppingListId) {
        return productDao.getProductsByShoppingListId(shoppingListId);
    }

    public void insertShoppingList(ShoppingList shoppingList) {
        ShoppingDatabase.databaseWriteExecutor.execute(() -> {
            shoppingListDao.insertShoppingList(shoppingList);
        });
    }

    public void updateShoppingList(ShoppingList shoppingList) {
        ShoppingDatabase.databaseWriteExecutor.execute(() -> {
            shoppingListDao.updateShoppingList(shoppingList);
        });
    }

    public void deleteShoppingList(ShoppingList shoppingList) {
        ShoppingDatabase.databaseWriteExecutor.execute(() -> {
            shoppingListDao.deleteShoppingList(shoppingList);
        });
    }

    public void insertProduct(Product product) {
        ShoppingDatabase.databaseWriteExecutor.execute(() -> {
            productDao.insertProduct(product);
        });
    }

    public void updateProduct(Product product) {
        ShoppingDatabase.databaseWriteExecutor.execute(() -> {
            productDao.updateProduct(product);
        });
    }

    public void deleteProduct(Product product) {
        ShoppingDatabase.databaseWriteExecutor.execute(() -> {
            productDao.deleteProduct(product);
        });
    }
    public LiveData<List<Product>> isProductNameExists(String productName, long shoppingListId) {
        return productDao.findProductByNameAndShoppingListId(productName,shoppingListId);


    }



    public Product getProductById(long productId) {
        return productDao.getProductById(productId);
    }
}
