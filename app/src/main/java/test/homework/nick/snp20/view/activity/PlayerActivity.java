package test.homework.nick.snp20.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.database.Resource;
import test.homework.nick.snp20.database.service.InfoService;
import test.homework.nick.snp20.events_for_eventbus.dialog_events.AddInfoToPlaylistDialogEvent;
import test.homework.nick.snp20.events_for_eventbus.view_to_player_events.*;
import test.homework.nick.snp20.model.music_info_model.Info;
import test.homework.nick.snp20.model.playlist_model.Playlist;
import test.homework.nick.snp20.utils.string_containers.Commands;
import test.homework.nick.snp20.utils.converters.ProgressToMillsConverter;
import test.homework.nick.snp20.utils.converters.StringGenerator;
import test.homework.nick.snp20.view.fragments.dialog_fragments.AddInfoToPlaylistDialog;

/**
 * Created by Nick on 13.11.16.
 */
public class PlayerActivity extends MActivity {

    private boolean repeat = false;
    private boolean randomized = false;
    private int durationMills = 0;
    private int positionMills = 0;


    private SeekBar progressSeekBar;
    private TextView title;
    private TextView author;
    private TextView currentPosition;
    private TextView duration;
    private ImageView backButton;
    private ImageView showPlaylistButton;
    private ImageView addInfoButton;
    private ImageView randomPlaylistButton;
    private ImageView previousButton;
    private ImageView startStopButton;
    private ImageView nextButton;
    private ImageView repeatButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity_layout);
        initControlElements();
        setupControlElements();
    }

    @Override
    protected void onResume() {
        super.onResume();


        try {
            if (repeat) {
                repeatButton.setBackgroundColor(getResources().getColor(R.color.red));
            } else {
                repeatButton.setBackgroundColor(getResources().getColor(R.color.white));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initControlElements() {
        progressSeekBar = (SeekBar) findViewById(R.id.player_progress_seek_bar);
        title = (TextView) findViewById(R.id.player_title);
        author = (TextView) findViewById(R.id.player_author);
        currentPosition = (TextView) findViewById(R.id.player_current_position);
        duration = (TextView) findViewById(R.id.player_duration);
        backButton = (ImageView) findViewById(R.id.player_back_button);
        showPlaylistButton = (ImageView) findViewById(R.id.show_playlist_button);
        addInfoButton = (ImageView) findViewById(R.id.add_info_button);
        randomPlaylistButton = (ImageView) findViewById(R.id.player_random_playlist_button);
        previousButton = (ImageView) findViewById(R.id.player_previous_button);
        startStopButton = (ImageView) findViewById(R.id.player_play_stop_button);
        nextButton = (ImageView) findViewById(R.id.player_next_button);
        repeatButton = (ImageView) findViewById(R.id.player_repeat_button);
    }

    @Override
    public void setupControlElements() {

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicPlaying) {
                    EventBus.getDefault().post(new EventToService(Commands.STOP_COMMAND));
                } else {
                    EventBus.getDefault().post(new EventToService(Commands.START_COMMAND));
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventToService(Commands.NEXT_COMMAND));
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventToService(Commands.PREVIOUS_COMMAND));
            }
        });

        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventToService(Commands.REPEAT_COMMAND));
            }
        });

        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    EventBus.getDefault().post(new PositionEventToService(ProgressToMillsConverter.progressToMills(durationMills, progress)));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if (repeat) {
            repeatButton.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            repeatButton.setBackgroundColor(getResources().getColor(R.color.white));
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        showPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        addInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInfoToPlaylistDialog dialog = new AddInfoToPlaylistDialog();
                dialog.show(getSupportFragmentManager(), "add_info_dialog");
            }
        });
    }

    @Subscribe
    public void onEvent(PositionEventToActivity positionEventToActivity) {
        positionMills = positionEventToActivity.getPosition();
        durationMills = positionEventToActivity.getDuration();
        currentPosition.setText(StringGenerator.generateDurationString(positionMills));
        duration.setText(StringGenerator.generateDurationString(durationMills));
        Log.i("position event", String.valueOf(positionMills));
        Log.i("position event", String.valueOf(durationMills));
        progressSeekBar.setProgress(ProgressToMillsConverter.millsToProgress(durationMills, positionMills));
    }

    @Subscribe
    public void onEvent(AddInfoToPlaylistDialogEvent addInfoToPlaylistDialogEvent){
        //checking current info for existing in the chosen playlist
        if (!new InfoService(this).getPlaylist(addInfoToPlaylistDialogEvent.getPlaylist()).contains(reservePlaylist.getPlaylist().get(reservePlaylist.getIndex()))){
            //checking current info for existing in database
            if (!new InfoService(this).getPlaylist(new Playlist(Resource.Playlist.ALL_MUSIC_PLAYLIST_TITLE, null)).contains(reservePlaylist.getPlaylist().get(reservePlaylist.getIndex()))){
                new InfoService(this).save(reservePlaylist.getPlaylist().get(reservePlaylist.getIndex()));
            }
            new InfoService(this).addInfoToPlaylist(reservePlaylist.getPlaylist().get(reservePlaylist.getIndex()), addInfoToPlaylistDialogEvent.getPlaylist());

        }
    }

    @Override
    public void onEvent(ActivityInformationEvent activityInformationEvent) {
        super.onEvent(activityInformationEvent);
        Info info = reservePlaylist.getPlaylist().get(reservePlaylist.getIndex());
        title.setText(info.getTitle());
        author.setText(info.getUser().getUsername());
        duration.setText(StringGenerator.generateDurationString(info.getDuration()));
    }

    @Override
    public void onEvent(PlayerInfoEvent playerInfoEvent) {
        super.onEvent(playerInfoEvent);
        Info currentInfo = reservePlaylist.getPlaylist().get(reservePlaylist.getIndex());
        title.setText(currentInfo.getTitle());
        author.setText(currentInfo.getUser().getUsername());
        currentPosition.setText(StringGenerator.generateDurationString(playerInfoEvent.getCurrentPosition()));
        duration.setText(StringGenerator.generateDurationString(playerInfoEvent.getDuration()));
        repeat = playerInfoEvent.isRepeat();
        randomized = playerInfoEvent.isRandomized();

        if (repeat) {
            repeatButton.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            repeatButton.setBackgroundColor(getResources().getColor(R.color.white));
        }

        if (musicPlaying) {
            startStopButton.setImageResource(R.drawable.ic_pause_circle_filled_black_48dp);
        } else {
            startStopButton.setImageResource(R.drawable.ic_play_circle_filled_black_48dp);
        }
    }
}
