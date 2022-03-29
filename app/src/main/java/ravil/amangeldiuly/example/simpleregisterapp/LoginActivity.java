package ravil.amangeldiuly.example.simpleregisterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout loginPageEmail;
    TextInputLayout loginPagePassword;
    Button loginButton;
    DBHelper DB;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent mainIntent = new Intent(getApplicationContext(), MainActivity2.class);

        mSharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        if (mSharedPreferences.contains("userMail")) {
            mainIntent.putExtra("userMail", mSharedPreferences.getString("userMail", "default"));
            startActivity(mainIntent);
            finish();
        }

        Intent intent = new Intent(this, RegisterActivity.class);
        TextView createAccTextView = findViewById(R.id.createAccTextView);
        createAccTextView.setOnClickListener(view -> {
            startActivity(intent);
            finish();
        });
        DB = new DBHelper(this);

        loginPageEmail = findViewById(R.id.loginPageEmail);
        loginPagePassword = findViewById(R.id.loginPagePassword);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            String mail = loginPageEmail.getEditText().getText().toString().trim();
            String password = loginPagePassword.getEditText().getText().toString().trim();

            if (mail.isEmpty()) {
                loginPageEmail.setError("Field can't be empty!");
            } else if (password.isEmpty()) {
                loginPagePassword.setError("Field can't be empty!");
            } else {
                if (DB.checkUserMail(mail)) {
                    if (DB.checkUserPassword(mail).equals(password)) {
                        Toast.makeText(getApplicationContext(), "Sign in successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString("userMail", mail);
                        editor.commit();
                        mainIntent.putExtra("userMail", mail);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        loginPagePassword.setError("Invalid password!");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid or unregistered mail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}