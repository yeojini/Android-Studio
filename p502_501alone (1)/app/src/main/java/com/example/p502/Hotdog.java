package com.example.p502;

public class Hotdog {
    String hdname;
    int hdcost;
    int hdimg;
    double hddist;

    public Hotdog() {
    }

    public Hotdog(String hdname, int hdcost, int hdimg, double hddist) {
        this.hdname = hdname;
        this.hdcost = hdcost;
        this.hdimg = hdimg;
        this.hddist = hddist;
    }

    public String getHdname() {
        return hdname;
    }

    public void setHdname(String hdname) {
        this.hdname = hdname;
    }

    public int getHdcost() {
        return hdcost;
    }

    public void setHdcost(int hdcost) {
        this.hdcost = hdcost;
    }

    public int getHdimg() {
        return hdimg;
    }

    public void setHdimg(int hdimg) {
        this.hdimg = hdimg;
    }

    public double getHddist() {
        return hddist;
    }

    public void setHddist(double hddist) {
        this.hddist = hddist;
    }
}
