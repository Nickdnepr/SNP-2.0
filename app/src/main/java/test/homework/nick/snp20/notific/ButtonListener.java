package test.homework.nick.snp20.notific;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.greenrobot.eventbus.EventBus;
import test.homework.nick.snp20.events_for_eventbus.EventToService;
import test.homework.nick.snp20.utils.Commands;
import test.homework.nick.snp20.utils.Constants;

/**
 * Created by Nick on 28.11.16.
 */
public class ButtonListener extends BroadcastReceiver{

    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Log.i("notification", intent.getStringExtra(Constants.BROADCAST_INTENT_COMMAND_EXTRA_TITLE));

        if (intent.getStringExtra(Constants.BROADCAST_INTENT_COMMAND_EXTRA_TITLE).equals(Commands.PREVIOUS_COMMAND)){
            EventBus.getDefault().post(new EventToService(Commands.PREVIOUS_COMMAND));
            Log.i("notification", "previous sended");
        }

        if (intent.getStringExtra(Constants.BROADCAST_INTENT_COMMAND_EXTRA_TITLE).equals(Commands.NEXT_COMMAND)){
            EventBus.getDefault().post(new EventToService(Commands.NEXT_COMMAND));
            Log.i("notification", "next sended");
        }

        if (intent.getStringExtra(Constants.BROADCAST_INTENT_COMMAND_EXTRA_TITLE).equals(Commands.STOP_COMMAND)){
            EventBus.getDefault().post(new EventToService(Commands.STOP_COMMAND));
            Log.i("notification", "stop sended");
        }

        if (intent.getStringExtra(Constants.BROADCAST_INTENT_COMMAND_EXTRA_TITLE).equals(Commands.START_COMMAND)){
            EventBus.getDefault().post(new EventToService(Commands.START_COMMAND));
            Log.i("notification", "start sended");
        }

    }
}
