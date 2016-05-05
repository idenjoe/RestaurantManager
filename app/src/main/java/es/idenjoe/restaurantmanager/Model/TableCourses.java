package es.idenjoe.restaurantmanager.Model;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Created by idenjoe on 17/04/16.
 */
public class TableCourses {
    private LinkedList<MainCourse> mCourses;

    public TableCourses() {
        mCourses = new LinkedList<MainCourse>();
    }

    public LinkedList<MainCourse> getCourses() {
        return mCourses;
    }

    public void setCourses(LinkedList<MainCourse> courses) {
        mCourses = courses;
    }

    public MainCourse getCourseAtPosition(int position){
        return mCourses.get(position);
    }

    public int numberOfCourses(){
        return mCourses.size();
    }

    private static OnAddCourseListener mOnAddCourseListener;

    public void setOnAddCourseListener(OnAddCourseListener object){
        mOnAddCourseListener = object;
    }

    public double sumAllCourses(){
        double total = 0;
        for (MainCourse course : mCourses)
        {
            total += course.getPrice();
        }

        return total;
    }

    public void addCourse(MainCourse course){
        mCourses.push(course);
        mOnAddCourseListener.onAddCourse(course);
    }

    // Interface to notify when a course order (with its details) has been selected
    public interface OnAddCourseListener {
        void onAddCourse(MainCourse course);
    }
}
