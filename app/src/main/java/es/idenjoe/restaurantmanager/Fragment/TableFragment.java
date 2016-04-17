package es.idenjoe.restaurantmanager.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.idenjoe.restaurantmanager.Activity.TableActivity;
import es.idenjoe.restaurantmanager.Model.MainCourse;
import es.idenjoe.restaurantmanager.Model.Table;
import es.idenjoe.restaurantmanager.Model.TableCourses;
import es.idenjoe.restaurantmanager.Model.Tables;
import es.idenjoe.restaurantmanager.R;

/**
 * Created by idenjoe on 17/04/16.
 */
public class TableFragment extends Fragment{
    private TableListener mListener;
    private static final String TABLE_INDEX="TABLE_INDEX";
    private int mTableIndex;

    public static TableFragment newInstance(int position) {
        Bundle arguments = new Bundle();
        arguments.putInt(TABLE_INDEX, position);

        TableFragment fragment = new TableFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null){
            mTableIndex = getArguments().getInt(TABLE_INDEX);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_table_detail, container);

        Tables tables = Tables.getInstance(getActivity());
        Table table = tables.getTableAtPosition(mTableIndex);
        TableCourses tableCourses = table.getCourses();
        ListView list = (ListView) root.findViewById(android.R.id.list);

        final ArrayAdapter<MainCourse> adapter = new ArrayAdapter<MainCourse>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                tableCourses.getCourses());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onTableSelected(adapter.getItem(position), position);
                }
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.table_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Tables tables = Tables.getInstance(getActivity());
        if (item.getItemId() == R.id.add_course) {
            if (mListener != null) {
                TableActivity myActivity = (TableActivity) getActivity();
                myActivity.onAddCourseTapped(tables.getTableAtPosition(mTableIndex));
            }

            return true;
        }
        else if (item.getItemId() == R.id.table_bill) {
            Snackbar snackbar = Snackbar.make(getView(), "El total de la mesa es: " + tables.getTableAtPosition(mTableIndex).bill(), Snackbar.LENGTH_LONG);

            snackbar.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (TableListener) getActivity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (TableListener) activity;

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Test");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    public interface TableListener {
        void onTableSelected(MainCourse course, int index);
    }
}
