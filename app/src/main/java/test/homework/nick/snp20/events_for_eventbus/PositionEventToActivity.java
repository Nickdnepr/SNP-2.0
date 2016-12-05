package test.homework.nick.snp20.events_for_eventbus;

/**
 * Created by Nick on 13.11.16.
 */
public class PositionEventToActivity {

    private int position;
    private int duration;

    public PositionEventToActivity(int position, int duration) {
        this.position = position;
        this.duration=duration;
    }

    public int getPosition() {
        return position;
    }

    public int getDuration() {
        return duration;
    }
}
