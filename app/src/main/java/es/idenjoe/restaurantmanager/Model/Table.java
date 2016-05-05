package es.idenjoe.restaurantmanager.Model;

/**
 * Created by idenjoe on 17/04/16.
 */
public class Table {
    private TableCourses mCourses;
    private int mId;

    public Table(int num) {
        mCourses = new TableCourses();
        mId = num;
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

    public String bill(){
        double bill = mCourses.sumAllCourses();
        return String.format("%.2f", bill);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public void addCourse(MainCourse course, String suggestions){
        MainCourse newCourse = new MainCourse(course.getName(),course.getDescription(),course.getPrice(),course.getImage(),suggestions);
        mCourses.addCourse(newCourse);
    }

    public void removeCourse(MainCourse course){
        mCourses.removeCourse(course);
    }

    public void removeAllCourses(){
        mCourses.removeAllCourses();
    }

    @Override
    public String toString() {
        return "Mesa " + getId();
    }
}
