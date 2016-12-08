package test.homework.nick.snp20.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.events_for_eventbus.view_to_player_events.EventToActivity;
import test.homework.nick.snp20.events_for_eventbus.view_to_player_events.EventToService;
import test.homework.nick.snp20.events_for_eventbus.view_to_player_events.PlayerInfoEvent;
import test.homework.nick.snp20.view.fragments.PlaylistsEditFragment;
import test.homework.nick.snp20.view.fragments.SearchPlayerFragment;
import test.homework.nick.snp20.utils.Commands;

public class MainActivity extends MActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean bottomSheetExtended = false;


    private FrameLayout fragmentContent;
    private LinearLayout bottom_panel;
    private BottomSheetBehavior bottomSheetBehavior;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView toolbarContext;
    private TextView bottomSheetTitle;
    private NavigationView navigationView;
    private ImageView pauseAndPlayBottomButton;
    private ImageView nextBottomButton;
    private ImageView previousBottomButton;

    private SearchPlayerFragment searchPlayerFragment;
    private PlaylistsEditFragment playlistsEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startPlayerService(false);
        initControlElements();
        setupControlElements();
        changeFragment(searchPlayerFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (playerActive) {
                openSheet();
            } else {
                closeSheet();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    //initializing all control elements from xml, like layouts, buttons, textViews, toolbar, etc.
    //здесь инициализируются все управляющие элементы типа кнопок, тулбаров и им подобных
    public void initControlElements() {
        bottom_panel = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_panel);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragmentContent = (FrameLayout) findViewById(R.id.fragmentHost);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarContext = (TextView) findViewById(R.id.text_in_toolbar);
        bottomSheetTitle = (TextView) findViewById(R.id.bottom_panel_title);
        navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        pauseAndPlayBottomButton = (ImageView) findViewById(R.id.bottom_panel_pause_start_button);
        nextBottomButton = (ImageView) findViewById(R.id.bottom_panel_next_button);
        previousBottomButton = (ImageView) findViewById(R.id.bottom_panel_previous_button);
    }


    //setting clickListeners, attaching elements from upper method to activity, etc.
    //здесь инициализированые в методе выше элементы привязываются к логике, устанавливаются кликлистенеры и тому подобные
    public void setupControlElements() {


        searchPlayerFragment = new SearchPlayerFragment();
        playlistsEditFragment = new PlaylistsEditFragment();
        //attaching toolbar to activity, enabling home button
        //подключение тулбара к активити, включение домашней кнопки на тулбаре
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //attaching toolbar to navigationDrawer(drawerLayout)
        //привязка тулбара к боковой панели навигации
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        //disabling bottomSheet sliding
        //отключение стайдинга иижней контрольной панели(bottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (bottomSheetExtended) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        //setting NavigationItemSelectedListener, it selects the pressed item in navigation drawer. here the activity is the listener
        //установка листенера на боковую выдвижную панель(navigation drawer), он выделяет нажатый элемент в списке. листенером выступает активити
        navigationView.setNavigationItemSelectedListener(this);

        pauseAndPlayBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicPlaying) {
                    EventBus.getDefault().post(new EventToService(Commands.STOP_COMMAND));
                } else {
                    EventBus.getDefault().post(new EventToService(Commands.START_COMMAND));
                }
            }
        });

        nextBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventToService(Commands.NEXT_COMMAND));
            }
        });

        previousBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventToService(Commands.PREVIOUS_COMMAND));
            }
        });

        bottom_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                startActivity(intent);
            }
        });

        if (bottomSheetExtended) {
            openSheet();
        }
    }


    //changing fragment in activity
    //меняет фрагмент в активити
    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHost, fragment).commit();
    }


    public void openSheet() {
        bottomSheetTitle.setText(reservePlaylist.getPlaylist().get(reservePlaylist.getIndex()).getTitle());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetExtended = true;
    }

    public void closeSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetExtended = false;
    }

    @Subscribe
    public void onEvent(EventToActivity eventToActivity) {
        super.onEvent(eventToActivity);
        if (eventToActivity.getMessage().equals(Commands.START_COMMAND)) {
            openSheet();
            musicPlaying = true;
            pauseAndPlayBottomButton.setImageResource(R.drawable.ic_pause_black_48dp);
        }

        if (eventToActivity.getMessage().equals(Commands.STOP_COMMAND)) {
            musicPlaying = false;
            pauseAndPlayBottomButton.setImageResource(R.drawable.ic_play_arrow_black_48dp);
        }

        if (eventToActivity.getMessage().equals(Commands.SERVICE_START)) {
            Log.i("MainActivity", "service is alive and connected");
            serviceAlive = true;
        }

        if (eventToActivity.getMessage().equals(Commands.SERVICE_DESTROY)) {
            Log.i("MainActivity", "service is dead");
            closeSheet();
            musicPlaying = false;
            serviceAlive = false;
        }


    }

    @Override
    public void onEvent(PlayerInfoEvent playerInfoEvent) {
        super.onEvent(playerInfoEvent);
        if (musicPlaying) {
            openSheet();
            pauseAndPlayBottomButton.setImageResource(R.drawable.ic_pause_black_48dp);
        } else {
            pauseAndPlayBottomButton.setImageResource(R.drawable.ic_play_arrow_black_48dp);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (bottomSheetExtended) {
            openSheet();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.drawer_search:
                changeFragment(searchPlayerFragment);
                break;
            case R.id.drawer_playlist:
                changeFragment(playlistsEditFragment);
                break;
            case R.id.drawer_equalizer:
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }


}
