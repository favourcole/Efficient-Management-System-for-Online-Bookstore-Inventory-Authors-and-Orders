package uk.ac.le.co2013.parttwo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    TextView productNameTextView;
    TextView productQuantityTextView;
    TextView productUnitTextView;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productNameTextView = itemView.findViewById(R.id.productname);
        productQuantityTextView = itemView.findViewById(R.id.productquantity);
        productUnitTextView = itemView.findViewById(R.id.productunit);

    }
}
