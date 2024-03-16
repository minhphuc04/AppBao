package com.example.appbao;

import java.io.Serializable;

public class Categories implements Serializable {
    private String ma;
    private String ten;

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Categories(String ma, String ten) {
        this.ma = ma;
        this.ten = ten;
    }

    public Categories() {
    }
    public String toString()
    {
        return  ma+"\t"+ten+"\t";
    }
}
