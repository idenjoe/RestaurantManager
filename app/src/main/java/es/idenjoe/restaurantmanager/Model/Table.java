package es.idenjoe.restaurantmanager.Model;

/**
 * Created by idenjoe on 17/04/16.
 */
public class Table {
    private TableCourses mCourses;

    public Table() {
        mCourses = new TableCourses();
    }

    public TableCourses getCourses() {
        return mCourses;
    }

    public void setCourses(TableCourses courses) {
        mCourses = courses;
    }

    public int numberOfCourses(){
        return mCourses.numberOfCourses();
    }

    public double bill(){
        return mCourses.sumAllCourses();
    }
}
