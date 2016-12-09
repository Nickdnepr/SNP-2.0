package test.homework.nick.snp20.events_for_eventbus.dialog_events;

import test.homework.nick.snp20.model.music_info_model.Info;
import test.homework.nick.snp20.model.playlist_model.Playlist;

import java.util.List;

/**
 * Created by Nick on 09.12.16.
 */
public class PlaylistDialogEvent {

    private List<Info> list;
    private Playlist playlist;


    public PlaylistDialogEvent(List<Info> list, Playlist playlist) {
        this.list = list;
        this.playlist = playlist;
    }

    public List<Info> getList() {
        return list;
    }

    public Playlist getPlaylist() {
        return playlist;
    }
}
