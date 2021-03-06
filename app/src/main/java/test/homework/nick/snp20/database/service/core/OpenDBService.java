package test.homework.nick.snp20.database.service.core;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import test.homework.nick.snp20.database.MDBHelper;

/**
 * Created by Nick on 06.12.16.
 */
public class OpenDBService {

    private MDBHelper helper;
    private SQLiteDatabase database;

    public OpenDBService() {
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    protected boolean isOpen() {
        return helper != null && database != null && database.isOpen();
    }

    protected void open(Context context) {
        if (database == null || !database.isOpen()) {
            helper = new MDBHelper(context);
            database = helper.getWritableDatabase();
        }
    }

    protected void close() {
        if (helper != null) {
            helper.close();
        }
    }
}
