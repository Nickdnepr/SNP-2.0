package test.homework.nick.snp20.events_for_eventbus;

import test.homework.nick.snp20.model.music_info_model.Info;

import java.util.List;

/**
 * Created by Nick on 31.10.16.
 */
public class ListEvent {

    private List<Info> playlist;
    private int index;


    public ListEvent(List<Info> playlist, int index) {
        this.playlist = playlist;
        this.index = index;

    }


    public List<Info> getPlaylist() {
        return playlist;
    }

    public int getIndex() {
        return index;
    }


}
