//package tarazico.thebookmarket.model;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import androidx.annotation.NonNull;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//@Database(entities = {Book.class}, version = 1)
//public abstract class BookDatabase extends RoomDatabase {
//
//    private static BookDatabase instance;
//
//    public abstract BookDao bookDao();
//
//    public static synchronized BookDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(), BookDatabase.class, "book_database")
//                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
//                    .build();
//
//        }
//
//        return instance;
//    }
//
//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//        }
//    };
//
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//        private BookDao bookDao;
//        private PopulateDbAsyncTask(BookDatabase db) {
//            bookDao = db.bookDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            bookDao.insert(new Book("title1", "course1", "description1", 5.0));
//            bookDao.insert(new Book("title2", "course2", "description2", 5.0));
//            bookDao.insert(new Book("title3", "course3", "description3", 5.0));
//          //  bookDao.insert(new Book("title4", "course4", "description5", 6.0));
//            return null;
//        }
//    }
//}
