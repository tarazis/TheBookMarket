package tarazico.thebookmarket.view.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import tarazico.thebookmarket.R;
import tarazico.thebookmarket.model.user.User;
import tarazico.thebookmarket.view.main_book.BookActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean toggleLogin = false;

    private TextView txt_login;
    private EditText username;
    private EditText password;
    private RelativeLayout relative_layout;
    private ImageView img_view;
    private Button btn_signup;

    private FirebaseAuth myAuth;
    private DatabaseReference databaseReference;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        txt_login = findViewById(R.id.txtLogin);
        btn_signup = findViewById(R.id.btnSignup);
        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);

        myAuth = FirebaseAuth.getInstance();

        txt_login.setOnClickListener(this); // Whenever this is cicked, we will switch from sign up to sign in
    }

    public void mainBtnClicked(View view) {
        String email = username.getText().toString();
        String pass = password.getText().toString();
        boolean isValidFields = this.checkValidFields(email, pass); // checks if email and password are valid and non-empty

        if (isValidFields) { // if fields are valid then either sign up or sign in.

            if (!toggleLogin) { // Sign up
                this.signUp(email, pass);

            } else { // Sign in
                signIn(email, pass);
            }

        } else { // if fields are not valid then email and password have wrong syntax
            Toast.makeText(this, "Please enter a valid email and password", Toast.LENGTH_LONG).show();
        }

    }

    public void signUp(final String email, String password) {

        myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) { // if sign up is successful
                    user = new User(myAuth.getUid(), email);
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("users").push();
                    String key = databaseReference.getKey();
                    databaseReference.setValue(user);


                    Toast.makeText(getApplicationContext(), "Successfully signed up!", Toast.LENGTH_LONG).show();
                } else { // if sign up has failed
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void signIn(String email, String password) {
        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UserLoginActivity.this, BookActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public boolean checkValidFields(String email, String password) {
        boolean isValid;
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Error: email or password is blank. Please try again! :)", Toast.LENGTH_LONG);
            isValid = false;
        } else {
            isValid = true;
        }

        return isValid;
    }

    @Override
    public void onClick(View view) { //on click toggles sign up or log in.
        if (view.getId() == R.id.txtLogin) {

            if (!toggleLogin) {
                toggleLogin = true;
                btn_signup.setText("Login");
                txt_login.setText("Sign up instead?");

            } else {
                toggleLogin = false;
                btn_signup.setText("Sign Up");
                txt_login.setText("Login instead?");

            }
        }

    }
}
