package ravil.amangeldiuly.example.simpleregisterapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CharactersPlaceHolderApi {

    @GET("characters")
    Call<List<Character>> getCharacters();

    @GET("characters/{id}")
    Call<List<Character>> getCharacterDetail(@Path("id") int char_id);
}
