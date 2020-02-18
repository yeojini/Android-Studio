package com.example.p502;

import java.util.Comparator;

public class Item {
    String img;
    String name;
    String team;
    int capacity;
    int reviews;
    double latitude;
    double longitude;
    double dist;

    public Item(){}

    public Item(String img, String name, String team, int capacity, int reviews, double latitude, double longitude, double dist) {
        this.img = img;
        this.name = name;
        this.team = team;
        this.capacity = capacity;
        this.reviews = reviews;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dist = dist;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public static Comparator<Item> sortDistance = new Comparator<Item>() {

        public int compare(Item i1, Item i2) {

            double dist1 = i1.getDist();
            double dist2 = i2.getDist();

            /*For ascending order*/
            //return (int)(dist2-dist1);

            /*For descending order*/
            return (int)(dist1-dist2);
        }};

    public static Comparator<Item> sortReviews = new Comparator<Item>() {

        public int compare(Item i1, Item i2) {

            int review1 = i1.getReviews();
            int review2 = i2.getReviews();

            /*For ascending order*/
            return review2-review1;

            /*For descending order*/
            //rollno2-rollno1;
        }};

    public static Comparator<Item> sortName = new Comparator<Item>() {

        public int compare(Item i1, Item i2) {
            String Name1 = i1.getName().toUpperCase();
            String Name2 = i2.getName().toUpperCase();

            //ascending order
            return Name1.compareTo(Name2);

            //descending order
//            return Name2.compareTo(Name1);
        }};

    @Override
    public String toString() {
        return "Item{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", capacity=" + capacity +
                ", reviews=" + reviews +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", dist=" + dist +
                '}';
    }
}