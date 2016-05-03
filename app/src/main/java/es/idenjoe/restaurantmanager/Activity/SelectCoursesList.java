package es.idenjoe.restaurantmanager.Activity;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import es.idenjoe.restaurantmanager.Fragment.SelectCoursesListFragment;
import es.idenjoe.restaurantmanager.Model.MainCourse;
import es.idenjoe.restaurantmanager.R;

public class SelectCoursesList extends AppCompatActivity implements SelectCoursesListFragment.SelectCoursesListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_courses_list);

        FragmentManager fm = getFragmentManager();

        /*if (fm.findFragmentById(R.id.courses_list_fragment) == null) {

            fm.beginTransaction()
                    .add(R.id.courses_list_fragment, new SelectCoursesListFragment())
                    .commit();
        }*/
    }

    @Override
    public void onCourseSelected(MainCourse course, int index) {
        Log.v("SelectCourseFragment", course.getName());
    }
}
