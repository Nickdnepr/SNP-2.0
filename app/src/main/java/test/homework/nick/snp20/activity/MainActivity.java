package test.homework.nick.snp20.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.fragments.BottomSheetTestingFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private boolean bottomSheetExtended = false;

    private FrameLayout fragmentContent;
    private LinearLayout bottom_panel;
    private BottomSheetBehavior bottomSheetBehavior;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView toolbarContext;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControlElements();
        setupControlElements();
        BottomSheetTestingFragment fragment = new BottomSheetTestingFragment();
        changeFragment(fragment);
    }

    //initializing all control elements from xml, like layouts, buttons, textViews, toolbar, etc.
    //здесь инициализируются все управляющие элементы типа кнопок, тулбаров и им подобных
    private void initControlElements() {
        bottom_panel = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_panel);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragmentContent = (FrameLayout) findViewById(R.id.fragmentHost);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarContext = (TextView) findViewById(R.id.text_in_toolbar);
        navigationView= (NavigationView) findViewById(R.id.navigation_drawer);
    }


    //setting clickListeners, attaching elements from upper method to activity, etc.
    //здесь инициализированые в методе выше элементы привязываются к логике, устанавливаются кликлистенеры и тому подобные
    private void setupControlElements() {
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
    }


    //changing fragment in activity
    //меняет фрагмент в активити
    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHost, fragment).commit();
    }


    public void openSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetExtended = true;
    }

    public void closeSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetExtended = false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }
}
