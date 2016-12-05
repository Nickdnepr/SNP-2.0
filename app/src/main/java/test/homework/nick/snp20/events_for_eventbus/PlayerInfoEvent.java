package test.homework.nick.snp20.events_for_eventbus;

/**
 * Created by Nick on 13.11.16.
 */
public class PlayerInfoEvent {

    private boolean repeat;
    private boolean randomized;
    private int currentPosition;
    private int duration;
    private ListEvent playlist;
    private boolean playing;

    public PlayerInfoEvent(boolean repeat, boolean randomized, int currentPosition, int duration, ListEvent playlist, boolean playing) {
        this.repeat = repeat;
        this.randomized = randomized;
        this.currentPosition = currentPosition;
        this.duration = duration;
        this.playlist = playlist;
        this.playing = playing;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isRandomized() {
        return randomized;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getDuration() {
        return duration;
    }

    public ListEvent getPlaylist() {
        return playlist;
    }

    public boolean isPlaying() {
        return playing;
    }
}
