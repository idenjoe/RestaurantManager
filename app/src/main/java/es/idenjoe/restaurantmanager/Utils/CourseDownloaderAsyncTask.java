package es.idenjoe.restaurantmanager.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import es.idenjoe.restaurantmanager.Model.MainCourse;
import es.idenjoe.restaurantmanager.R;
import es.idenjoe.restaurantmanager.Model.Courses;

public class CourseDownloaderAsyncTask extends AsyncTask<Void, Void, LinkedList<MainCourse>> {
    private ProgressDialog mProgressDialog;
    private ViewGroup mContainer;
    private WeakReference<Activity> mActivity;


    public CourseDownloaderAsyncTask(ViewGroup container, Activity activity) {
        super();
        mContainer = container;
        mActivity = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        mContainer.setAlpha(0.0f);

        mProgressDialog = new ProgressDialog(mActivity.get());
        mProgressDialog.setTitle(mActivity.get().getString(R.string.downloading));
        mProgressDialog.show();
    }

    @Override
    protected LinkedList<MainCourse> doInBackground(Void... params) {
        // Simulate a longer loading
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Courses.downloadCourses();
    }

    @Override
    protected void onPostExecute(LinkedList<MainCourse> courses) {
        try {
            mProgressDialog.dismiss();
        } catch (IllegalArgumentException ex) {
            // Android bug can sometimes cause this
        }
        if (courses == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(mActivity.get());
            alert.setMessage(R.string.error_downloading_courses);
            alert.setCancelable(false);
            alert.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mActivity.get().finish();
                }
            });
            alert.show();
        }
        else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mContainer.animate()
                    .setDuration(mContainer.getContext().getResources().getInteger(R.integer.default_animation_duration))
                    .alpha(1.0f);
        }
    }
}
