package test.homework.nick.snp20.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import test.homework.nick.snp20.events_for_eventbus.view_to_player_events.ActivityInformationEvent;
import test.homework.nick.snp20.events_for_eventbus.view_to_player_events.EventToActivity;
import test.homework.nick.snp20.events_for_eventbus.view_to_player_events.ListEvent;
import test.homework.nick.snp20.events_for_eventbus.view_to_player_events.PlayerInfoEvent;
import test.homework.nick.snp20.services.PlayerService;
import test.homework.nick.snp20.utils.string_containers.Commands;
import test.homework.nick.snp20.view.ViewModel;

/**
 * Created by Nick on 13.11.16.
 */
public abstract class MActivity extends AppCompatActivity implements ViewModel{
    protected boolean musicPlaying = false;
    protected boolean serviceAlive = false;
    protected boolean playerActive = false;
    protected ListEvent reservePlaylist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
//        initControlElements();
//        setupControlElements();
    }


    public abstract void initControlElements();

    public abstract void setupControlElements();


    protected void startPlayerService(boolean startMusic) {
        Intent intent = new Intent(MActivity.this, PlayerService.class);
        startService(intent);
        if (startMusic) {
            EventBus.getDefault().post(reservePlaylist);
        }
    }

    @Subscribe
    public void onEvent(ListEvent listEvent) {
        reservePlaylist = listEvent;
        Log.i("debug event", reservePlaylist.getPlaylist().toString());
        if (!serviceAlive) {
            startPlayerService(true);
        }
    }

    @Subscribe
    public void onEvent(EventToActivity eventToActivity) {
        if (eventToActivity.getMessage().equals(Commands.PLAYER_ACTIVE)||eventToActivity.getMessage().equals(Commands.PLAYER_DEACTIVE)) {
            playerActive = !playerActive;
        }


    }

    @Subscribe
    public void onEvent(PlayerInfoEvent playerInfoEvent) {
        reservePlaylist = playerInfoEvent.getPlaylist();
        musicPlaying = playerInfoEvent.isPlaying();
    }

    @Subscribe
    public void onEvent(ActivityInformationEvent activityInformationEvent) {
        reservePlaylist = activityInformationEvent.getPlaylist();
        musicPlaying = activityInformationEvent.isMusicPlaying();
        serviceAlive = activityInformationEvent.isServiceAlive();
        playerActive = activityInformationEvent.isPlayerActive();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("activity configuration", "configuration changed");

        try {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
                Log.i("activity configuration", reservePlaylist.getPlaylist().toString());
                Log.i("activity configuration", "landscape");
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
                Log.i("activity configuration", reservePlaylist.getPlaylist().toString());
                Log.i("activity configuration", "portrait");
            }
        } catch (NullPointerException e) {
            Log.e("activity configuration", "playlist is null");
        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().postSticky(new ActivityInformationEvent(reservePlaylist, musicPlaying, serviceAlive, playerActive));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
