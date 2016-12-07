package test.homework.nick.snp20.database.service;

import android.content.Context;
import test.homework.nick.snp20.database.dao.PlaylistDao;
import test.homework.nick.snp20.database.service.core.OpenDBService;
import test.homework.nick.snp20.database.service.core.Service;
import test.homework.nick.snp20.model.playlist_model.Playlist;

import java.util.List;

/**
 * Created by Nick on 06.12.16.
 */
public class PlaylistService extends OpenDBService implements Service<Playlist> {

    private Context context;

    public PlaylistService(Context context) {
        this.context = context;
    }

    @Override
    public long save(Playlist playlist) {

        try {
            if (!isOpen()) {
                open(context);
            }
            return new PlaylistDao(getDatabase()).save(playlist);
        } finally {
            if (isOpen()) {
                close();
            }
        }
    }

    @Override
    public List<Playlist> getAll() {
        try {
            if (!isOpen()) {
                open(context);
            }
            return new PlaylistDao(getDatabase()).getAll();
        } finally {
            if (isOpen()) {
                close();
            }
        }
    }
}
