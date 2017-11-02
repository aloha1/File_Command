package aaron.filecommand.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import aaron.filecommand.R;
import aaron.filecommand.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageView imageToolbar;

    private boolean doubleClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigation();
        initFragment();
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
