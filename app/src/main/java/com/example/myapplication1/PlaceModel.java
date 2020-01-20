package com.example.myapplication1;

public class PlaceModel {
    String image;
    String nama,detail;
    String lokasi,direct;

    public PlaceModel() {
    }

    public PlaceModel(String image, String nama, String detail, String lokasi, String direct) {
        this.image = image;
        this.nama = nama;
        this.detail = detail;
        this.lokasi = lokasi;
        this.direct = direct;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }
}
