package aaron.filecommand.activity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.util.Collections;
import java.util.List;

import aaron.filecommand.R;
import aaron.filecommand.dao.Category;
import aaron.filecommand.dao.CategoryRepo;
import aaron.filecommand.fragment.DocumentFragment;
import aaron.filecommand.fragment.HomeFragment;
import aaron.filecommand.fragment.MusicFragment;
import aaron.filecommand.fragment.PhotoFragment;
import aaron.filecommand.fragment.VideoFragment;
import aaron.filecommand.model.Storage;

import static aaron.filecommand.activity.helper.Helper.fileExt;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public enum AppStart {
        FIRST_TIME, FIRST_TIME_VERSION, NORMAL;
    }
    private static final String LAST_APP_VERSION = "last_app_version";
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private InterstitialAd mInterstitialAd;

    private String TAG  = "MainActivity";
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageView imageToolbar;

    private boolean doubleClick = false;

    private int _algorithm_id = 0;
    private int mTreeSteps = 0;
    private AdView mAdView;
    private TextView mPathView;

    private Storage mStorage;
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

    public void setDrawerLayout(){
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
    }

    private void initNavigation(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        toolbarTitle = (TextView) findViewById(R.id.text_toolbar_title);
        imageToolbar = (ImageView) findViewById(R.id.imgae_toolbar_title);
        setToolbarHome();
        setDrawerLayout();
        switch (checkAppStart()) {
            case NORMAL:
                break;
            case FIRST_TIME_VERSION:
                addToHome();
                break;
            case FIRST_TIME:
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
        toolbarTitle.setText(R.string.text_tick_home);
        imageToolbar.setVisibility(View.VISIBLE);
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
        clearBackStack();
        setSupportActionBar(toolbar);
        imageToolbar.setVisibility(View.INVISIBLE);
        if (id == R.id.nav_go_premium) {
            startPremium();
        } else if (id == R.id.nav_home) {initFragment();} else if (id == R.id.nav_pictures) {
            initPictures();
        }else if (id == R.id.nav_documents) {initDocumentFragment();}else if (id == R.id.nav_videos) {
            initVideos();
        }else if (id == R.id.nav_music) {initMusic();} else if (id == R.id.nav_recent_files) {
            toolbarTitle.setText(R.string.text_tick_recent_files);
        } else if (id == R.id.nav_favorite) {
            toolbarTitle.setText(R.string.text_tick_favorites);
        } else if (id == R.id.nav_internal_shared_storage) {
            toolbarTitle.setText(R.string.text_tick_internal_shared_storage);
        } else if (id == R.id.nav_ftp) {
            toolbarTitle.setText(R.string.text_tick_ftp);
        }else if (id == R.id.nav_language) {
            initFragment();
            Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
            startActivity(intent);
        }else{

        }
        setDrawerLayout();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initPictures(){
        addNewFragment(new PhotoFragment());
        toolbarTitle.setText(R.string.text_tick_picture);
    }


//        public void initDocumentFragment(){
//            addNewFragment(new PhotoFragment());
//            toolbarTitle.setText(R.string.text_tick_picture);
//        }

        public void initVideos(){
        toolbarTitle.setText(R.string.text_tick_videos);
        addNewFragment(new VideoFragment());
        }

        public void initMusic(){
        addNewFragment(new MusicFragment());
        toolbarTitle.setText(R.string.text_tick_music);
        }


    public void initDocumentFragment(){
        mStorage = new Storage(this);
        String dataContent = mStorage.getInternalFilesDirectory(); Log.d(TAG, dataContent);
        addDocumentFragment(dataContent);
        toolbarTitle.setText(R.string.text_tick_documents);
    }

    public void startPremium(){
        initFragment();
        startActivity( new Intent(MainActivity.this, GoPremiumActivity.class));
    }
    public void addDocumentFragment(String data){
        DocumentFragment fragment = new DocumentFragment();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, "SubFragment")
                .addToBackStack("SubFragment").commit();
        fragment.dataFlow(data);
    }

    public void addNewFragment(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, "SubFragment")
                .addToBackStack("SubFragment").commit();
    }
    private void addToHome(){
        addToFavorite("image_tick_favorites");
        addToFavorite("image_tick_documents");
        addToFavorite("image_tick_videos");
        addToFavorite("image_tick_archives");
        addToFavorite("image_tick_downloads");
        addToFavorite("image_tick_recent_files");
        addToFavorite("image_tick_convert_files");
    }

    public void clearBackStack() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
    private void addToFavorite(String topic) {
        CategoryRepo repo = new CategoryRepo(this);
        Category category = repo.getColumnByTopic(topic);
        try {
            if (category.topic.equals(topic)) {
                repo.update(category);
                Toast.makeText(this, R.string.no_content, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            category.time = 25;
            category.content = "";
            category.topic = topic;
            category.dbId = _algorithm_id;
            _algorithm_id = repo.insert(category);
            Log.d(TAG, "add:"+category.topic);
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

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }
    }
    private String getCurrentPath() {
        return mPathView.getText().toString();
    }

    private void showFiles(String path) {
        mPathView.setText(path);
        List<File> files = mStorage.getFiles(path);
    }

    private String getPreviousPath() {
        String path = getCurrentPath();
        int lastIndexOf = path.lastIndexOf(File.separator);
        if (lastIndexOf < 0) {
            Toast.makeText(this,"Can't go anymore", Toast.LENGTH_SHORT).show();
            return getCurrentPath();
        }
        return path.substring(0, lastIndexOf);
    }

    public void clickFile(File file){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String mimeType =  MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExt(file.getAbsolutePath()));
            Uri apkURI = FileProvider.getUriForFile(
                    this,
                    getApplicationContext()
                            .getPackageName() + ".provider", file);
            intent.setDataAndType(apkURI, mimeType);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setAd(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3456168518371304/4640726822");
    }
    @Override
    public void onBackPressed() {
        if (mTreeSteps > 0) {
            String path = getPreviousPath();//exit to last layer
            mTreeSteps--;
            showFiles(path);//show this layer
            return;
        }

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
                Toast.makeText(this, R.string.click_to_exit, Toast.LENGTH_SHORT).show();
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
