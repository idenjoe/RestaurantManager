package es.idenjoe.restaurantmanager.Model;

import android.media.Image;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by idenjoe on 17/04/16.
 */
public class MainCourse implements Comparable<MainCourse>, Serializable {
    private String mName;
    private String mDescription;
    private double mPrice;
    private URL mImage;

    public MainCourse(String name, String description, double price, URL image) {
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

    public URL getImage() {
        return mImage;
    }

    public void setImage(URL image) {
        mImage = image;
    }

    @Override
    public int compareTo(@NonNull MainCourse another) {
        return mName.compareToIgnoreCase(another.getName());
    }
}
