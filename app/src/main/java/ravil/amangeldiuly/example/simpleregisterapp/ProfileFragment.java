package ravil.amangeldiuly.example.simpleregisterapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    DBHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        db = new DBHelper(getContext());
        TextView email = view.findViewById(R.id.yourEmail);
        TextView username = view.findViewById(R.id.yourUsername);
        TextView dateOfReg = view.findViewById(R.id.yourDateOfReg);
        Intent intent = ((Activity) getContext()).getIntent();
        String userMail = intent.getExtras().getString("userMail");

        email.setText(userMail);
        dateOfReg.setText(db.getDateOfRegistration(userMail));
        username.setText(db.getUsername(userMail));

        return view;
    }
}
