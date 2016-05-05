package es.idenjoe.restaurantmanager.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.LinkedList;

import es.idenjoe.restaurantmanager.CourseView;
import es.idenjoe.restaurantmanager.Model.MainCourse;

import es.idenjoe.restaurantmanager.R;

/**
 * Created by idenjoe on 02/05/16.
 */
public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.MainCourseViewHolder> {
    private LinkedList<MainCourse> mMainCourses;
    private OnCourseSelectedListener mCourseSelectedListener;

    public CourseRecyclerAdapter(LinkedList<MainCourse> courses, OnCourseSelectedListener courseSelectedListener){
        mMainCourses = courses;
        mCourseSelectedListener = courseSelectedListener;
    }

    @Override
    public MainCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_course,parent,false);
        return new MainCourseViewHolder(view, mCourseSelectedListener);
    }

    @Override
    public void onBindViewHolder(MainCourseViewHolder holder, int position) {
        holder.bindMainCourse(mMainCourses.get(position));
    }

    @Override
    public int getItemCount() {
        return mMainCourses.size();
    }

    public  class MainCourseViewHolder extends RecyclerView.ViewHolder{
        private CourseView mCourseView;
        private MainCourse mCourseData;
        private AsyncTask<Void, Void, Bitmap> mDownloadImageAsyncTask;
        private WeakReference<OnCourseSelectedListener> mOnCourseSelectedListener;

        public MainCourseViewHolder(View itemView, OnCourseSelectedListener courseSelectedListener) {
            super(itemView);

            mCourseView = (CourseView) itemView.findViewById(R.id.course);
            mOnCourseSelectedListener = new WeakReference<OnCourseSelectedListener>(courseSelectedListener);
        }

        public void bindMainCourse(final MainCourse course){
            mCourseData = course;
            mCourseView.setTitle(course.getName());
            mCourseView.setDescription(course.getDescription());
            Double price = course.getPrice();
            String priceString = String.format("%.2f",price);
            String priceFormated = "Precio: " + priceString  + " €";
            mCourseView.setPrice(priceFormated);
            mCourseView.setSuggestion(course.getSuggestions());

            if (mOnCourseSelectedListener.get() != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCourseData != null) {
                            mOnCourseSelectedListener.get().onCourseSelected(mCourseData);
                        }
                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mCourseData != null) {
                            mOnCourseSelectedListener.get().onCourseLongSelected(mCourseData);
                        }
                        return true;
                    }
                });
            }

            // Start download image if needed
            // Cancel previous task if exists
            if (mDownloadImageAsyncTask != null) {
                mDownloadImageAsyncTask.cancel(true);
            }
            mDownloadImageAsyncTask = new AsyncTask<Void, Void, Bitmap>() {
                Context mContext;

                @Override
                protected void onPreExecute() {
                    mContext = mCourseView.getContext();
                    mCourseView.setImage(null);
                }

                @Override
                protected Bitmap doInBackground(Void... params) {
                    return mCourseData.getBitmap(mContext);
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (!isCancelled()) { // Check if was cancelled, if so don't set the image
                        mCourseView.setImage(bitmap);
                    }
                }
            };

            mDownloadImageAsyncTask.execute();
        }
    }

    // Interface to notify when a course has been selected
    public interface OnCourseSelectedListener {
        void onCourseSelected(MainCourse course);
        void onCourseLongSelected(MainCourse course);
    }
}
