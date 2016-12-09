package test.homework.nick.snp20.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nick on 22.11.16.
 */
public class MDBHelper extends SQLiteOpenHelper {


    public MDBHelper(Context context) {
        super(context, "snpMusicDataBase", null, 1);

        Log.i("mdatabase", "constructor called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Resource.Info.CREATE_TABLE);
        db.execSQL(Resource.Playlist.CREATE_TABLE);
        addPlaylist(db, Resource.Playlist.ALL_MUSIC_PLAYLIST_TITLE);
        addPlaylist(db, Resource.Playlist.DOWNLOADED_MUSIC_PLAYLIST_TITLE);
        Log.i("mdatabase", "onCreate called");

    }


    private long addPlaylist(SQLiteDatabase database, String title){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Resource.Playlist.TITLE, title);
        return database.insert(Resource.Playlist.TABLE_NAME, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
