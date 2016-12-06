package test.homework.nick.snp20.database.dao.core;

import android.database.Cursor;

import java.util.List;

/**
 * Created by Nick on 06.12.16.
 */
public interface Dao<T> {

    long save(T t);
    List<T> getAll();
    List<T> parseCursor(Cursor cursor);
}
