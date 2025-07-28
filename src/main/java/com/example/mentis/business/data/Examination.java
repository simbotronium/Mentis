package com.example.mentis.business.data;

public class Examination {

    private String layer;
    private int num;

    public Examination(String layer, int num) {
        this.layer = layer;
        this.num = num;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
