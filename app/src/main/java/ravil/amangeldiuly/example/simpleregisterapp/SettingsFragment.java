package ravil.amangeldiuly.example.simpleregisterapp;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    Switch mSwitch;
    SharedPreferences mSharedPreferences;
    DBTheme mDBTheme;
    ImageButton changeBackgroundButton;
    View view;
    ImageButton colorOrange;
    ImageButton colorPink;
    ImageButton colorWhite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        mSharedPreferences = getActivity().getSharedPreferences("theme", Context.MODE_PRIVATE);
        mDBTheme = new DBTheme(getActivity().getApplicationContext());

        mSwitch = view.findViewById(R.id.dark_theme_switch_button);
        mSwitch.setChecked(mSharedPreferences.getBoolean("value", false));

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkTheme(b);
            }
        });

        ImageButton shareButton = view.findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });

        changeBackgroundButton = view.findViewById(R.id.change_background_button);
        changeBackgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground();
            }
        });

        return view;
    }

    public void changeBackground() {
        final Dialog dialog = new Dialog(SettingsFragment.this.getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);
        colorOrange = dialog.findViewById(R.id.orange_background);
        colorPink = dialog.findViewById(R.id.pink_background);
        colorWhite = dialog.findViewById(R.id.white_background);
        RelativeLayout mainActivity = getActivity().findViewById(R.id.mainBackground);

        colorOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDBTheme.updateTheme("#FFFF9900");
                mainActivity.setBackgroundColor(Color.parseColor(mDBTheme.checkTheme()));
                dialog.dismiss();
            }
        });

        colorPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDBTheme.updateTheme("#FFFADADB");
                mainActivity.setBackgroundColor(Color.parseColor(mDBTheme.checkTheme()));
                dialog.dismiss();
            }
        });

        colorWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDBTheme.updateTheme("#FFFFFFFF");
                mainActivity.setBackgroundColor(Color.parseColor(mDBTheme.checkTheme()));
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void checkTheme(boolean b) {
        if(b) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean("value", true);
            editor.apply();
            mSwitch.setChecked(true);
            mDBTheme.updateTheme("#FF000000");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean("value", false);
            editor.apply();
            mSwitch.setChecked(false);
            mDBTheme.updateTheme("#FFFFFFFF");
        }
    }

    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String body = "Sorry, currently no link for google play to install";
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(intent, "Share this app via"));
    }
}
