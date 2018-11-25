package tarazico.thebookmarket.view.main_book;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import tarazico.thebookmarket.R;
//import tarazico.thebookmarket.model.Book;
import tarazico.thebookmarket.model.book.Book;
import tarazico.thebookmarket.utility.LazyLoadUtility;

public class BookAdapter extends RecyclerView.Adapter <BookAdapter.BookHolder>{
    private Context mContext;

    private List<Book> books = new ArrayList<>();

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOAD = 1;
    public LazyLoadUtility lazyLoad;
    boolean isLoading;
    Activity activity;
    int maxVisible = 2;
    int lastVisibleItem, totalItemCount;

    private OnItemClickListener myListener;

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        myListener = listener;
    }

    public BookAdapter(Context context, List<Book> books) {
        this.mContext = context;
        this.books = books;
    }

    public BookAdapter(RecyclerView recyclerView, Context context, List<Book> books) {
        this.mContext = context;
        this.books = books;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!(isLoading) && totalItemCount <= (lastVisibleItem + maxVisible)) {
                    if (lazyLoad != null) {
                        lazyLoad.onLoadMore();
                    }
                    isLoading = true;
                }

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return books.get(position) == null ? VIEW_TYPE_LOAD:VIEW_TYPE_ITEM;
    }


    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_view, parent, false);

            return new BookHolder(itemView, myListener);
//        }

    }

    @Override
    public void onBindViewHolder(@NonNull final BookHolder holder, int position) {
        Book book = books.get(position);
        holder.textViewTitle.setText(book.getTitle());
        holder.textViewPrice.setText("$"+ String.valueOf(book.getPrice()));
        Picasso.get()
                .load(book.getUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.bookMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.bookMenu);


            }
        });


    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.book_info, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.buy:
                    Toast.makeText(mContext, "Please click on the name of the book (below the picture) in order to buy! :)", Toast.LENGTH_LONG).show();

//                    BookActivity.sendBuyer();
                    return true;
                default:
            }
            return false;
        }


    }


   public class BookHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewPrice;
        private ImageView imageView;
        private ImageView bookMenu;

        public BookHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewPrice = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.thumbnail);
            bookMenu = itemView.findViewById(R.id.cardMenu);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClickListener(position);
                        }
                    }

                }
            });

        }

    }

    public class LoadingHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

}
