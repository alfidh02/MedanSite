package com.example.myapplication1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private View view;
//    Initiate appcompatactivity if needed
    private AppCompatActivity appCompatActivity;
//    Initiate context
    private Context mContext;
    private TextView mEmail,mNama,mKredit;
    private ImageView iProfile;
    private Button btnLogout,mNear;

    public UserFragment() {
        // Required empty public constructor
    }

//    To get context
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mEmail = view.findViewById(R.id.email);
        mNama = view.findViewById(R.id.nama);
        mKredit = view.findViewById(R.id.kredit);
        iProfile = view.findViewById(R.id.imageProfile);
        btnLogout = view.findViewById(R.id.btn_sign_out);
        mNear = view.findViewById(R.id.nearMe);

        appCompatActivity = ((AppCompatActivity)getActivity());

        mEmail.setText(user.getEmail());
        mNama.setText(user.getDisplayName());

        if (user.getPhotoUrl()!=null) {
            String photo = String.valueOf(user.getPhotoUrl());
            Picasso.get().load(photo).into(iProfile);
            mKredit.setVisibility(View.INVISIBLE);
        } else {
          mKredit.setVisibility(View.VISIBLE);
        }


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(mContext)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(mContext,MainActivity.class));
//                                finish activity
                                appCompatActivity.finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MapsActivity.class));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

}
