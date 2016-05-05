package es.idenjoe.restaurantmanager.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;

import es.idenjoe.restaurantmanager.Activity.SelectCoursesList;
import es.idenjoe.restaurantmanager.Adapter.CourseRecyclerAdapter;
import es.idenjoe.restaurantmanager.Model.Courses;
import es.idenjoe.restaurantmanager.Model.MainCourse;
import es.idenjoe.restaurantmanager.Model.Table;
import es.idenjoe.restaurantmanager.Model.TableCourses;
import es.idenjoe.restaurantmanager.Model.Tables;
import es.idenjoe.restaurantmanager.R;

/**
 * Created by idenjoe on 02/05/16.
 */
public class SelectCoursesListFragment extends Fragment {

    private OnAddCourseSelectedListener mOnCourseSelectedListener;
    private static final String TABLE_INDEX="TABLE_INDEX";
    private int mTableIndex;

    public static SelectCoursesListFragment newInstance(int position) {
        Bundle arguments = new Bundle();
        arguments.putInt(TABLE_INDEX, position);

        SelectCoursesListFragment fragment = new SelectCoursesListFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);

        if (getArguments() != null) {
            mTableIndex = getArguments().getInt(TABLE_INDEX);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_select_courses, container);

        LinkedList<MainCourse> courses = Courses.getAllCourses();
        Tables tables = Tables.getInstance();
        mTableIndex = getActivity().getIntent().getIntExtra(SelectCoursesList.TABLE_INDEX, 0);
        final Table table = tables.getTableAtPosition(mTableIndex);
        TableCourses tableCourses = table.getCourses();

        RecyclerView list = (RecyclerView) root.findViewById(R.id.courses_list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(new CourseRecyclerAdapter(Courses.getAllCourses(), new CourseRecyclerAdapter.OnCourseSelectedListener() {
            @Override
            public void onCourseSelected(MainCourse course) {
                Log.v("Select Course", course.getName());
                mOnCourseSelectedListener.onAddCourseSelected(course, table);
            }

            @Override
            public void onCourseLongSelected(MainCourse course) {
                Log.v("Select Course", course.getName());
            }
        }));

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachToActivity(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        attachToActivity(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnCourseSelectedListener = null;
    }

    private void attachToActivity(Context context) {
        // If parent activity doesn't implement it, crash
        if (!(context instanceof OnAddCourseSelectedListener)) {
            throw new ClassCastException("Activity holding CourseSelectorFragment should implement OnCourseOrderSelectedListener");
        }

        mOnCourseSelectedListener = (OnAddCourseSelectedListener) context;
    }

    // Interface to notify when a course order (with its details) has been selected
    public interface OnAddCourseSelectedListener {
        void onAddCourseSelected(MainCourse course, Table table);
    }
}
