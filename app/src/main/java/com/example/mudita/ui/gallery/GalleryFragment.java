package com.example.mudita.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mudita.Mainscreen1;
import com.example.mudita.R;
import com.example.mudita.SendNotif;
import com.example.mudita.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private Button Update;
    private EditText user_name;
    private String string;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        Update=root.findViewById(R.id.updatebutton);
        user_name=root.findViewById(R.id.friendusernametxt);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string=user_name.getText().toString();


                if(string!=null&&!string.isEmpty())
                { SharedPreferences pref=getActivity().getSharedPreferences(getString(R.string.usernameshpref), Context.MODE_PRIVATE);
                  SharedPreferences.Editor editor=pref.edit();
                  editor.putString("Friendname",string.trim());
                  editor.apply();


                    Intent intent = getActivity().getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    getActivity().overridePendingTransition(0, 0);
                    getActivity().finish();

                    getActivity().overridePendingTransition(0, 0);
                    SendNotif.Sendnotificationfbase(getContext(),5001);
                    startActivity(intent);





                }
                else
                {
                    Toast.makeText(getContext(),"UserName Cannot be Empty ",Toast.LENGTH_SHORT).show();
                }

            }
        });


      /*  galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               *//* textView.setText("Add a Friend");*//*
            }
        });*/
        return root;
    }



}
