package uk.ac.le.co2013.parttwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListHolder> {
    private Context context;
    private List<ShoppingList> shoppingLists;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    public ShoppingListAdapter(Context context, List<ShoppingList> shoppingLists) {
        this.context = context;
        this.shoppingLists = shoppingLists;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public ShoppingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShoppingListHolder(LayoutInflater.from(context).inflate(R.layout.shoppinglistitemlayout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListHolder holder, int position) {
        holder.nameView.setText(shoppingLists.get(position).getName());
        holder.imageView.setImageResource(shoppingLists.get(position).getImage());
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(shoppingLists.get(position));
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                return longClickListener.onItemLongClick(shoppingLists.get(position));
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ShoppingList shoppingList);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(ShoppingList shoppingList);
    }
}




