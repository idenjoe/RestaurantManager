package es.idenjoe.restaurantmanager.Model;

import java.util.LinkedList;

/**
 * Created by idenjoe on 17/04/16.
 */
public class Courses {

    private LinkedList<MainCourse> mCourses;

    public Courses(LinkedList<MainCourse> courses) {
        mCourses = courses;
    }

    public LinkedList<MainCourse> getCourses() {
        return mCourses;
    }

    public void setCourses(LinkedList<MainCourse> courses) {
        mCourses = courses;
    }
}
