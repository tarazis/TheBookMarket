package tarazico.thebookmarket.view.add_book;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import tarazico.thebookmarket.R;
//import tarazico.thebookmarket.model.Book;
import tarazico.thebookmarket.model.book.BookPicture;
import tarazico.thebookmarket.model.book.Book;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddBookActivity extends AppCompatActivity {

    /*
    unique names for book attributes in order to receive them in the Book Activity
     */
    public static final String EXTRA_TITLE = "tarazico.thebookmarket.view.EXTRA_TITLE";
    public static final String EXTRA_COURSE = "tarazico.thebookmarket.view.EXTRA_COURSE";
    public static final String EXTRA_DESC = "tarazico.thebookmarket.view.EXTRA_DESC";
    public static final String EXTRA_PRICE = "tarazico.thebookmarket.view.EXTRA_PRICE";

    public static final int PERMISSION_REQUEST = 1;
    public static final int ADD_PICTURE_REQUEST = 2;



    private TextInputLayout editTextBookTitle;
    private TextInputLayout editTextCourseName;
    private TextInputLayout editTextDesc;
    private TextInputLayout editTextPrice;

    BookPicture bookPicture;
    private Uri imageUri;
    String URL;


// Fire base requirements:
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String userID;

    private StorageReference storageReference;
//   private DatabaseReference storageReference;
    private StorageReference fileReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Book Upload");
        editTextBookTitle = findViewById(R.id.upload_book_title);
        editTextCourseName = findViewById(R.id.upload_book_course_name);
        editTextDesc =  findViewById(R.id.upload_book_description);
        editTextPrice =  findViewById(R.id.upload_book_price);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference("uploads");


        userID = auth.getCurrentUser().getUid();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_book_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.save_book:
                requestPhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    public void saveBook() {

        String title = editTextBookTitle.getEditText().getText().toString();
        String courseName = editTextCourseName.getEditText().getText().toString();
        String desc = editTextDesc.getEditText().getText().toString();
        String price = editTextPrice.getEditText().getText().toString();

        if(title.trim().isEmpty() || courseName.trim().isEmpty()) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Book book2 = new Book(this.userID, title, courseName, desc, Double.parseDouble(price), this.URL);

        String key = reference.child("Books").push().getKey();
        reference.child("Books").child(key).setValue(book2);
        finish();



    }

    public void requestPhoto() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // If phone supports asking for permission, then ask. Else: request photo.

            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { // if permession is not granted, ASK for it.
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST); // request permissions to access photos

            } else {
                accessPhotos(); // directs user to photo gallery
            }
        } else {
            accessPhotos(); // directs user to photo gallery

        }

    }

    public void accessPhotos() { // once permissions are granted (or not needed), go ahead and access photos

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ADD_PICTURE_REQUEST);

    }

    private String getFileExtension(Uri uri) { //returns picture extension
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {  // invoked when permissions are asked
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // if permissions are granted, access photos
                accessPhotos();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PICTURE_REQUEST && resultCode == RESULT_OK && data != null ) {

            this.imageUri = data.getData(); // get image URI

            Toast.makeText(AddBookActivity.this, "Loading.." , Toast.LENGTH_SHORT).show();

            fileReference = storageReference.child(System.currentTimeMillis() // create a file name that is unique
                    + "." + getFileExtension(this.imageUri));


                fileReference.putFile(this.imageUri)
                        .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                return fileReference.getDownloadUrl();
                            }

                        })
                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                    Uri downloadUri = task.getResult();
                                    URL = downloadUri.toString();
                                    saveBook();
                            }
                        });
        }


    }
}

