package uk.ac.le.co2013.parttwo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products WHERE shoppingListId = :shoppingListId")
    LiveData<List<Product>> getProductsByShoppingListId(long shoppingListId);

    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("DELETE FROM products WHERE shoppingListId = :shoppingListId")
    void deleteProductsByShoppingListId(long shoppingListId);

    @Query("SELECT * FROM products WHERE productId = :productId")
    Product getProductById(long productId);

    @Query("SELECT * FROM products WHERE name = :productName AND shoppingListId = :shoppingListId")
    LiveData<List<Product>> findProductByNameAndShoppingListId(String productName, long shoppingListId);





}
