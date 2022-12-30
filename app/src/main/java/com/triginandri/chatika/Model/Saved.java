package com.triginandri.chatika.Model;

public class Saved {

    String jenis_kelamin;
    String pembukaan;
    String tujuan;
    String pertanyaan;
    String penutupan;

    public Saved(String jenis_kelamin, String pembukaan, String tujuan, String pertanyaan, String penutupan) {
        this.jenis_kelamin = jenis_kelamin;
        this.pembukaan = pembukaan;
        this.tujuan = tujuan;
        this.pertanyaan = pertanyaan;
        this.penutupan = penutupan;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getPembukaan() {
        return pembukaan;
    }

    public void setPembukaan(String pembukaan) {
        this.pembukaan = pembukaan;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getPenutupan() {
        return penutupan;
    }

    public void setPenutupan(String penutupan) {
        this.penutupan = penutupan;
    }



}
