package test.homework.nick.snp20.events_for_eventbus.view_to_player_events;

/**
 * Created by Nick on 13.11.16.
 */
public class ActivityInformationEvent {
    private ListEvent playlist;
    private boolean musicPlaying;
    private boolean serviceAlive;
    private boolean playerActive;

    public ActivityInformationEvent(ListEvent playlist, boolean musicPlaying, boolean serviceAlive, boolean playerActive) {
        this.playlist = playlist;
        this.musicPlaying = musicPlaying;
        this.serviceAlive = serviceAlive;
        this.playerActive = playerActive;
    }

    public ListEvent getPlaylist() {
        return playlist;
    }

    public boolean isMusicPlaying() {
        return musicPlaying;
    }

    public boolean isServiceAlive() {
        return serviceAlive;
    }

    public boolean isPlayerActive() {
        return playerActive;
    }
}
