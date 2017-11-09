package aaron.filecommand.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import aaron.filecommand.R;

import static java.security.AccessController.getContext;

/**
 * Created by Yunwen on 11/6/2017.
 */

public class LanguageActivity extends AppCompatActivity  implements View.OnClickListener {
    private String TAG = "LanguageActivity";
    private Toolbar toolbar;
    private TextView toolbarTitle, textTitle, textContent;
    private ImageView imageEnglish, imageArabic,imageGerman,imageSpanish,imageFrench,imageChinese, imageJapanese;
    private Configuration config;
    private Resources resources;
    private DisplayMetrics dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        initView();
    }

    protected void initView(){
        resources = getResources();
        dm = resources.getDisplayMetrics();
        config = resources.getConfiguration();
        imageEnglish = (ImageView) findViewById(R.id.image_tick_english);
        imageChinese = (ImageView) findViewById(R.id.image_tick_chinese);
        imageArabic = (ImageView) findViewById(R.id.image_tick_arabic);
        imageGerman = (ImageView) findViewById(R.id.image_tick_german);
        imageSpanish = (ImageView) findViewById(R.id.image_tick_spanish);
        imageFrench = (ImageView) findViewById(R.id.image_tick_french);
        imageJapanese = (ImageView) findViewById(R.id.image_tick_japanese);
        imageEnglish.setOnClickListener(this);imageChinese.setOnClickListener(this);imageJapanese.setOnClickListener(this);
        imageArabic.setOnClickListener(this);imageGerman.setOnClickListener(this);imageSpanish.setOnClickListener(this);
        imageFrench.setOnClickListener(this);
    }

    public void onClick(View v) {
        changeImageLike(v.getId());
        switch (v.getId()){
            case R.id.image_tick_english:
                config.locale = Locale.ENGLISH;
                resources.updateConfiguration(config, dm);
                break;
            case R.id.image_tick_chinese:
                config.locale = Locale.CHINESE;
                resources.updateConfiguration(config, dm);
                break;
            case R.id.image_tick_japanese:
                config.locale = Locale.JAPAN;
                resources.updateConfiguration(config, dm);
                break;
            case R.id.image_tick_arabic:
                Locale arabic = new Locale("ar", "AR");
                config.locale = arabic;
                resources.updateConfiguration(config, dm);
                break;
            case R.id.image_tick_french:
                config.locale = Locale.FRENCH;
                resources.updateConfiguration(config, dm);
                break;
            case R.id.image_tick_spanish:
                Locale spanish = new Locale("es", "ES");
                config.locale = spanish;
                resources.updateConfiguration(config, dm);
                break;
            case R.id.image_tick_german:
                config.locale = Locale.GERMAN;
                resources.updateConfiguration(config, dm);
                break;
            default:
                config.locale = Locale.ENGLISH;
                resources.updateConfiguration(config, dm);
        }
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void changeImageLike(Integer resId){
        ImageView imageTarget =  (ImageView) findViewById(resId);
        assert(resId == imageTarget.getId());
        Integer integer = (Integer) imageTarget.getTag();
        integer = integer == null ? 0 : integer;
        switch(integer) {
            case R.drawable.tick:
                imageTarget.setImageResource(R.drawable.square);
                imageTarget.setTag(R.drawable.square);
                break;
            case R.drawable.square:
            default:
                imageTarget.setImageResource(R.drawable.tick);
                imageTarget.setTag(R.drawable.tick);
                break;
        }
    }
}
