package test.homework.nick.snp20.notific;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.events_for_eventbus.view_to_player_events.PlayerInfoEvent;
import test.homework.nick.snp20.utils.Commands;
import test.homework.nick.snp20.utils.Constants;

/**
 * Created by Nick on 28.11.16.
 */
public class NotificationHelper {

    private Context context;
    private boolean playing;
    private PlayerInfoEvent currentInfo;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    private RemoteViews remoteViews;
    private final int notificationId = (int) System.currentTimeMillis();

    public NotificationHelper(Context context, boolean playing, PlayerInfoEvent currentInfo) {
        this.context = context;
        this.playing = playing;
        this.currentInfo = currentInfo;


    }


    public void showNotification() {
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        if (!playing) {
            remoteViews.setImageViewResource(R.id.notification_pause_button, R.drawable.ic_play_arrow_black_48dp);
        } else {
            remoteViews.setImageViewResource(R.id.notification_pause_button, R.drawable.ic_pause_black_48dp);
        }

        setListeners();


        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_pause_black_48dp);
        builder.setAutoCancel(false);
        builder.setContent(remoteViews);
        builder.setOngoing(true);

        manager.notify(notificationId, builder.build());
    }

    private void setListeners() {
        Intent previousButtonIntent = new Intent(context.getString(R.string.notification_broadcast_title));
        previousButtonIntent.putExtra(Constants.BROADCAST_INTENT_COMMAND_EXTRA_TITLE, Commands.PREVIOUS_COMMAND);
        PendingIntent previousClickIntent = PendingIntent.getBroadcast(context, 0, previousButtonIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.notification_previous_button, previousClickIntent);

        Intent nextButtonIntent = new Intent(context.getString(R.string.notification_broadcast_title));
        nextButtonIntent.putExtra(Constants.BROADCAST_INTENT_COMMAND_EXTRA_TITLE, Commands.NEXT_COMMAND);
        PendingIntent nextClickIntent = PendingIntent.getBroadcast(context, 1, nextButtonIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.notification_next_button, nextClickIntent);

        Intent pauseButtonIntent;

        pauseButtonIntent = new Intent(context.getString(R.string.notification_broadcast_title));
        pauseButtonIntent.putExtra(Constants.BROADCAST_INTENT_COMMAND_EXTRA_TITLE, Commands.NOTIFICATION_START_STOP_COMMAND);
        PendingIntent pauseClickIntent = PendingIntent.getBroadcast(context, 2, pauseButtonIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.notification_pause_button, pauseClickIntent);

    }

    public void hideNotification() {
        manager.cancel(notificationId);
    }

    public void setCurrentInfo(PlayerInfoEvent currentInfo) {
        this.currentInfo = currentInfo;
        playing = currentInfo.isPlaying();
        showNotification();
    }
}
