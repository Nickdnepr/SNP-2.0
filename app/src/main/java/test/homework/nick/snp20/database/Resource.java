package test.homework.nick.snp20.database;

/**
 * Created by Nick on 06.12.16.
 */
public class Resource {

    public static final String DB_NAME = "snpMusicDataBase";
    public static final int DB_VERSION = 1;

    public static final class Info {
        public static final String ID = "id";
        public static final String TABLE_NAME = "allMusicTable";
        public static final String TITLE = "title";
        public static final String STREAM_URL = "stream_url";
        public static final String DURATION = "duration";
        public static final String ARTWORK_URL = "artwork_url";
        public static final String PATH_TO_FILE = "path_to_file";
        public static final String AUTHOR_NAME = "author_name";
        public static final String PLAYLIST = "playlist";

        public static final String CREATE_TABLE = "create table " + TABLE_NAME +
                " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                STREAM_URL + " TEXT, " +
                DURATION + " INTEGER, " +
                ARTWORK_URL + " TEXT, " +
                PATH_TO_FILE + " TEXT, " +
                AUTHOR_NAME + " TEXT, " +
                PLAYLIST + " TEXT);";
    }

    public static final class Playlist{
        public static final String ID = "id";
        public static final String TABLE_NAME = "playlistTable";
        public static final String TITLE = "title";
        public static final String ARTWORK_URL = "artwork_url";

        public static final String ALL_MUSIC_PLAYLIST_TITLE = "All music";
        public static final String DOWNLOADED_MUSIC_PLAYLIST_TITLE = "Downloaded music";


        public static final String CREATE_TABLE = "create table " + TABLE_NAME +
                " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                ARTWORK_URL + " TEXT);";

    }
}
