package com.triginandri.chatika.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class User implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("nim")
    private String nim;
    @SerializedName("prodi")
    private String prodi;
    @SerializedName("univ")
    private String univ;
    @SerializedName("email")
    private String email;
    @SerializedName("key")
    private String key;

    public User(int id, String name, String nim, String prodi, String univ, String email, String key) {
        this.id = id;
        this.name = name;
        this.nim = nim;
        this.prodi = prodi;
        this.univ = univ;
        this.email = email;
        this.key = key;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getUniv() {
        return univ;
    }

    public void setUniv(String univ) {
        this.univ = univ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



}
