package es.idenjoe.restaurantmanager.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

import es.idenjoe.restaurantmanager.R;

/**
 * Created by idenjoe on 17/04/16.
 */
public class MainCourse implements Comparable<MainCourse>, Serializable {
    private String mName;
    private String mDescription;
    private double mPrice;
    private URL mImageURL;
    private String mSuggestions;

    public MainCourse(String name, String description, double price, URL image, String suggestions) {
        mName = name;
        mDescription = description;
        mPrice = price;
        mImageURL = image;
        mSuggestions = suggestions;
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
        return mImageURL;
    }

    public void setImage(URL image) {
        mImageURL = image;
    }

    public String getSuggestions() {
        return mSuggestions;
    }

    public void setSuggestions(String suggestions) {
        mSuggestions = suggestions;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(@NonNull MainCourse another) {
        return mName.compareToIgnoreCase(another.getName());
    }

    public Bitmap getBitmap(Context context) {
        return getBitmapFromURL(context, mImageURL.toString());
    }

    private Bitmap getBitmapFromURL(Context context, String url) {
        Log.v("MainCourse",url);
        File imageFile = new File(context.getCacheDir(), getName());

        // We check if file already exists
        if (imageFile.exists()) {
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }

        InputStream in = null;
        try {
            in = new java.net.URL(url).openStream();
            Bitmap bmp = BitmapFactory.decodeStream(in);
            if (bmp != null) {
                FileOutputStream fos = new FileOutputStream(imageFile);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.close();
                return bmp;
            }
            else {
                return null;
            }
        } catch (Exception e) {
            Log.e(context.getString(R.string.app_name), "Error downloading image", e);
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
