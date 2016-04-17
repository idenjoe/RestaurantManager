package es.idenjoe.restaurantmanager.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.idenjoe.restaurantmanager.Model.Table;
import es.idenjoe.restaurantmanager.Model.Tables;
import es.idenjoe.restaurantmanager.R;

/**
 * Created by idenjoe on 17/04/16.
 */
public class TableListFragment extends Fragment {

    private TableListListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_table_list,container);

        Tables tables = Tables.getInstance(getActivity());
        ListView list = (ListView) root.findViewById(android.R.id.list);

        final ArrayAdapter<Table> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1, // Recuerda que esto es el layout de cada fila
                tables.getTables());
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
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (TableListListener) getActivity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (TableListListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    public interface TableListListener {
        void onTableSelected(Table table, int index);
    }
}
