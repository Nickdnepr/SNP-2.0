package test.homework.nick.snp20.events_for_eventbus.view_to_player_events;

/**
 * Created by Nick on 13.11.16.
 */
public class PositionEventToService {

    private int position;

    public PositionEventToService(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
