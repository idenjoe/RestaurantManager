package es.idenjoe.restaurantmanager.Activity;

import android.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import es.idenjoe.restaurantmanager.Fragment.SelectCoursesListFragment;
import es.idenjoe.restaurantmanager.Model.MainCourse;
import es.idenjoe.restaurantmanager.Model.Table;
import es.idenjoe.restaurantmanager.Model.TableCourses;
import es.idenjoe.restaurantmanager.Model.Tables;
import es.idenjoe.restaurantmanager.R;

public class SelectCoursesList extends AppCompatActivity implements SelectCoursesListFragment.OnAddCourseSelectedListener {
    private TableCourses mTableCourses;
    public static final String TABLE_INDEX="TABLE_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_courses_list);
    }

    @Override
    public void onAddCourseSelected(MainCourse course, Table table) {
        Log.v("SelectCourseFragment", course.getName());
        table.addCourse(course);
        finish();
    }
}
