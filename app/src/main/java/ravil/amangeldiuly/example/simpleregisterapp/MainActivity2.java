package ravil.amangeldiuly.example.simpleregisterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    DBTheme mDBTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        System.out.println("#################################################################");
        System.out.println("onCreate");
        System.out.println("#################################################################");

        RelativeLayout mainActivity = findViewById(R.id.mainBackground);
        mDBTheme = new DBTheme(this);
        mainActivity.setBackgroundColor(Color.parseColor(mDBTheme.checkTheme()));


        Intent intent = getIntent();
        String userMail = intent.getExtras().getString("userMail");
        Intent passIntent = new Intent(this, ProfileFragment.class);
        passIntent.putExtra("userMail", userMail);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CharactersFragment()).commit();
        }

        bottomNav.setOnItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId()) {
                        case R.id.nav_characters:
                            selectedFragment = new CharactersFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("#################################################################");
        System.out.println("onRestart");
        System.out.println("#################################################################");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("#################################################################");
        System.out.println("onResume");
        System.out.println("#################################################################");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("#################################################################");
        System.out.println("onPause");
        System.out.println("#################################################################");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("#################################################################");
        System.out.println("onStop");
        System.out.println("#################################################################");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("#################################################################");
        System.out.println("onDestroy");
        System.out.println("#################################################################");
    }
}