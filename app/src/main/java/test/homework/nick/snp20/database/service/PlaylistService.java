package test.homework.nick.snp20.database.service;

import test.homework.nick.snp20.database.service.core.OpenDBService;
import test.homework.nick.snp20.database.service.core.Service;
import test.homework.nick.snp20.model.playlist_model.Playlist;

import java.util.List;

/**
 * Created by Nick on 06.12.16.
 */
public class PlaylistService extends OpenDBService implements Service<Playlist> {
    @Override
    public long save(Playlist playlist) {
        return 0;
    }

    @Override
    public List<Playlist> getAll() {
        return null;
    }
}
