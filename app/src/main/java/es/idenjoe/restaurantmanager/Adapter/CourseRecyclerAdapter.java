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
    public CourseRecyclerAdapter(LinkedList<MainCourse> courses){
        mMainCourses = courses;
    }

    @Override
    public MainCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_course,parent,false);
        return new MainCourseViewHolder(view);
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

        public MainCourseViewHolder(View itemView) {
            super(itemView);

            mCourseView = (CourseView) itemView.findViewById(R.id.course);

        }

        public void bindMainCourse(final MainCourse course){
            mCourseData = course;
            mCourseView.setTitle(course.getName());
            mCourseView.setDescription(course.getDescription());
            mCourseView.setPrice(Double.toString(course.getPrice()));

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
}
