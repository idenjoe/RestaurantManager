package es.idenjoe.restaurantmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by idenjoe on 03/05/16.
 */
public class CourseView extends CardView {

    private final ProgressBar mImageDownloadProgress;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mPrice;
    private ImageView mImage;

    /**
     * Constructor for code
     * @param context application context to get app resources
     */
    public CourseView(Context context) {
        this(context, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
    }

    public CourseView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Create the layout from XML
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_main_course, this, true);

        mTitle = (TextView) findViewById(R.id.course_name);
        mDescription = (TextView) findViewById(R.id.course_customization);
        mPrice = (TextView) findViewById(R.id.course_price);
        mImage = (ImageView) findViewById(R.id.course_image);
        mImageDownloadProgress = (ProgressBar) findViewById(R.id.image_download_progress);
    }

    public void setImage(Bitmap bitmap) {
        if (bitmap != null) {
            mImage.setImageBitmap(bitmap);
            if (mImage.getVisibility() == View.GONE) {
                mImage.setVisibility(View.VISIBLE);
                // Animate the transition to visible for a better effect
                mImage.setAlpha(0.0f);
                mImage.animate()
                        .setDuration(1)
                        .alpha(1.0f);
            }
            mImageDownloadProgress.setVisibility(View.GONE);
        }
        else {
            mImage.setVisibility(View.GONE);
            mImageDownloadProgress.setVisibility(View.VISIBLE);
        }
    }

    public TextView getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public TextView getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription.setText(description);
    }

    public TextView getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice.setText(price);
    }
}
