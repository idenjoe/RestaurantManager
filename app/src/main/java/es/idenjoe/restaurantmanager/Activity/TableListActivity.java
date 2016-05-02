package es.idenjoe.restaurantmanager.Activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import es.idenjoe.restaurantmanager.Fragment.TableListFragment;
import es.idenjoe.restaurantmanager.Model.Courses;
import es.idenjoe.restaurantmanager.Model.Table;
import es.idenjoe.restaurantmanager.R;
import es.idenjoe.restaurantmanager.Utils.CourseDownloaderAsyncTask;

/**
 * Created by idenjoe on 17/04/16.
 */
public class TableListActivity extends AppCompatActivity implements TableListFragment.TableListListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.tables);
        }

        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(R.id.table_list_fragment) == null){

            fm.beginTransaction()
                    .add(R.id.table_list_fragment, new TableListFragment())
                    .commit();
        }

        downloadCoursesIfNeeded();
    }

    protected  void downloadCoursesIfNeeded() {
        // Check if we need to download courses
        if (Courses.getAllCourses().size() == 0) {
            CourseDownloaderAsyncTask courseDownloader = new CourseDownloaderAsyncTask((ViewGroup) findViewById(R.id.coordinator), this);
            courseDownloader.execute();
        }
    }

    @Override
    public void onTableSelected(Table table, int index) {
        Intent intent = new Intent(this, TableActivity.class);
        intent.putExtra(TableActivity.TABLE_INDEX,index);
        startActivity(intent);
    }
}
