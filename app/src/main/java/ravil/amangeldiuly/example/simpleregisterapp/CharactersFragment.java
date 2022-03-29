package ravil.amangeldiuly.example.simpleregisterapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharactersFragment extends Fragment {
    List<Character> characterList;
    RecyclerView recyclerView;
    Call<List<Character>> call;
    boolean connection;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (connection) {
            call.cancel();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chracters, container, false);

        recyclerView = view.findViewById(R.id.characters_list_view);
        characterList = new ArrayList<>();
        TextView amountOfCharacters = view.findViewById(R.id.amount_of_characters);
        connection = false;

        if (isConnected(getActivity())) {
            connection = true;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.breakingbadapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CharactersPlaceHolderApi charactersPlaceHolderApi = retrofit.create(CharactersPlaceHolderApi.class);

            call = charactersPlaceHolderApi.getCharacters();

            call.enqueue(new Callback<List<Character>>() {
                @Override
                public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                    if (!response.isSuccessful()) {
                        System.out.println("Code: " + response.code());
                        return;
                    }

                    List<Character> characters = response.body();
                    amountOfCharacters.setText("List of your characters (" + characters.size() + ")");

                    for (Character character: characters) {
                        characterList.add(character);
                    }

                    PutDataIntoRecyclerView(characterList);
                }

                @Override
                public void onFailure(Call<List<Character>> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        } else {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.alert_dialog);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            TextView cancelText = dialog.findViewById(R.id.cancel_label);
            TextView connectText = dialog.findViewById(R.id.connect_label);

            cancelText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
            });

            connectText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            });

            dialog.show();
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getLayoutInflater().getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getLayoutInflater().getContext(), CharacterDetailActivity.class);
                        intent.putExtra("id", characterList.get(position).getChar_id());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );

        return view;
    }

    private void PutDataIntoRecyclerView(List<Character> characterList) {
        Adaptery adaptery = new Adaptery(getLayoutInflater().getContext(), characterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getLayoutInflater().getContext()));
        recyclerView.setAdapter(adaptery);
    }

    private boolean isConnected(FragmentActivity activity2) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity2.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }
}
