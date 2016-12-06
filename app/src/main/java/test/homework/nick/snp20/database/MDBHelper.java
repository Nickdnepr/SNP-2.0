package test.homework.nick.snp20.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import test.homework.nick.snp20.model.music_info_model.Info;
import test.homework.nick.snp20.model.music_info_model.User;
import test.homework.nick.snp20.model.playlist_model.Playlist;
import test.homework.nick.snp20.utils.Constants;
import test.homework.nick.snp20.utils.StringGenerator;

import java.util.ArrayList;

/**
 * Created by Nick on 22.11.16.
 */
public class MDBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private Cursor musicTableCursor;
    private Cursor playlistTableCursor;

    // musicTable fields
    private int musicIdFieldIndex;
    private int titleFieldIndex;
    private int authorNameFieldIndex;
    private int streamUrlFieldIndex;
    private int artworkUrlFieldInsex;
    private int durationFieldIndex;
    private int pathToFileFieldIndex;
    private int playlistFieldIndex;
    // playlist fields
    private int playlistIdFieldIndex;
    private int playlsitTitleFieldIndex;
    private int playlistArtworkPath;

    public MDBHelper(Context context) {
        super(context, "snpMusicDataBase", null, 1);
        database = getWritableDatabase();
        Log.i("mdatabase", database.toString());
//        musicTableCursor = database.query(Constants.ALL_MUSIC_TABLE_TITLE, null, null, null, null, null, null);
//        playlistTableCursor = database.query(Constants.PLAYLIST_TABLE_TITLE, null, null, null, null, null, null);
        Log.i("mdatabase", "constructor called");
    }

    public void addInfo(Info addedInfo, String playlist) {
        initFields();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TITLE_FIELD_TITLE, addedInfo.getTitle());
        contentValues.put(Constants.AUTHOR_NAME_FIELD_TITLE, addedInfo.getUser().getUsername());
        contentValues.put(Constants.STREAM_URL_FIELD_TITLE, addedInfo.getStream_url());
        contentValues.put(Constants.ARTWORK_URL_FIELD_TITLE, addedInfo.getArtwork_url());
        contentValues.put(Constants.DURATION_FIELD_TITLE, addedInfo.getDuration());
        contentValues.put(Constants.PATH_TO_FIELD_TITLE, addedInfo.getPath_to_file());

        if (playlist == null) {
            contentValues.put(Constants.PLAYLIST_FIELD_TITLE, "All music,");
        } else {
            contentValues.put(Constants.PLAYLIST_FIELD_TITLE, playlist);
        }

        database.insert(Constants.ALL_MUSIC_TABLE_TITLE, null, contentValues);

    }

    public void addInfoToPlaylist(Info info, String playlist) {
        initFields();
        String playlists = "";
        int id = 0;
        if (musicTableCursor.moveToFirst()) {
            do {
                if (info.getStream_url().equals(musicTableCursor.getString(streamUrlFieldIndex))) {
                    playlists = musicTableCursor.getString(playlistFieldIndex);
                    break;
                }
                id++;
            } while (musicTableCursor.moveToNext());
        }

        ContentValues contentValues = new ContentValues();
        playlists += playlist;
        playlists += "===";

        contentValues.put(Constants.PLAYLIST_FIELD_TITLE, playlists);
        database.update(Constants.ALL_MUSIC_TABLE_TITLE, contentValues, "id = " + id, null);


    }

    public void addPlaylist(String playlist) {
        initFields();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TITLE_FIELD_TITLE, playlist);

        database.insert(Constants.PLAYLIST_TABLE_TITLE, null, contentValues);

    }

    public ArrayList<Playlist> getListOfPlaylists() {
        Log.i("mdatabase", "getListOfPlaylists called");

        initFields();
        ArrayList<Playlist> playlistList = new ArrayList<>();
        if (playlistTableCursor.moveToFirst()) {
            do {
                playlistList.add(new Playlist(playlistTableCursor.getString(playlsitTitleFieldIndex), playlistTableCursor.getString(playlistArtworkPath)));
            } while (playlistTableCursor.moveToNext());
        }

        return playlistList;
    }

    public ArrayList<Info> getPlaylist(String playlistTitle) {
        initFields();
        ArrayList<Info> playlist = new ArrayList<>();
        if (musicTableCursor.moveToFirst()) {
            do {

                Info tmp = new Info(musicTableCursor.getString(titleFieldIndex), musicTableCursor.getInt(durationFieldIndex), new User(musicTableCursor.getString(authorNameFieldIndex)), musicTableCursor.getString(streamUrlFieldIndex), musicTableCursor.getString(pathToFileFieldIndex), musicTableCursor.getString(artworkUrlFieldInsex));

                ArrayList<String> listOfPlayLists = StringGenerator.generateListOfPlaylists(musicTableCursor.getString(playlistFieldIndex));
                for (int i = 0; i < listOfPlayLists.size(); i++) {
                    if (listOfPlayLists.get(i).equals(playlistTitle)) {
                        playlist.add(tmp);
                    }
                }


            } while (musicTableCursor.moveToNext());
        }
        return playlist;
    }


