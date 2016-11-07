package test.homework.nick.snp20.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import test.homework.nick.snp20.events_for_eventbus.EventToActivity;
import test.homework.nick.snp20.events_for_eventbus.EventToService;
import test.homework.nick.snp20.events_for_eventbus.ListEvent;
import test.homework.nick.snp20.model.Info;
import test.homework.nick.snp20.utils.Commands;

import java.io.IOException;
import java.util.List;

/**
 * Created by Nick on 31.10.16.
 */


public class PlayerService extends Service implements MediaPlayer.OnPreparedListener {

    private MediaPlayer player;
    private List<Info> playlist;
    private int index;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().register(this);
        return super.onStartCommand(intent, flags, startId);
    }

    private void initPlayer() {
        if (player != null) {
            player.pause();
        }
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    private void startPlayerWithPath(String path) throws IOException {
        player.setDataSource(path);
        player.prepare();
    }

    @Subscribe
    public void onEvent(ListEvent event) throws IOException {
        initPlayer();
        playlist = event.getPlaylist();
        index = event.getIndex();
        Info info = playlist.get(index);
        startPlayerWithPath(info.getStream_url());
    }

    @Subscribe
    public void onEvent(EventToService eventToService) {
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
        EventBus.getDefault().post(Commands.SERVICE_DESTROY);
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        EventBus.getDefault().post(new EventToActivity(Commands.START_COMMAND));
    }
}
