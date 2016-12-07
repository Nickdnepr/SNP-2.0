package test.homework.nick.snp20.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import test.homework.nick.snp20.database.Resource;
import test.homework.nick.snp20.database.dao.core.Dao;
import test.homework.nick.snp20.model.music_info_model.Info;
import test.homework.nick.snp20.model.music_info_model.User;
import test.homework.nick.snp20.model.playlist_model.Playlist;
import test.homework.nick.snp20.utils.StringGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 06.12.16.
 */
public class InfoDao implements Dao<Info> {
    private SQLiteDatabase database;

    public InfoDao(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public long save(Info info) {
        return database.insert(Resource.Info.TABLE_NAME, null, parseInfo(info));
    }

    @Override
    public List<Info> getAll() {
        Cursor cursor = database.query(Resource.Info.TABLE_NAME, null, null, null, null, null, null);
        return parseCursor(cursor);
    }

    @Override
    public List<Info> parseCursor(Cursor cursor) {
        ArrayList<Info> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Info info = new Info(
                        cursor.getString(cursor.getColumnIndex(Resource.Info.TITLE)),
                        cursor.getInt(cursor.getColumnIndex(Resource.Info.DURATION)),
                        new User(cursor.getString(cursor.getColumnIndex(Resource.Info.AUTHOR_NAME))),
                        cursor.getString(cursor.getColumnIndex(Resource.Info.STREAM_URL)),
                        cursor.getString(cursor.getColumnIndex(Resource.Info.PATH_TO_FILE)),
                        cursor.getString(cursor.getColumnIndex(Resource.Info.ARTWORK_URL))
                );
                list.add(info);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public long addInfoToPlaylist(Info info, Playlist playlist) {
        ContentValues contentValues = new ContentValues();
        List<Info> list = getAll();
        Cursor cursor = database.query(Resource.Info.TABLE_NAME, null, null, null, null, null, null);
        cursor.move(list.indexOf(info));
        contentValues.put(Resource.Info.PLAYLIST, cursor.getString(cursor.getColumnIndex(Resource.Info.PLAYLIST)) + "===" + playlist.getTitle());
        return database.update(Resource.Info.TABLE_NAME, contentValues, "id = " + list.indexOf(info), null);
    }

    public long deleteInfoFromPlaylist(Info info, Playlist playlist) {
        ContentValues contentValues = new ContentValues();
        List<Info> list = getAll();
//        Info currentInfo = list.get(list.indexOf(info));
        Cursor cursor = database.query(Resource.Info.TABLE_NAME, null, null, null, null, null, null);
        cursor.move(list.indexOf(info));
        List<String> listOfPlaylists = StringGenerator.generateListOfPlaylists(cursor.getString(cursor.getColumnIndex(Resource.Info.PLAYLIST)));
        listOfPlaylists.remove(playlist.getTitle());
        contentValues.put(Resource.Info.PLAYLIST, StringGenerator.generatePlaylistStringFromList(listOfPlaylists));
        return database.update(Resource.Info.TABLE_NAME, contentValues, "id = " + list.indexOf(info), null);
    }

    private ContentValues parseInfo(Info info) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Resource.Info.TITLE, info.getTitle());
        contentValues.put(Resource.Info.STREAM_URL, info.getStream_url());
        contentValues.put(Resource.Info.ARTWORK_URL, info.getArtwork_url());
        contentValues.put(Resource.Info.DURATION, info.getDuration());
        contentValues.put(Resource.Info.AUTHOR_NAME, info.getUser().getUsername());
        contentValues.put(Resource.Info.PATH_TO_FILE, info.getPath_to_file());
        contentValues.put(Resource.Info.PLAYLIST, Resource.Playlist.ALL_MUSIC_PLAYLIST_TITLE);

        return contentValues;
    }

}