package com.example.markerlessproject.models;

public class ContentRankingModel {
    String namaUser, dateRank, point, photo;

    public void setDateRank(String dateRank) {
        this.dateRank = dateRank;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getDateRank() {
        return dateRank;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPoint() {
        return point;
    }
}
