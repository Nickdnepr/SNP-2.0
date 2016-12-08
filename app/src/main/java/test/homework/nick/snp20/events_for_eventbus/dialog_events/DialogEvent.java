package test.homework.nick.snp20.events_for_eventbus.dialog_events;

/**
 * Created by Nick on 08.12.16.
 */
public class DialogEvent {
    private String message;

    public DialogEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
