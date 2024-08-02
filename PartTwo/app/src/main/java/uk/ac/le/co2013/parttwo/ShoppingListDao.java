package uk.ac.le.co2013.parttwo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ShoppingListDao {
    @Query("SELECT * FROM shopping_lists")
    LiveData<List<ShoppingList>> getAllShoppingLists();

    @Insert
    void insertShoppingList(ShoppingList shoppingList);

    @Update
    void updateShoppingList(ShoppingList shoppingList);

    @Delete
    void deleteShoppingList(ShoppingList shoppingList);
}
