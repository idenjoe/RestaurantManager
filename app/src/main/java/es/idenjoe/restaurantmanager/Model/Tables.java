package es.idenjoe.restaurantmanager.Model;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * Created by idenjoe on 17/04/16.
 */
public class Tables {
    private LinkedList<Table> mTables;
    private static Tables sTables;
    private static final int NUM_TABLES = 20;

    public static Tables getInstance(Context context) {
        if ( sTables == null) {
                sTables = new Tables();
        }

        return sTables;
    }

    private Tables() {
        mTables = new LinkedList<Table>();

        for (int i = 0; i < NUM_TABLES; i++) {
            mTables.add(new Table(i));
        }
    }

    public LinkedList<Table> getTables() {
        return mTables;
    }

    public void setTables(LinkedList<Table> tables) {
        mTables = tables;
    }

    public Table getTableAtPosition(int position){
        return mTables.get(position);
    }
}
