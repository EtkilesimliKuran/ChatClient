package smyy.qsearch.activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.io.Serializable;

import smyy.qsearch.fragment.FragmentMeal;
import smyy.qsearch.R;
import smyy.qsearch.helper.AlarmManagerBroadcastReceiver;

public class ChatActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private String[] activityTitles;
    public static int navItemIndex = 0;
    private static final String TAG_MEAL = "meal";
    private static final String TAG_TEFSİR = "tefsir";
    private static final String TAG_HADİS = "hadis";
    private static final String TAG_SUNNET = "sünnet";
    public static String CURRENT_TAG = TAG_MEAL;
    private Handler mHandler;
    AlarmManagerBroadcastReceiver alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        activityTitles = getResources().getStringArray(R.array.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_MEAL;
            loadHomeFragment();
        }

    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_meal:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_MEAL;
                        break;
                    case R.id.nav_tefsir:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_TEFSİR;
                        break;
                    case R.id.nav_hadis:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_HADİS;
                        break;
                    case R.id.nav_sünnet:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_SUNNET;
                        break;
                    default:
                        navItemIndex = 0;
                }
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer,
                 R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }
    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        Bundle data = new Bundle();
        data.putInt("type", navItemIndex);
        FragmentMeal homeFragment = new FragmentMeal();
        homeFragment.setArguments(data);
        return homeFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                return false;
            case R.id.action_add_bookmark:
                GetAllBookmark();
                return true;
            case R.id.action_settings:
                Settings();
                return true;
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void GetAllBookmark(){
        Intent intent = new Intent(ChatActivity.this, BookmarkActivity.class);
        startActivity(intent);
    }

    public void Settings(){
        Intent intent = new Intent(ChatActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}
