//package tarazico.thebookmarket.model;
//
//import java.util.List;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//import tarazico.thebookmarket.model.Book;
//
//@Dao
//public interface BookDao {
//
//    @Insert
//    void insert(Book book);
//
//    @Update
//    void update(Book book);
//
//    @Delete
//    void delete(Book book);
//
//    @Query("SELECT * FROM book_table")
//    LiveData<List<Book>> getAllBooks();
//}
