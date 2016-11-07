package test.homework.nick.snp20.events_for_eventbus;

/**
 * Created by Nick on 31.10.16.
 */
public class EventToService {
    private String message;

    public EventToService(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
