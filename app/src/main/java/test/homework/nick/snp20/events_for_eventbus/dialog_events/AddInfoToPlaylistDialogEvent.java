package test.homework.nick.snp20.events_for_eventbus.dialog_events;

import test.homework.nick.snp20.model.music_info_model.Info;
import test.homework.nick.snp20.model.playlist_model.Playlist;

/**
 * Created by Nick on 08.12.16.
 */
public class AddInfoToPlaylistDialogEvent {


    private Playlist playlist;

    public AddInfoToPlaylistDialogEvent(Playlist playlist) {

        this.playlist = playlist;
    }


    public Playlist getPlaylist() {
        return playlist;
    }
}
