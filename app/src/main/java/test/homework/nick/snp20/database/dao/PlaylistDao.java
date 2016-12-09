package test.homework.nick.snp20.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import test.homework.nick.snp20.database.Resource;
import test.homework.nick.snp20.database.dao.core.Dao;
import test.homework.nick.snp20.model.playlist_model.Playlist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 06.12.16.
 */
public class PlaylistDao implements Dao<Playlist>{

    private SQLiteDatabase database;

    public PlaylistDao(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public long save(Playlist playlist) {
        return database.insert(Resource.Playlist.TABLE_NAME, null, parsePlaylist(playlist));
    }

    @Override
    public List<Playlist> getAll() {
        Cursor cursor = database.query(Resource.Playlist.TABLE_NAME, null, null, null, null, null, null);
        return parseCursor(cursor);
    }



    @Override
    public List<Playlist> parseCursor(Cursor cursor) {
        ArrayList<Playlist> list = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                list.add(new Playlist(
                        cursor.getString(cursor.getColumnIndex(Resource.Playlist.TITLE)),
                        cursor.getString(cursor.getColumnIndex(Resource.Playlist.ARTWORK_URL))));
            }while (cursor.moveToNext());
        }

        return list;
    }

    private ContentValues parsePlaylist(Playlist playlist){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Resource.Playlist.TITLE, playlist.getTitle());
        contentValues.put(Resource.Playlist.ARTWORK_URL, playlist.getArtwork());
        return contentValues;
    }
}
