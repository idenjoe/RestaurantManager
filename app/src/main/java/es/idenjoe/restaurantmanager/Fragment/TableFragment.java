package es.idenjoe.restaurantmanager.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
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
    private static final String TABLE_INDEX="TABLE_INDEX";
    private int mTableIndex;
    private ArrayAdapter<MainCourse> mAdapter;
    private TableCourses mTableCourses;
    Table mTable;

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

        Tables tables = Tables.getInstance();
        mTableIndex = getActivity().getIntent().getIntExtra(TableActivity.TABLE_INDEX, 0);
        mTable = tables.getTableAtPosition(mTableIndex);
        mTableCourses = mTable.getCourses();
        ListView list = (ListView) root.findViewById(android.R.id.list);

        mAdapter = new ArrayAdapter<MainCourse>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mTableCourses.getCourses());
        list.setAdapter(mAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // Show a confirm dialog to remove an order
                AlertDialog.Builder confirmDialog = new AlertDialog.Builder(getActivity());
                confirmDialog.setMessage(R.string.remove_course_from_table);
                confirmDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.notifyDataSetChanged();
                        mTable.removeCourse(mTable.getCourses().getCourseAtPosition(position));
                        Snackbar
                                .make(getView(), R.string.course_deleted, Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
                confirmDialog.setNegativeButton(android.R.string.cancel, null);
                confirmDialog.show();
                return true;
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.table_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Tables tables = Tables.getInstance();
        if (item.getItemId() == R.id.add_course) {

            TableActivity myActivity = (TableActivity) getActivity();
            myActivity.onAddCourseTapped(mTableIndex);

            return true;
        }
        else if (item.getItemId() == R.id.table_bill) {
            // Show a confirm dialog to remove an order
            AlertDialog.Builder confirmDialog = new AlertDialog.Builder(getActivity());
            confirmDialog.setMessage(getActivity().getString(R.string.bill_total_table) + tables.getTableAtPosition(mTableIndex).bill() + getActivity().getString(R.string.should_delete_table));
            confirmDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mTable.removeAllCourses();
                    mAdapter.notifyDataSetChanged();
                    Snackbar
                            .make(getView(), R.string.table_cleaned, Snackbar.LENGTH_SHORT)
                            .show();
                }
            });
            confirmDialog.setNegativeButton(android.R.string.cancel, null);
            confirmDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Test");
        }
    }
}
