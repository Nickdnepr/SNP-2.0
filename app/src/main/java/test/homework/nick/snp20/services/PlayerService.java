package test.homework.nick.snp20.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import test.homework.nick.snp20.events_for_eventbus.*;
import test.homework.nick.snp20.model.music_info_model.Info;
import test.homework.nick.snp20.notific.NotificationHelper;
import test.homework.nick.snp20.utils.Commands;
import test.homework.nick.snp20.utils.Constants;

import java.io.IOException;
import java.util.List;

/**
 * Created by Nick on 31.10.16.
 */


public class PlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer player;
    private List<Info> playlist;
    private int index;
    private boolean playerActive = false;
    private boolean repeat = false;
    private boolean randomize;
    private boolean positionSending;
    private NotificationHelper notificationHelper;

    public static String TAG = "my_service_tag";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "service started");

        try {
            EventBus.getDefault().register(this);
            if (notificationHelper == null) {
                notificationHelper = new NotificationHelper(this, false, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        EventBus.getDefault().post(new EventToActivity(Commands.SERVICE_START));
        return super.onStartCommand(intent, flags, startId);
    }


    private void initPlayer() {
        Log.i(TAG, "started init player");
        if (player != null) {
            player.release();

            Log.i(TAG, "player pause");
        }

        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        try {
            startPositionSending();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startPlayerWithPath(String path) {
        Log.i(TAG, "startPlayerCalled");
        initPlayer();

        try {
            player.setDataSource(path + "?client_id=" + Constants.USER_ID);
        } catch (IOException e) {
            Log.i(TAG, "player io error on setdata");
            e.printStackTrace();
        }


        player.prepareAsync();
        sendStatus();
    }

    @Subscribe
    public void onEvent(ListEvent listEvent) {
        Log.i(TAG, "listEvent  received");
//        initPlayer();
        playlist = listEvent.getPlaylist();
        index = listEvent.getIndex();
        Info info = playlist.get(index);

        //TODO playing from sdcard
        Log.i(TAG, info.getStream_url() + "?client_id=" + Constants.USER_ID);
        startPlayerWithPath(info.getStream_url());

    }

    @Subscribe
    public void onEvent(PositionEventToService positionEventToService) {
        player.seekTo(positionEventToService.getPosition());
    }

    @Subscribe
    public void onEvent(EventToService eventToService) {

        Log.i("notification service", eventToService.getMessage());
        Log.i(TAG, "eventToService  received");
        Log.i(TAG, eventToService.getMessage());
        if (eventToService.getMessage().equals(Commands.START_COMMAND)) {
            player.start();
            sendStatus();
        }

        if (eventToService.getMessage().equals(Commands.STOP_COMMAND)) {
            player.pause();
            sendStatus();
        }

        if (eventToService.getMessage().equals(Commands.NEXT_COMMAND)) {
            nextSong();
        }

        if (eventToService.getMessage().equals(Commands.PREVIOUS_COMMAND)) {
            previousSong();
        }

        if (eventToService.getMessage().equals(Commands.REPEAT_COMMAND)) {
            repeat = !repeat;
        }

        if (eventToService.getMessage().equals(Commands.NOTIFICATION_START_STOP_COMMAND)){
            Log.i(TAG, "start stop recieved");
            if (player.isPlaying()){
                player.pause();
            }else {
                player.start();
            }
        }

        sendStatus();
    }


    private void nextSong() {
        Log.i(TAG, "next command");
        if (index + 1 == playlist.size()) {
            index = 0;
        } else {
            index++;
        }
        startPlayerWithPath(playlist.get(index).getStream_url());
    }


    private void previousSong() {
        Log.i(TAG, "previous command");
        if (index - 1 == 0) {
            index = playlist.size() - 1;
        } else {
            index--;
        }
        startPlayerWithPath(playlist.get(index).getStream_url());
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        EventBus.getDefault().post(new EventToActivity(Commands.PLAYER_ACTIVE));
        mp.start();
        sendStatus();
        player.setOnCompletionListener(this);

    }

    private void startPositionSending() {
        if (!positionSending){
            new AsyncTask() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    positionSending=true;
                }

                @Override
                protected Object doInBackground(Object[] params) {
                    Log.i(TAG, "sss");
                    while (true) {
                        try {
                            Thread.sleep(500);
                            publishProgress();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }


                @Override
                protected void onProgressUpdate(Object[] values) {
                    super.onProgressUpdate(values);
                    EventBus.getDefault().post(new PositionEventToActivity(player.getCurrentPosition(), player.getDuration()));
                    notificationHelper.setCurrentInfo(new PlayerInfoEvent(repeat, randomize, player.getCurrentPosition(), player.getDuration(), new ListEvent(playlist, index), player.isPlaying()));
                    sendStatus();
                }

                @Override
                protected void onCancelled() {
                    super.onCancelled();
                    positionSending=false;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    positionSending=false;
                }


            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }


    }

    private void sendStatus() {
        EventBus.getDefault().post(new PlayerInfoEvent(repeat, randomize, player.getCurrentPosition(), player.getDuration(), new ListEvent(playlist, index), player.isPlaying()));
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i(TAG, "music completed");
        if (!repeat) {
            nextSong();
        } else {
            startPlayerWithPath(playlist.get(index).getStream_url());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().post(new EventToActivity(Commands.SERVICE_DESTROY));
        notificationHelper.hideNotification();
        if (playerActive) {
            EventBus.getDefault().post(new EventToActivity(Commands.PLAYER_DEACTIVE));
        }
    }
}
