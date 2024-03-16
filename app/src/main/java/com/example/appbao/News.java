package com.example.appbao;

import java.io.Serializable;

public class News implements Serializable {
    private String ma;
    private String title;
    private String content;
    private String CategoryID;



    private String image;
    private String CreatedDate;
    private String authorID;

    public News(String ma, String title, String content, String image, String CreatedDate) {
        this.ma = ma;
        this.title = title;
        this.content = content;
        this.image = image;
        this.CreatedDate = CreatedDate;


    }

    public News(String title, String categoryID, String image, String createdDate) {
        this.title = title;
        CategoryID = categoryID;
        this.image = image;
        CreatedDate = createdDate;
    }
    public News(String title,  String createdDate) {
        this.title = title;
        CreatedDate = createdDate;
    }


    public News(String ma, String title, String content, String categoryID, String image, String CreatedDate, String authorID) {
        this.ma = ma;
        this.title = title;
        this.content = content;
        CategoryID = categoryID;
        this.image = image;
        this.CreatedDate = CreatedDate;
        this.authorID = authorID;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public News() {
    }
    public String toString()
    {
        return  "\n"+ "ID: "+ma+"\t"+"\n\n"+"Title: "+title+"\n\n"+"Content: "+content+"\n\n"+"CateID: "+CategoryID+"\n\n"+"Image: "+image+"\n\n"+"PublishedDate: "+CreatedDate+"\n\n"+"AuthorID: "+authorID+"\n\n";
    }
}
