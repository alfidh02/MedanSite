package com.example.myapplication1;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements OnMapReadyCallback {
    TextView name,detail;
    ImageView image;
    Button mDirect;
    double latitude,longitude;
    String placeName,placeDetail,placeImage;
    String googleMap = "com.google.android.apps.maps";// identitas package aplikasi google maps android
//    lokasiTempat = direction
    String lokasiTempat;
//    Make it to array because of lat and long
    String[] placeLoc;
    private Intent mapIntent;
    private Uri gmmIntentUri;
//    Initiate context
    private Context mContext;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        name = view.findViewById(R.id.nameRumah);
        detail = view.findViewById(R.id.detailRumah);
        image = view.findViewById(R.id.imageRumah);
        mDirect = view.findViewById(R.id.btn_direct_me);
//        And this is how you retrieve it
        placeName = getArguments().getString("name");
        placeDetail = getArguments().getString("detail");
        placeImage = getArguments().getString("image");
        placeLoc = getArguments().getString("location").split(",");
        latitude = Double.parseDouble(placeLoc[0]);
        longitude = Double.parseDouble(placeLoc[1]);
//        Give direction
        lokasiTempat = getArguments().getString("direction");
//        Set the text and image
        name.setText(placeName);
        detail.setText(placeDetail);
        Picasso.get().load(placeImage).into(image);

//      Set the map. Need to getChildFragmentManager, otherwise it won't work
        SupportMapFragment mapFragment =(SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapRumah);
        mapFragment.getMapAsync(this);

        mDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Buat Uri dari intent string. Gunakan hasilnya untuk membuat Intent.
                gmmIntentUri = Uri.parse("google.navigation:q="+lokasiTempat);
                // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
                mapIntent =new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
                mapIntent.setPackage(googleMap);

                if(mapIntent.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivity(mapIntent);
                }
                else {
                    Toast.makeText(mContext, "Google Maps Belum Terinstal. Install Terlebih dahulu.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

//    Set the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng tempatMedan = new LatLng(latitude,longitude);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(tempatMedan));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(tempatMedan,15));
        googleMap.addMarker(new MarkerOptions().position(tempatMedan).title(placeName));
    }
}
