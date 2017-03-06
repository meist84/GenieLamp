package com.example.anthony.genielamp;

public class User {

    private String Username, Image, Description;

    private User() { }

    public User(String Username, String Image, String Description) {
        this.Username = Username;
        this.Image = Image;
        this.Description = Description;
    }

    public String getImage() {
        return this.Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getUsername() {
        return this.Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String toString(){
        return this.Image +" "+ this.Username +""+ this.Description;
    }
}