//
//    private ArrayList<Info> getAllMusicInDataBase(){
//        initFields();
//        ArrayList<Info> allMusic = new ArrayList<>();
//
//        if (musicTableCursor.moveToFirst()){
//            do {
//                Info tmp = new Info(musicTableCursor.getString(titleFieldIndex), musicTableCursor.getInt(durationFieldIndex), new User(musicTableCursor.getString(authorNameFieldIndex)), musicTableCursor.getString(streamUrlFieldIndex), musicTableCursor.getString(pathToFileFieldIndex), musicTableCursor.getString(artworkUrlFieldInsex));
//                allMusic.add(tmp);
//            }while (musicTableCursor.moveToNext());
//        }
//
//        return allMusic;
//    }

    public void deleteInfo(Info info) {

        initFields();
        if (musicTableCursor.moveToFirst()) {
            do {
                String url = musicTableCursor.getString(streamUrlFieldIndex);
                if (url.equals(info.getStream_url())) {
                    database.delete(Constants.ALL_MUSIC_TABLE_TITLE, "id = " + musicTableCursor.getPosition(), null);
                }
            } while (musicTableCursor.moveToNext());
        }

    }

    public void deletePlaylist(String playlist) {
        //  TODO delete playlist method
    }

    public void renameInfo(Info info) {
        //  TODO rename info method

    }

    public void renamePlaylist(String oldName, String newName) {
        //  TODO rename playlist method

    }


    private void initFields() {
        Log.i("mdatabase", "initFields called");
//        database=getWritableDatabase();
        musicTableCursor = database.query(Constants.ALL_MUSIC_TABLE_TITLE, null, null, null, null, null, null);

        musicIdFieldIndex = musicTableCursor.getColumnIndex(Constants.ID_FIELD_TITLE);
        titleFieldIndex = musicTableCursor.getColumnIndex(Constants.TITLE_FIELD_TITLE);
        authorNameFieldIndex = musicTableCursor.getColumnIndex(Constants.AUTHOR_NAME_FIELD_TITLE);
        streamUrlFieldIndex = musicTableCursor.getColumnIndex(Constants.STREAM_URL_FIELD_TITLE);
        artworkUrlFieldInsex = musicTableCursor.getColumnIndex(Constants.ARTWORK_URL_FIELD_TITLE);
        durationFieldIndex = musicTableCursor.getColumnIndex(Constants.DURATION_FIELD_TITLE);
        pathToFileFieldIndex = musicTableCursor.getColumnIndex(Constants.PATH_TO_FIELD_TITLE);
        playlistFieldIndex = musicTableCursor.getColumnIndex(Constants.PLAYLIST_FIELD_TITLE);


        playlistTableCursor = database.query(Constants.PLAYLIST_TABLE_TITLE, null, null, null, null, null, null);

        playlistIdFieldIndex = playlistTableCursor.getColumnIndex(Constants.ID_FIELD_TITLE);
        playlsitTitleFieldIndex = playlistTableCursor.getColumnIndex(Constants.TITLE_FIELD_TITLE);
        playlistArtworkPath = playlistTableCursor.getColumnIndex(Constants.PLAYLIST_ARTWORK_TITLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table allMusicTable (" +
                "id integer primary key autoincrement," +
                "title text," +
                "author_name text," +
                "stream_url text," +
                "artwork_url text," +
                "duration int," +
                "path_to_file text," +
                "playlist text" +
                ");");

        db.execSQL("create table playlistTable (" +
                "id integer primary key autoincrement," +
                "title text," +
                "artwork_path text" +
                ");");

        addPlaylist("All music");
        addPlaylist("Downloads");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
