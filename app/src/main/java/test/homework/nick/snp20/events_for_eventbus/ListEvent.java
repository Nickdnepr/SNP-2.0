package test.homework.nick.snp20.events_for_eventbus;

import test.homework.nick.snp20.model.Info;

import java.util.List;

/**
 * Created by Nick on 31.10.16.
 */
public class ListEvent {

    private List<Info> playlist;
    private int index;
    private boolean startWhenReceive;

    public ListEvent(List<Info> playlist, int index, boolean startWhenReceive) {
        this.playlist = playlist;
        this.index = index;
        this.startWhenReceive = startWhenReceive;
    }


    public List<Info> getPlaylist() {
        return playlist;
    }

    public int getIndex() {
        return index;
    }

    public boolean isStartWhenReceive() {
        return startWhenReceive;
    }
}
