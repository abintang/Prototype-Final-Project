package com.example.markerlessproject.models;

public class ContentMateriModel {
    int id_materi;
    String nama_materi, mini_desc_materi, iconMateri;

    public void setIconMateri(String iconMateri) {
        this.iconMateri = iconMateri;
    }

    public void setId_materi(int id_materi) {
        this.id_materi = id_materi;
    }

    public void setMini_desc_materi(String mini_desc_materi) {
        this.mini_desc_materi = mini_desc_materi;
    }

    public void setNama_materi(String nama_materi) {
        this.nama_materi = nama_materi;
    }

    public int getId_materi() {
        return id_materi;
    }

    public String getIconMateri() {
        return iconMateri;
    }

    public String getMini_desc_materi() {
        return mini_desc_materi;
    }

    public String getNama_materi() {
        return nama_materi;
    }
}
