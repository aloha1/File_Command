package aaron.filecommand.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aaron.filecommand.R;

/**
 * Created by Yunwen on 10/30/2017.
 */

public class AppStoreActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageView imageToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appstore);
        initView();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarTitle = (TextView) findViewById(R.id.text_toolbar_title);
        setToolbarHome();
    }

    private void setToolbarHome(){
        toolbarTitle.setText("Our App Store");
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
