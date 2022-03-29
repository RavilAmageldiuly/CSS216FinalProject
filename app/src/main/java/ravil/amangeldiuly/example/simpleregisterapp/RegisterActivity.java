package ravil.amangeldiuly.example.simpleregisterapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
    );
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9@#$%^&_+=]{8,}");

    TextInputLayout textInputEmail;
    TextInputLayout textInputUsername;
    TextInputLayout textInputPassword;
    TextInputLayout textInputRePassword;

    DBHelper DB;
    DBTheme dbTheme;

    boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty!");
            return false;
        } else if (!EMAIL_PATTERN.matcher(emailInput).matches()) {
            textInputEmail.setError("Invalid format of email address!");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty!");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }

    boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty!");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password length must be at least 8 characters");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    boolean validateRePassword() {
        String rePasswordInput = textInputRePassword.getEditText().getText().toString().trim();

        if (rePasswordInput.isEmpty()) {
            textInputRePassword.setError("Field can't be empty!");
            return false;
        } else if (!textInputPassword.getEditText().getText().toString().trim().equals(textInputRePassword.getEditText().getText().toString().trim())) {
            textInputRePassword.setError("Password mismatch!");
            return false;
        } else {
            textInputRePassword.setError(null);
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = new Intent(this, LoginActivity.class);
        TextView signInTextView = findViewById(R.id.signInTextView);
        signInTextView.setOnClickListener(view -> {
            startActivity(intent);
            finish();
        });

        textInputEmail = findViewById(R.id.text_input_email);
        textInputUsername = findViewById(R.id.text_input_username);
        textInputPassword = findViewById(R.id.text_input_password);
        textInputRePassword = findViewById(R.id.text_input_retype_password);
        DB = new DBHelper(this);
        dbTheme = new DBTheme(this);

        Button button = findViewById(R.id.registerButton);
        button.setOnClickListener(view -> {
            String emailInput = textInputEmail.getEditText().getText().toString().trim();
            String usernameInput = textInputUsername.getEditText().getText().toString().trim();
            String passwordInput = textInputPassword.getEditText().getText().toString().trim();

            if (validateEmail() && validateUsername() && validatePassword() && validateRePassword()) {
                Boolean checkUser = DB.checkUserMail(emailInput);
                if (!checkUser) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDateTime now = LocalDateTime.now();
                    String date = dtf.format(now);
                    Boolean insert = DB.insertData(emailInput, passwordInput, date, usernameInput);
                    if (insert) {
                        dbTheme.insertData("#FFFFFFFF");
                        Toast.makeText(getApplicationContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "User already exists, please sign in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}