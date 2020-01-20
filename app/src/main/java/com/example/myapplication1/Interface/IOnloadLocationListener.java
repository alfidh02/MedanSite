package com.example.myapplication1.Interface;

import com.example.myapplication1.MyLatLng;

import java.util.List;

public interface IOnloadLocationListener {
    void onLoadLocationSuccess(List<MyLatLng> latLngs);
    void onLoadLocationFailed(String message);
}
