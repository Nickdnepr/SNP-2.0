package test.homework.nick.snp20.events_for_eventbus;

/**
 * Created by Nick on 04.11.16.
 */
public class EventToActivity {
    private String message;

    public EventToActivity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
