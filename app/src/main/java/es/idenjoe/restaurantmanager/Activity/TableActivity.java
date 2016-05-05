package es.idenjoe.restaurantmanager.Activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v7.widget.Toolbar;

import es.idenjoe.restaurantmanager.Fragment.TableFragment;
import es.idenjoe.restaurantmanager.Model.MainCourse;
import es.idenjoe.restaurantmanager.Model.Table;
import es.idenjoe.restaurantmanager.Model.Tables;
import es.idenjoe.restaurantmanager.R;

/**
 * Created by idenjoe on 17/04/16.
 */
public class TableActivity extends AppCompatActivity implements TableFragment.TableListener {
    public static final String TABLE_INDEX = "TABLE_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Bundle b = getIntent().getExtras();
            if(b != null) {
                int value = b.getInt(TABLE_INDEX);
                actionBar.setTitle(Tables.getInstance().getTableAtPosition(value).toString());
            }
        }

        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(R.id.table_detail_fragment) == null){

            int indexTable = getIntent().getIntExtra(TABLE_INDEX,0);

            fm.beginTransaction()
                    .add(R.id.table_detail_fragment, TableFragment.newInstance(indexTable))
                    .commit();
        }
    }

    @Override
    public void onTableSelected(MainCourse course, int index) {
        Log.v("TableActivity", "Plato seleccionado");
    }

    public void onAddCourseTapped(int tableIndex){
        Intent intent = new Intent(this, SelectCoursesList.class);
        intent.putExtra(TableActivity.TABLE_INDEX,tableIndex);
        startActivity(intent);
    }
}
