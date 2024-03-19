package com.example.appbao;

import java.io.Serializable;

public class News implements Serializable {
    private String ma;
    private String title;
    private String content;
    private String CategoryID;



    private byte[]hinh;
    private String CreatedDate;
    private String authorID;

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

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public News(String ma, String title, String content, String categoryID, byte[] hinh, String createdDate, String authorID) {
        this.ma = ma;
        this.title = title;
        this.content = content;
        CategoryID = categoryID;
        this.hinh = hinh;
        CreatedDate = createdDate;
        this.authorID = authorID;
    }
    public News(String ma, String title, String categoryID, byte[] hinh, String createdDate) {
        this.ma = ma;
        this.title = title;
        CategoryID = categoryID;
        this.hinh = hinh;
        CreatedDate = createdDate;

    }

    public News() {
    }

    public String toString()
    {
        return  "\n"+ "ID: "+ma+"\t"+"\n\n"+"Title: "+title+"\n\n"+"Content: "+content+"\n\n"+"CateID: "+CategoryID+"\n\n"+"Image: "+hinh+"\n\n"+"PublishedDate: "+CreatedDate+"\n\n"+"AuthorID: "+authorID+"\n\n";
    }
}
