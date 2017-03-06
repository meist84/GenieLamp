package com.example.anthony.genielamp;

public class Post {
    private String Image, Title, Description, Userid;

    public Post() { }

    public Post(String Image, String Title, String Description, String Userid) {
        this.Image = Image;
        this.Title = Title;
        this.Description = Description;
        this.Userid = Userid;
    }

    public String getImage() {
        return this.Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getUserid() {
        return this.Userid;
    }

    public void setUserid(String userid) {
        this.Userid = userid;
    }

    public String toString(){
        return this.Image +""+ this.Title +""+ this.Description +""+ this.Userid;
    }

}
