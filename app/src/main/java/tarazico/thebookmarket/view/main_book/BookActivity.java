package tarazico.thebookmarket.view.main_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import tarazico.thebookmarket.R;
//import tarazico.thebookmarket.model.Book;
import tarazico.thebookmarket.model.book.Book;
import tarazico.thebookmarket.model.user.User;
import tarazico.thebookmarket.view.add_book.AddBookActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private BookAdapter bookAdapter;
    private RecyclerView recyclerView;
    private String keyword;
    private TextInputLayout searchBar;
    private FirebaseAuth auth;

    //    private BookViewModel bookViewModel; // which should correspond to the current activity: BookActivity
    private DatabaseReference databaseReference;
    private List<Book> books;

    FirebaseRecyclerOptions<Book> options;
    FirebaseRecyclerAdapter<Book, BookViewHolder> fAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);



        /*
        Add book button on click
         */
        FloatingActionButton buttonAddBook = findViewById(R.id.add_book);
        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookActivity.this, AddBookActivity.class);
                startActivity(intent);

            }
        });

        searchBar = findViewById(R.id.search_bar);

        /*
        search bar listener for new searches
         */
        searchBar.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()) {
                    onSearch(editable.toString());
                } else {
                    onSearch("");
                }
            }
        });

        onSearch(""); // View all books available in the database


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);

        books = new ArrayList<>();


    }

    public void onSearch(final String keyword) {
//        Toast.makeText(BookActivity.this, "Updating.." + keyword, Toast.LENGTH_SHORT).show();

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        databaseReference.orderByChild("title")

                .startAt(keyword)
                .endAt(keyword + "\uf8ff")

//
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            books.clear();

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Log.i("new book: ", postSnapshot.getValue(Book.class).getTitle());
                                Book book = new Book(
                                        postSnapshot.getValue(Book.class).getUserID(),
                                        postSnapshot.getValue(Book.class).getTitle(),
                                        postSnapshot.getValue(Book.class).getCourseName(),
                                        postSnapshot.getValue(Book.class).getDesc(),
                                        postSnapshot.getValue(Book.class).getPrice(),
                                        postSnapshot.getValue(Book.class).getUrl());
//                   System.out.println() ;
                                books.add(book);
                            }
                        } else {
                            books.clear();
                        }

                        bookAdapter = new BookAdapter(BookActivity.this, books);
                        recyclerView.setAdapter(bookAdapter);
                        bookAdapter.notifyDataSetChanged();
                        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position) {
                                sendBuyer(position);
                            }
                        });



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(BookActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void sendBuyer(int position) {


        Book book = books.get(position);
        Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("userID").equalTo(book.getUserID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] recepient = new String[1];
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    recepient[0] = postSnapshot.getValue(User.class).getEmail();
                }
//                Toast.makeText(BookActivity.this, recepient, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, recepient);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Interested in your book!");

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "choose an email client"));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile, menu);
        return true;
    }

}
