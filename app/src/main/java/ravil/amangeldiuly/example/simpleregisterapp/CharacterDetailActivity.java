package ravil.amangeldiuly.example.simpleregisterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharacterDetailActivity extends AppCompatActivity {

    Call<List<Character>> call;
    int id;
    DBTheme mDBTheme;

    @Override
    public void onDestroy() {
        super.onDestroy();
        call.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        ConstraintLayout characterDetailActivity = findViewById(R.id.character_detail_activity);
        mDBTheme = new DBTheme(this);
        characterDetailActivity.setBackgroundColor(Color.parseColor(mDBTheme.checkTheme()));

        ImageButton backButton = findViewById(R.id.imageButton);
        backButton.setOnClickListener(view -> {
            finish();
        });

        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");

        TextView characterNameTextView = findViewById(R.id.characterNameTextView);
        TextView characterBirthdayTextView = findViewById(R.id.characterBirthdayTextView);
        TextView characterOccupationTextView = findViewById(R.id.characterOccupationTextView);
        TextView characterStatusTextView = findViewById(R.id.characterStatusTextView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.breakingbadapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CharactersPlaceHolderApi charactersPlaceHolderApi = retrofit.create(CharactersPlaceHolderApi.class);

        call = charactersPlaceHolderApi.getCharacterDetail(id);

        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }

                List<Character> character = response.body();
                setImage(character.get(0).getImg());
                String occupation = character.get(0).getOccupation().toString();
                characterNameTextView.append(character.get(0).getName());
                characterBirthdayTextView.append(character.get(0).getBirthday());
                characterOccupationTextView.append(occupation.substring(1, occupation.length() - 1));
                characterStatusTextView.append(character.get(0).getStatus());
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    void setImage(String img) {
        Glide.with(this)
                .load(img)
                .into((ImageView) findViewById(R.id.characterImageView));
    }
}