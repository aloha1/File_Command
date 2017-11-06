package aaron.filecommand.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import aaron.filecommand.R;
import aaron.filecommand.dao.Category;
import aaron.filecommand.dao.CategoryRepo;
import aaron.filecommand.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public enum AppStart {
        FIRST_TIME, FIRST_TIME_VERSION, NORMAL;
    }
    private static final String LAST_APP_VERSION = "last_app_version";

    private String TAG  = "MainActivity";
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageView imageToolbar;

    private boolean doubleClick = false;

    private int _algorithm_id = 0;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigation();
        initFragment();
        initAds();
    }
    protected void initAds(){
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3456168518371304/2700959193");

        mAdView =   (AdView)  findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void initNavigation(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle = (TextView) findViewById(R.id.text_toolbar_title);
        imageToolbar = (ImageView) findViewById(R.id.imgae_toolbar_title);

        setToolbarHome();

        RelativeLayout layoutToolbar = (RelativeLayout)
                toolbar.findViewById(R.id.toolbar_item_container);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        switch (checkAppStart()) {
            case NORMAL:
                // We don't want to get on the user's nerves
                break;
            case FIRST_TIME_VERSION:
                // TODO show what's new
                addToHome();
                break;
            case FIRST_TIME:
                // TODO show a tutorial
                addToHome();
                break;
            default:
                break;
        }
    }

    private void initFragment(){
        addNewFragment(new HomeFragment());
        setToolbarHome();
    }

    private void setToolbarHome(){
        toolbarTitle.setText("Home");
        imageToolbar.setImageResource(R.drawable.bag_white);
        imageToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppStoreActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_go_premium) {
            // Handle the camera action

        } else if (id == R.id.nav_home) {
            setToolbarHome();
        } else if (id == R.id.nav_recent_files) {
            toolbarTitle.setText("Recent");
        } else if (id == R.id.nav_favorite) {

        } else if (id == R.id.nav_internal_shared_storage) {

        } else if (id == R.id.nav_ftp) {

        }else{

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addNewFragment(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, "SubFragment")
                .addToBackStack("SubFragment").commit();
    }

    private void addToHome(){
        addToFavorite("image_tick_picture");
        addToFavorite("image_tick_music");
        addToFavorite("image_tick_documents");
        addToFavorite("image_tick_secured_files");
        addToFavorite("image_tick_pc_files_transfer");
        addToFavorite("image_tick_favorites");
        addToFavorite("image_tick_recent_files");
    }
    private void addToFavorite(String topic) {
        CategoryRepo repo = new CategoryRepo(this);
        Category category = repo.getColumnByTopic(topic);
        try {
            if (category.topic.equals(topic)) {
                repo.update(category);
                Toast.makeText(this, "没有内容", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            category.time = 25;
            category.content = "";
            category.topic = topic;
            category.dbId = _algorithm_id;
            _algorithm_id = repo.insert(category);
            Log.d(TAG, "add:"+category.topic);
            Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public AppStart checkAppStart() {
        PackageInfo pInfo;
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        AppStart appStart = AppStart.NORMAL;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int lastVersionCode = sharedPreferences
                    .getInt(LAST_APP_VERSION, -1);
            int currentVersionCode = pInfo.versionCode;
            appStart = checkAppStart(currentVersionCode, lastVersionCode);
            // Update version in preferences
            sharedPreferences.edit()
                    .putInt(LAST_APP_VERSION, currentVersionCode).commit();
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("Check Start",
                    "Unable to determine current app version from pacakge manager. Defenisvely assuming normal app start.");
        }
        return appStart;
    }

    public AppStart checkAppStart(int currentVersionCode, int lastVersionCode) {
        if (lastVersionCode == -1) {
            return AppStart.FIRST_TIME;
        } else if (lastVersionCode < currentVersionCode) {
            return AppStart.FIRST_TIME_VERSION;
        } else if (lastVersionCode > currentVersionCode) {
            Log.d("Check Start", "Current version code (" + currentVersionCode
                    + ") is less then the one recognized on last startup ("
                    + lastVersionCode
                    + "). Defenisvely assuming normal app start.");
            return AppStart.NORMAL;
        } else {
            return AppStart.NORMAL;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        Fragment current = fm.findFragmentById(R.id.container);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fm.getBackStackEntryCount() > 1) {
                fm.popBackStack();

            } else {
                if (doubleClick) {
                    finish();
                }
                this.doubleClick = true;
                Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleClick = false;
                    }
                }, 1000);
            }
        }
    }
}
