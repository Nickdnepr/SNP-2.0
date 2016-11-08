package test.homework.nick.snp20.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import test.homework.nick.snp20.events_for_eventbus.EventToActivity;
import test.homework.nick.snp20.events_for_eventbus.EventToService;
import test.homework.nick.snp20.events_for_eventbus.ListEvent;
import test.homework.nick.snp20.model.Info;
import test.homework.nick.snp20.utils.Commands;
import test.homework.nick.snp20.utils.Constants;

import java.io.IOException;
import java.util.List;

/**
 * Created by Nick on 31.10.16.
 */


public class PlayerService extends Service implements MediaPlayer.OnPreparedListener {

    private MediaPlayer player;
    private List<Info> playlist;
    private int index;
    public static String TAG = "service";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "service started");
        initPlayer();
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new EventToActivity(Commands.SERVICE_START));
        return super.onStartCommand(intent, flags, startId);
    }

    private void initPlayer() {
        Log.i(TAG, "started init player");
        if (player != null) {
            player.pause();
            Log.i(TAG, "player pause");
        }
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    private void startPlayerWithPath(String path) throws IOException {
        Log.i(TAG, "startPlayerCalled");
        player.setDataSource(path);
        player.setOnPreparedListener(this);
        player.prepare();
    }

    @Subscribe
    public void onEvent(ListEvent event) throws IOException {
        Log.i(TAG, "listEvent  received");
        initPlayer();
        playlist = event.getPlaylist();
        index = event.getIndex();
        Info info = playlist.get(index);

        //TODO
        startPlayerWithPath(info.getStream_url()+"?client_id="+ Constants.USER_ID);
        Log.i("player debug", info.getStream_url()+"?client_id="+ Constants.USER_ID);
    }

    @Subscribe
    public void onEvent(EventToService eventToService) {
        Log.i(TAG, "eventToService  received");
        if (eventToService.getMessage().equals(Commands.START_COMMAND)) {
            player.start();
            EventBus.getDefault().post(new EventToActivity(Commands.START_COMMAND));
        }

        if (eventToService.getMessage().equals(Commands.STOP_COMMAND)) {
            player.pause();
            EventBus.getDefault().post(new EventToActivity(Commands.STOP_COMMAND));
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().post(Commands.SERVICE_DESTROY);
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        EventBus.getDefault().post(new EventToActivity(Commands.START_COMMAND));
    }
}
