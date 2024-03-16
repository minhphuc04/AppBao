package com.example.appbao;

public class BaiBao {
    private int image1;
    private String name; private String type;
    private String time;

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BaiBao(int image1, String name, String type, String time) {
        this.image1 = image1;
        this.name = name;
        this.type = type;
        this.time = time;
    }
}
