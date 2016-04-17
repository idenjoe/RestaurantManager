package es.idenjoe.restaurantmanager.Model;

import java.util.LinkedList;

/**
 * Created by idenjoe on 17/04/16.
 */
public class Tables {
    private LinkedList<Table> mTables;

    public Tables() {
        mTables = new LinkedList<Table>();

        for (int i = 0; i < 10; i++) {
            mTables.add(new Table());
        }
    }
}
