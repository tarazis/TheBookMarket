package tarazico.thebookmarket.view.main_book;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import tarazico.thebookmarket.R;

public class BookViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public TextView textViewPrice;
    public ImageView imageView;
    public ImageView bookMenu;

    public BookViewHolder(View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.title);
        textViewPrice = itemView.findViewById(R.id.price);
        imageView = itemView.findViewById(R.id.thumbnail);
        bookMenu = itemView.findViewById(R.id.cardMenu);

    }
}
