//package tarazico.thebookmarket.view.main_book;
//
//import android.app.Application;
//
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//import tarazico.thebookmarket.model.Book;
//import tarazico.thebookmarket.model.BookRepository;
//
//public class BookViewModel extends AndroidViewModel {
//    private BookRepository repository;
//    private LiveData<List<Book>> allBooks;
//
//    public BookViewModel(@NonNull Application application) {
//        super(application);
//        repository = new BookRepository(application);
//        allBooks = repository.getAllBooks();
//    }
//
//    public void insert (Book book) {
//        repository.insert(book);
//    }
//
//    public void update(Book book) {
//        repository.update(book);
//    }
//
//    public void delete(Book book) {
//        repository.delete(book);
//    }
//
//    public LiveData<List<Book>> getAllBooks() {
//        return allBooks;
//    }
//}
