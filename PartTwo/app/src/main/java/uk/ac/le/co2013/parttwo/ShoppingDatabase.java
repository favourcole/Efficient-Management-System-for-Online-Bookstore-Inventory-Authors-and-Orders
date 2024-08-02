package uk.ac.le.co2013.parttwo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ShoppingList.class, Product.class}, version = 1)
public abstract class ShoppingDatabase extends RoomDatabase {
    public abstract ShoppingListDao shoppingListDao();
    public abstract ProductDao productDao();

    private static volatile ShoppingDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ShoppingDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoppingDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ShoppingDatabase.class, "shopping_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

