package es.idenjoe.restaurantmanager.Model;

import android.media.Image;

/**
 * Created by idenjoe on 17/04/16.
 */
public class MainCourse {
    private String mName;
    private String mDescription;
    private double mPrice;
    private Image mImage;

    public MainCourse(String name, String description, double price, Image image) {
        mName = name;
        mDescription = description;
        mPrice = price;
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }
}
