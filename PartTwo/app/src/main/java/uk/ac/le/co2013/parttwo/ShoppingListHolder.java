package uk.ac.le.co2013.parttwo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingListHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView ;
    public ShoppingListHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.name);

    }

}
